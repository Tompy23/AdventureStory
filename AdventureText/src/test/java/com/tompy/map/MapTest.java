package com.tompy.map;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.common.Coordinates2DImpl;
import com.tompy.entity.area.Area;
import com.tompy.messages.MessageHandler;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class MapTest {
    public AdventureMapBuilder builder;

    @Mock Player mockPlayer;

    @Mock Adventure mockAdventure;

    @Mock Area mockArea;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        builder = AdventureMapImpl.createBuilder();
    }

    @Test
    public void testAddLegend() {
        AdventureMap advMap = builder.height(3).width(3).build();
        advMap.addLegendExt('X', "marks the spot");

        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[4].equals("X -> marks the spot"));
    }

    @Test
    public void testAddLandmark() {
        AdventureMap advMap = builder.height(3).width(3).build();
        advMap.addLandmark('X', "marks the spot", new Coordinates2DImpl(1, 1));

        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[1].equals(".X."));
        Assert.assertTrue(tests[4].equals("X -> marks the spot"));
    }

    @Test
    public void testAddArea() {
        AdventureMap advMap = builder.height(7).width(7).build();
        advMap.addArea('X', "a mess", new Coordinates2DImpl(1, 2), new Coordinates2DImpl(4, 4));

        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[0].equals("......."));
        Assert.assertTrue(tests[1].equals("......."));
        Assert.assertTrue(tests[2].equals(".XXXX.."));
        Assert.assertTrue(tests[3].equals(".XXXX.."));
        Assert.assertTrue(tests[4].equals(".XXXX.."));
        Assert.assertTrue(tests[5].equals("......."));
        Assert.assertTrue(tests[6].equals("......."));
        Assert.assertTrue(tests[7].equals(". -> Clear"));
        Assert.assertTrue(tests[8].equals("X -> a mess"));
    }

    @Test
    public void testDefineDisplay() {
        AdventureMap advMap = builder.height(7).width(7).build();
        advMap.addArea('X', "a mess", new Coordinates2DImpl(1, 2), new Coordinates2DImpl(4, 4));
        advMap.defineDisplay(new Coordinates2DImpl(1, 5), new Coordinates2DImpl(5, 6));

        String[] tests = prepareTests(advMap);
        Assert.assertTrue(tests.length == 3);
        Assert.assertTrue(tests[0].equals("....."));
        Assert.assertTrue(tests[1].equals("....."));
        Assert.assertTrue(tests[2].equals(". -> Clear"));
    }

    @Test
    public void testResetDisplay() {
        AdventureMap advMap = builder.height(7).width(7).build();
        advMap.addArea('X', "a mess", new Coordinates2DImpl(1, 2), new Coordinates2DImpl(4, 4));
        advMap.defineDisplay(new Coordinates2DImpl(1, 5), new Coordinates2DImpl(5, 6));

        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests.length == 3);
        Assert.assertTrue(tests[0].equals("....."));
        Assert.assertTrue(tests[1].equals("....."));
        Assert.assertTrue(tests[2].equals(". -> Clear"));

        advMap.resetDisplay();

        String[] tests2 = prepareTests(advMap);

        Assert.assertTrue(tests2[0].equals("......."));
        Assert.assertTrue(tests2[1].equals("......."));
        Assert.assertTrue(tests2[2].equals(".XXXX.."));
        Assert.assertTrue(tests2[3].equals(".XXXX.."));
        Assert.assertTrue(tests2[4].equals(".XXXX.."));
        Assert.assertTrue(tests2[5].equals("......."));
        Assert.assertTrue(tests2[6].equals("......."));
        Assert.assertTrue(tests2[7].equals(". -> Clear"));
        Assert.assertTrue(tests2[8].equals("X -> a mess"));
    }

    @Test
    public void testMapCreate() {
        MessageHandler messages = new MessageHandler("message.properties");

        AdventureMap advMap = builder.height(4).width(6).map(AdventureUtils.getMap("map", messages)).build();

        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[0].equals("......"));
        Assert.assertTrue(tests[1].equals("......"));
        Assert.assertTrue(tests[2].equals(".ABC.."));
        Assert.assertTrue(tests[3].equals("......"));
    }

    @Test
    public void testPlayerOverlay() {
        AdventureMap advMap = builder.height(3).width(3).build();
        MapOverlay playerOverlay = MapOverlayPlayerLocationImpl.createBuilder().mark('X').legend("player").build();

        when(mockArea.getCoordinates()).thenReturn(new Coordinates2DImpl(1, 1));
        when(mockPlayer.getArea()).thenReturn(mockArea);

        advMap.addOverlay(playerOverlay);
        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[0].equals("..."));
        Assert.assertTrue(tests[1].equals(".X."));
        Assert.assertTrue(tests[2].equals("..."));
        Assert.assertTrue(tests[4].equals("X -> player"));
        Assert.assertTrue(tests.length == 5);

        advMap.removeOverlay(playerOverlay);
        tests = prepareTests(advMap);

        Assert.assertTrue(tests[0].equals("..."));
        Assert.assertTrue(tests[1].equals("..."));
        Assert.assertTrue(tests[2].equals("..."));
        Assert.assertTrue(tests.length == 4);
    }

    @Test
    public void testApplyMultipleOverlays() {
        AdventureMap advMap = builder.height(3).width(3).build();

        when(mockArea.getCoordinates()).thenReturn(new Coordinates2DImpl(1, 1));
        when(mockPlayer.getArea()).thenReturn(mockArea);

        advMap.addOverlay(MapOverlayPlayerLocationImpl.createBuilder().mark('X').legend("player").build());
        advMap.addOverlay(MapOverlaySplatterImpl.createBuilder().mark('R').spot(new Coordinates2DImpl(0, 2))
                .spot(new Coordinates2DImpl(2, 2)).legend("robot").build());
        String[] tests = prepareTests(advMap);

        Assert.assertTrue(tests[0].equals("..."));
        Assert.assertTrue(tests[1].equals(".X."));
        Assert.assertTrue(tests[2].equals("R.R"));
        Assert.assertTrue(tests[4].equals("R -> robot"));
        Assert.assertTrue(tests[5].equals("X -> player"));
        Assert.assertTrue(tests.length == 6);
    }

    private String[] prepareTests(AdventureMap advMap) {
        List<Response> responses = advMap.display(mockPlayer, mockAdventure);
        String[] tests = new String[responses.size()];
        int count = 0;
        for (Response s : responses) {
            tests[count++] = s.render();
        }
        return tests;
    }
}

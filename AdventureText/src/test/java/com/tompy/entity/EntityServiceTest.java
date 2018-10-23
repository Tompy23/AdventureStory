package com.tompy.entity;

import com.tompy.attribute.Attribute;
import com.tompy.attribute.AttributeManagerFactory;
import com.tompy.attribute.AttributeManagerFactoryImpl;
import com.tompy.directive.ItemType;
import com.tompy.entity.event.EventManagerFactory;
import com.tompy.entity.item.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.OptionalInt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest {

    private AttributeManagerFactory attributeManagerFactory;
    private EntityService entityService;


    @Mock
    private EventManagerFactory mockEventManagerFactory;

    @Before
    public void init() {
        attributeManagerFactory = new AttributeManagerFactoryImpl();
        entityService = new EntityServiceImpl(attributeManagerFactory, mockEventManagerFactory);
    }

    @Test
    public void testCreateItem() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        assertTrue(i.getKey() == 1);
    }

    @Test
    public void testAddAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        assertTrue(entityService.is(i, Attribute.TEST_NORMALA));
    }

    @Test
    public void testAddAttributeValue() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_HAS_VALUE, 1);
    }

    @Test
    public void testRemoveAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        entityService.remove(i, Attribute.TEST_NORMALA);
    }

    @Test
    public void testResetAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        entityService.reset(i, Attribute.TEST_NORMALA);
    }

    @Test
    public void testIsAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        boolean check = entityService.is(i, Attribute.TEST_NORMALA);
        assertTrue(check);
    }

    @Test
    public void testIsFalseAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_NORMALA);
        boolean check = entityService.is(i, Attribute.TEST_NORMALB);
        assertFalse(check);
    }

    @Test
    public void testValueAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.add(i, Attribute.TEST_HAS_VALUE, 5);
        OptionalInt check = entityService.valueFor(i, Attribute.TEST_HAS_VALUE);
        assertTrue(check.getAsInt() == 5);
    }

    @Test
    public void testNoValueAttribute() {
        Item i = entityService.createItemBuilder().type(ItemType.ITEM_TEST).name("item").description("stuff").build();
        entityService.reset(i, Attribute.TEST_NORMALA);
        OptionalInt check = entityService.valueFor(i, Attribute.TEST_NORMALA);
        assertTrue(check.equals(OptionalInt.empty()));
    }

}

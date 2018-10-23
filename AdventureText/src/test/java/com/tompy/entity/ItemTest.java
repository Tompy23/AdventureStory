package com.tompy.entity;

import com.tompy.attribute.AttributeManagerFactory;
import com.tompy.directive.ItemType;
import com.tompy.entity.event.EventManagerFactory;
import com.tompy.entity.item.Item;
import com.tompy.entity.item.ItemBuilder;
import com.tompy.entity.item.ItemBuilderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ItemTest {
    public ItemBuilderFactory itemBuilderFactory;

    @Mock
    private AttributeManagerFactory mockAttributeManagerFactory;

    @Mock
    private EventManagerFactory mockEventManagerFactory;

    @Before
    public void init() {
        itemBuilderFactory = new EntityServiceImpl(mockAttributeManagerFactory, mockEventManagerFactory);
    }

    @Test
    public void testName() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").description(
                "An ancient grail").build();
        assertTrue(i.getName().equals("item"));
    }

    @Test
    public void testDescriptors() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").description(
                "An ancient grail").build();
        assertTrue(i.getDescriptionWords().size() == 3);
    }

    @Test
    public void getDetailDescription() {
        ItemBuilder builder = itemBuilderFactory.createItemBuilder();
        Item i = builder.type(ItemType.ITEM_TEST).name("item").description(
                "An ancient grail").build();
        assertTrue(i.getDescription().equals("An ancient grail"));
    }
}

package com.tompy.attribute;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.OptionalInt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AttributeTest {

    private AttributeManagerFactoryImpl managerFactory;
    private AttributeManager manager;

    @Before
    public void init() {
        managerFactory = new AttributeManagerFactoryImpl();
        manager = managerFactory.create();
    }

    @Test
    public void testAdd() {
        manager.add(Attribute.TEST_NORMALA);
        manager.add(Attribute.TEST_NORMALB);

        assertTrue(manager.getAll().size() == 2);
    }

    @Test
    public void testAddDup() {
        manager.add(Attribute.TEST_NORMALA);
        manager.add(Attribute.TEST_NORMALA);

        assertTrue(manager.getAll().size() == 1);
    }

    @Test
    public void testAddWithValue() {
        manager.add(Attribute.TEST_HAS_VALUE, 5);

        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).getAsInt() == 5);
    }

    @Test
    public void testAddWithNewValue() {
        manager.add(Attribute.TEST_HAS_VALUE, 5);
        manager.add(Attribute.TEST_HAS_VALUE, 2);

        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).getAsInt() == 5);
    }

    @Test
    public void testStackable() {
        manager.add(Attribute.TEST_STACKABLE);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 1);
        manager.add(Attribute.TEST_STACKABLE);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 2);
        manager.remove(Attribute.TEST_STACKABLE);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 1);
    }

    @Test
    public void testWrongGet() {
        manager.add(Attribute.TEST_STACKABLE);
        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).equals(OptionalInt.empty()));
    }

    @Test
    public void testRemoveHasValue() {
        manager.add(Attribute.TEST_HAS_VALUE, 5);
        manager.remove(Attribute.TEST_HAS_VALUE);
        manager.add(Attribute.TEST_HAS_VALUE, 2);

        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).getAsInt() == 2);
    }

    @Test
    public void testReset() {
        manager.add(Attribute.TEST_HAS_VALUE, 5);
        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).getAsInt() == 5);

        manager.reset(Attribute.TEST_HAS_VALUE);
        assertTrue(manager.getValue(Attribute.TEST_HAS_VALUE).getAsInt() == 0);
    }

    @Test
    public void testResetNoValue() {
        manager.add(Attribute.TEST_NORMALA);
        manager.reset(Attribute.TEST_NORMALA);

        assertTrue(manager.getValue(Attribute.TEST_NORMALA).equals(OptionalInt.empty()));
    }

    @Test
    public void testGetAllAndClear() {
        manager.add(Attribute.TEST_NORMALA);
        assertTrue(manager.getAll().size() == 1);
        manager.clear();
        assertTrue(manager.getAll().size() == 0);
    }

    @Test
    public void testStackableValue() {
        manager.add(Attribute.TEST_STACKABLE, 5);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 5);
        manager.add(Attribute.TEST_STACKABLE, 2);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 7);
        manager.add(Attribute.TEST_STACKABLE, -6);
        assertTrue(manager.getValue(Attribute.TEST_STACKABLE).getAsInt() == 1);
    }

    @Test
    public void testIs() {
        manager.add(Attribute.TEST_NORMALA);
        manager.add(Attribute.TEST_NORMALB);
        assertTrue(manager.is(Attribute.TEST_NORMALA));
        manager.remove(Attribute.TEST_NORMALA);
        assertFalse(manager.is(Attribute.TEST_NORMALA));
        assertTrue(manager.is(Attribute.TEST_NORMALB));
    }
}

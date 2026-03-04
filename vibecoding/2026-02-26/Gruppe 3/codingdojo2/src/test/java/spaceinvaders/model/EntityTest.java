package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void constructor_setsFieldsCorrectly() {
        Entity e = new Entity(10, 20, 30, 40);
        assertEquals(10, e.getX());
        assertEquals(20, e.getY());
        assertEquals(30, e.getWidth());
        assertEquals(40, e.getHeight());
        assertTrue(e.isAlive());
    }

    @Test
    void setters_updateFields() {
        Entity e = new Entity(0, 0, 10, 10);
        e.setX(50);
        e.setY(60);
        e.setAlive(false);
        assertEquals(50, e.getX());
        assertEquals(60, e.getY());
        assertFalse(e.isAlive());
    }

    @Test
    void intersects_overlapping_returnsTrue() {
        Entity a = new Entity(0, 0, 20, 20);
        Entity b = new Entity(10, 10, 20, 20);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    void intersects_notOverlapping_returnsFalse() {
        Entity a = new Entity(0, 0, 10, 10);
        Entity b = new Entity(20, 20, 10, 10);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_touching_returnsFalse() {
        Entity a = new Entity(0, 0, 10, 10);
        Entity b = new Entity(10, 0, 10, 10);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_deadEntity_returnsFalse() {
        Entity a = new Entity(0, 0, 20, 20);
        Entity b = new Entity(5, 5, 20, 20);
        b.setAlive(false);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_bothDead_returnsFalse() {
        Entity a = new Entity(0, 0, 20, 20);
        Entity b = new Entity(5, 5, 20, 20);
        a.setAlive(false);
        b.setAlive(false);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_overlapOnlyHorizontally_returnsFalse() {
        Entity a = new Entity(0, 0, 20, 10);
        Entity b = new Entity(10, 20, 20, 10);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_overlapOnlyVertically_returnsFalse() {
        Entity a = new Entity(0, 0, 10, 20);
        Entity b = new Entity(20, 10, 10, 20);
        assertFalse(a.intersects(b));
    }

    @Test
    void intersects_containedEntity_returnsTrue() {
        Entity a = new Entity(0, 0, 100, 100);
        Entity b = new Entity(10, 10, 5, 5);
        assertTrue(a.intersects(b));
    }
}

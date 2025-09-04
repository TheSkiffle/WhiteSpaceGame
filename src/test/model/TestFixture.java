package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixture {
    private Fixture wall;
    private Fixture floor;

    @BeforeEach
    public void runBefore() {
        wall = new Fixture("wall", -1, -1, 1, 1, true);
        floor = new Fixture("floor", -1, -1, 1, 1, false);
    }

    // Test constructor
    @Test
    public void testConstructor() {
        assertEquals("wall", wall.getType());
        assertEquals(-1, wall.getXPos1());
        assertEquals(-1, wall.getYPos1());
        assertEquals(1, wall.getXPos2());
        assertEquals(1, wall.getYPos2());
        assertTrue(wall.getCollidable());
        assertEquals("floor", floor.getType());
        assertEquals(-1, floor.getXPos1());
        assertEquals(-1, floor.getYPos1());
        assertEquals(1, floor.getXPos2());
        assertEquals(1, floor.getYPos2());
        assertFalse(floor.getCollidable());
    }
}

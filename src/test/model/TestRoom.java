package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRoom {
    private Room emptyroom;
    private Room emptybutwalledroom;

    @BeforeEach
    public void runBefore() {
        emptyroom = new Room("Empty");
        emptybutwalledroom = new Room("EmptyButWalled");
    }

    // Test constructor
    @Test
    public void testConstructor() {
        assertEquals(0, emptyroom.getFixtures().size());
        assertEquals(0, emptyroom.getItems().size());
        assertEquals(0, emptyroom.getCreatures().size());
        assertEquals(4, emptybutwalledroom.getFixtures().size());
        assertEquals(0, emptybutwalledroom.getItems().size());
        assertEquals(0, emptybutwalledroom.getCreatures().size());
    }
}

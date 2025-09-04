package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    private Player player;

    @BeforeEach
    public void runBefore() {
        player = new Player(0,0);
    }

    // Test constructor
    @Test
    public void testConstructor() {
        // Nothing to be tested yet
    }

    // Test movement calculation when there is no input
    @Test
    public void testCalculateMovementNoInput() {
        player.calculateMovement(0,0,0,0);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }

    // Test movement calculation when there is only one input
    @Test
    public void testCalculateMovementOneInput() {
        player.calculateMovement(1,0,0,0);
        assertEquals(-1 * player.getWalkSpeed(), player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(0,1,0,0);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(0,0,1,0);
        assertEquals(0, player.getXPos());
        assertEquals(-1 * player.getWalkSpeed(), player.getYPos());
        player.calculateMovement(0,0,0,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }

    // Test movement calculation when there are two inputs
    @Test
    public void testCalculateMovementTwoInput() {
        player.calculateMovement(1,1,0,0);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(1,0,1,0);
        assertEquals((int)round(-sqrt(2) / 2 * player.getWalkSpeed()), player.getXPos());
        assertEquals((int)round(-sqrt(2) / 2 * player.getWalkSpeed()), player.getYPos());
        player.calculateMovement(1,0,0,1);
        assertEquals(2 * (int)round(-sqrt(2) / 2 * player.getWalkSpeed()), player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(0,1,1,0);
        assertEquals((int)round(-sqrt(2) / 2 * player.getWalkSpeed()), player.getXPos());
        assertEquals((int)round(-sqrt(2) / 2 * player.getWalkSpeed()), player.getYPos());
        player.calculateMovement(0,1,0,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(0,0,1,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }

    // Test movement calculation when there are three inputs
    @Test
    public void testCalculateMovementThreeInput() {
        player.calculateMovement(1,1,1,0);
        assertEquals(0, player.getXPos());
        assertEquals(-1 * player.getWalkSpeed(), player.getYPos());
        player.calculateMovement(1,1,0,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(1,0,1,1);
        assertEquals(-1 * player.getWalkSpeed(), player.getXPos());
        assertEquals(0, player.getYPos());
        player.calculateMovement(0,1,1,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }

    // Test movement calculation when there are four inputs
    @Test
    public void testCalculateMovementFourInput() {
        player.calculateMovement(1,1,1,1);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }

    // Test collision
    @Test
    public void testCollision() {
        player.setWalkSpeed(5);
        Fixture wall = new Fixture("wall", 10,10,20,20,true);
        player.setXPos(0);
        player.setYPos(15);
        player.calculateMovement(0,1,0,0);
        player.collision(wall);
        assertEquals(0, player.getXPos());
        assertEquals(15, player.getYPos());
        player.setXPos(30);
        player.calculateMovement(1,0,0,0);
        player.collision(wall);
        assertEquals(30, player.getXPos());
        assertEquals(15, player.getYPos());
        player.setXPos(15);
        player.setYPos(-10);
        player.calculateMovement(0,0,0,1);
        player.collision(wall);
        assertEquals(15, player.getXPos());
        assertEquals(-10, player.getYPos());
        player.setYPos(40);
        player.calculateMovement(0,0,1,0);
        player.collision(wall);
        assertEquals(15, player.getXPos());
        assertEquals(40, player.getYPos());
    }

    // Test if not touching fixture
    @Test
    public void testNotTouching() {
        Fixture fixture = new Fixture("floor", 10,10,20,20,false);
        player.setXPos(-20);
        player.setYPos(-20);
        assertFalse(player.touchingFixture(fixture));
    }

    // Test if touching fixture
    @Test
    public void testTouching() {
        Fixture fixture = new Fixture("floor", 10,10,20,20,false);
        player.setXPos(15);
        player.setYPos(15);
        assertTrue(player.touchingFixture(fixture));
    }
}

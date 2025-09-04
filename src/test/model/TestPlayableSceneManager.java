package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayableSceneManager {
    private PlayableSceneManager playscenemanager;

    @BeforeEach
    public void runBefore() {
        playscenemanager = new PlayableSceneManager();
    }

    // Test constructor
    @Test
    public void testConstructor() {
        Player p = playscenemanager.getPlayer();
        assertEquals(0, p.getXPos());
        assertEquals(0, p.getYPos());
    }

    // Test update method
    @Test
    public void testUpdate() {
        ArrayList<Integer> keyevents = new ArrayList<>();
        keyevents.add(0);
        keyevents.add(VK_LEFT);
        keyevents.add(VK_RIGHT);
        keyevents.add(VK_UP);
        keyevents.add(VK_DOWN);
        Player p1 = playscenemanager.getPlayer();
        Player p2 = new Player(0,0);
        assertEquals(0, p1.getXPos());
        assertEquals(0, p1.getYPos());
        for (int i : keyevents) {
            playscenemanager.keyPressed(i);
            p2.calculateMovement(playscenemanager.getPlayerLeft(),
                    playscenemanager.getPlayerRight(),
                    playscenemanager.getPlayerUp(),
                    playscenemanager.getPlayerDown());
            playscenemanager.update();
            assertEquals(p2.getXPos(), p1.getXPos());
            assertEquals(p2.getYPos(), p1.getYPos());
        }
    }

    // Test key presses
    @Test
    public void testKeyPressed() {
        playscenemanager.keyPressed(VK_LEFT);
        assertEquals(1, playscenemanager.getPlayerLeft());
        playscenemanager.keyPressed(KeyEvent.VK_RIGHT);
        assertEquals(1, playscenemanager.getPlayerRight());
        playscenemanager.keyPressed(KeyEvent.VK_UP);
        assertEquals(1, playscenemanager.getPlayerUp());
        playscenemanager.keyPressed(KeyEvent.VK_DOWN);
        assertEquals(1, playscenemanager.getPlayerDown());
        playscenemanager.resetVariables();
        playscenemanager.keyPressed(KeyEvent.VK_A);
        assertEquals(1, playscenemanager.getPlayerLeft());
        playscenemanager.keyPressed(KeyEvent.VK_D);
        assertEquals(1, playscenemanager.getPlayerRight());
        playscenemanager.keyPressed(KeyEvent.VK_W);
        assertEquals(1, playscenemanager.getPlayerUp());
        playscenemanager.keyPressed(KeyEvent.VK_S);
        assertEquals(1, playscenemanager.getPlayerDown());
    }

    // Test key presses
    @Test
    public void testResetVariables() {
        playscenemanager.keyPressed(VK_LEFT);
        playscenemanager.keyPressed(KeyEvent.VK_RIGHT);
        playscenemanager.keyPressed(KeyEvent.VK_UP);
        playscenemanager.keyPressed(KeyEvent.VK_DOWN);
        playscenemanager.resetVariables();
        assertEquals(0, playscenemanager.getPlayerLeft());
        assertEquals(0, playscenemanager.getPlayerRight());
        assertEquals(0, playscenemanager.getPlayerUp());
        assertEquals(0, playscenemanager.getPlayerDown());
    }
}

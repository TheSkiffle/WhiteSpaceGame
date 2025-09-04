package model;

import ui.Console;

import java.awt.*;
import java.awt.event.KeyEvent;

/*
 * Represents a Cutscene Manager
 */
public class CutSceneManager {
    /*
     * Represents window where player is not present
     * and only images and dialogue are shown in
     * a prescribed sequence
     */

    private CutScene currentCutScene;

    public CutSceneManager() {
        currentCutScene = new CutScene("Waiting for James");
    }

    public void setCurrentCutScene(CutScene cutscene) {
        currentCutScene = cutscene;
    }

    // MODIFIES: currentCutScene
    // EFFECTS: Passes game to currentCutScene
    public void injectGame(WhiteSpaceGame game) {
        currentCutScene.setGame(game);
    }

    // MODIFIES: currentCutScene
    // EFFECTS: Passes console to currentCutScene
    public void injectConsole(Console console) {
        currentCutScene.setConsole(console);
    }

    // MODIFIES: currentCutScene
    // EFFECTS: Passes timer tick to currentCutScene
    public void update() {
        currentCutScene.update();
    }

    // MODIFIES: currentCutScene
    // EFFECTS: If space is pressed, plays next cut scene
    public void keyPressed(int keyCode) {
        if (KeyEvent.VK_SPACE == keyCode) {
            currentCutScene.playCutScene();
        }
    }

    // EFFECTS: Draws cutscene outline and dialogue box
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WhiteSpaceGame.WIDTH, WhiteSpaceGame.HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(0, 550, WhiteSpaceGame.WIDTH, WhiteSpaceGame.HEIGHT - 550);
        g.setColor(Color.BLACK);
        g.fillRect(5, 555, WhiteSpaceGame.WIDTH - 10, WhiteSpaceGame.HEIGHT - 560);
        g.setColor(Color.GRAY);
        g.drawString("Press Space to continue", WhiteSpaceGame.WIDTH / 2 - 60, WhiteSpaceGame.HEIGHT - 20);
        g.setColor(origColor);
        currentCutScene.draw(g);
    }
}

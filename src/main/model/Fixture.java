package model;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/*
 * Represents a Fixture
 */
public class Fixture {
    /*
     * Represents a fixture that is present
     * in rooms such as walls, floors,
     * furniture, etc.
     */

    private final String type;
    private final Boolean collidable;
    // xpos1 and ypos1 are in the top left corner of the fixture
    private final int xpos1;
    private final int ypos1;
    // xpos2 and ypo2 are in the bottom right corner of the fixture
    private final int xpos2;
    private final int ypos2;
    private Image topGateImg;
    private Image botGateImg;

    // REQUIRES: type is wall, floor, or interactable
    //           interactables are listed in PlayableSceneManager
    public Fixture(String type, int xpos1, int ypos1, int xpos2, int ypos2, Boolean collidable) {
        this.type = type;
        this.xpos1 = xpos1;
        this.ypos1 = ypos1;
        this.xpos2 = xpos2;
        this.ypos2 = ypos2;
        this.collidable = collidable;
        try {
            topGateImg = ImageIO.read(new File("./data/sprites/Fixtures/qih90fwbklpa2.png"))
                    .getScaledInstance(xpos2 - xpos1, ypos2 - ypos1, Image.SCALE_DEFAULT);
            botGateImg = ImageIO.read(new File("./data/sprites/Fixtures/qih90fwbklpa2.png"))
                    .getScaledInstance(xpos2 - xpos1, ypos2 - ypos1, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            // Should not reach
        }
    }

    // EFFECTS: Writes JSON representation of fixture to file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fixture-type", type);
        json.put("fixture-collidable", collidable);
        json.put("fixture-xpos1", xpos1);
        json.put("fixture-xpos2", xpos2);
        json.put("fixture-ypos1", ypos1);
        json.put("fixture-ypos2", ypos2);
        return json;
    }

    // EFFECTS: Draws fixture
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        if (type.equals("wall")) {
            g.setColor(Color.BLACK);
            g.fillRect(xpos1 + WhiteSpaceGame.WIDTH / 2, ypos1 + WhiteSpaceGame.HEIGHT / 2,
                    xpos2 - xpos1, ypos2 - ypos1);
        }
        if ((type.equals("gateTop")) || (type.equals("gateBot"))) {
            g.drawImage(topGateImg,
                    xpos1 + WhiteSpaceGame.WIDTH / 2, ypos1 + WhiteSpaceGame.HEIGHT / 2, null);
        }
        g.setColor(origColor);
    }

    public String getType() {
        return type;
    }

    public int getXPos1() {
        return this.xpos1;
    }

    public int getYPos1() {
        return this.ypos1;
    }

    public int getXPos2() {
        return this.xpos2;
    }

    public int getYPos2() {
        return this.ypos2;
    }

    public Boolean getCollidable() {
        return this.collidable;
    }
}

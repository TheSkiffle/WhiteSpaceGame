package model;

import org.json.JSONObject;

import java.awt.*;

/*
 * Represents an Item
 */
public class Item {
    /*
     * Represents an item that is present
     * in rooms, strewn about the floor.
     */

    private final String type;
    private final String name;
    private final int xpos;
    private final int ypos;

    // REQUIRES: type is key, consumable, weapon, helmet, chestwear,
    //           armwear, gloves, pants, or shoes
    //           name is a valid item name under its respective type
    public Item(String type, String name, int xpos, int ypos) {
        this.type = type;
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    // EFFECTS: Writes JSON representation of item to file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("item-type", type);
        json.put("item-name", name);
        json.put("item-xpos", xpos);
        json.put("item-ypos", ypos);
        return json;
    }

    // EFFECTS: Draws item
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(xpos + WhiteSpaceGame.WIDTH / 2 - 4, ypos + WhiteSpaceGame.HEIGHT / 2 - 4, 18, 18);
        g.setColor(Color.YELLOW);
        g.fillRect(xpos + WhiteSpaceGame.WIDTH / 2, ypos + WhiteSpaceGame.HEIGHT / 2, 10, 10);
        g.setColor(origColor);
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getXPos() {
        return this.xpos;
    }

    public int getYPos() {
        return this.ypos;
    }
}

package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Represents a Room
 */
public class Room {
    /*
     * Represents the room for the top-down adventure
     * aspect of WhiteSpace, containing fixtures,
     * items, and creatures
     */

    private static final int ITEM_SIZE = 20;
    private static final Random RND = WhiteSpaceGame.RND;
    private static final int WIDTH = WhiteSpaceGame.WIDTH;
    private static final int HEIGHT = WhiteSpaceGame.HEIGHT;

    private final ArrayList<Fixture> fixtures;
    private final ArrayList<Item> items;
    private final ArrayList<Creature> creatures;
    private final String type;

    // REQUIRES: type is one of the switch cases in updateRoomContents()
    public Room(String type) {
        fixtures = new ArrayList<>();
        items = new ArrayList<>();
        creatures = new ArrayList<>();
        this.type = type;
        updateRoomContents();
    }

    // REQUIRES: type is one of the switch cases in updateRoomContents()
    // An alternative construction method to more directly create a room and its contents
    public Room(ArrayList<Fixture> fixtures, ArrayList<Item> items, ArrayList<Creature> creatures,
                String type) {
        this.fixtures = fixtures;
        this.items = items;
        this.creatures = creatures;
        this.type = type;
    }

    // MODIFIES: This
    // EFFECTS: Adds room contents based on the room type by calling
    //          the appropriate method
    public void updateRoomContents() {
        // A list of all available rooms
        switch (type) {
            case "Empty": // Not to be used in actual game
                // Empty
                break;
            case "EmptyButWalled": // Not to be used in actual game
                smallBoxOfWalls();
                break;
            case "EmptyButWalledWithItem": // Not to be used in actual game
                smallBoxOfWalls();
                setOfTestItems();
                break;
            case "Dungeon1": // Not to be used in actual game
                bigBoxOfWalls();
                dungeon1Interior();
                break;
            case "Dungeon2": // Not to be used in actual game
                bigBoxOfWalls();
                dungeon2Interior();
                break;
            case "Dungeon3": // Not to be used in actual game
                bigBoxOfWalls();
                dungeon3Interior();
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Creates a small box of walls
    public void smallBoxOfWalls() {
        fixtures.add(new Fixture("wall",-120,-120,-100,120,true));
        fixtures.add(new Fixture("wall",-100,-120,100,-100,true));
        fixtures.add(new Fixture("wall",100,-120,120,120,true));
        fixtures.add(new Fixture("wall",-100,100,100,120,true));
    }

    // MODIFIES: This
    // EFFECTS: Creates a large box of walls
    public void bigBoxOfWalls() {
        fixtures.add(new Fixture("wall", -500, -400, 500, -325, true));
        fixtures.add(new Fixture("wall", 500, -345, 520, 345, true));
        fixtures.add(new Fixture("wall", -500, 325, 500, 400, true));
        fixtures.add(new Fixture("wall", -520, -345, -500, 345, true));
    }

    // MODIFIES: This
    // EFFECTS: Creates the first dungeon room interior
    public void dungeon1Interior() {
        fixtures.add(new Fixture("wall", -500, -325, -100, -100, true));
        fixtures.add(new Fixture("wall", 100, -325, 500, -100, true));
        fixtures.add(new Fixture("wall", -500, 100, -100, 325, true));
        fixtures.add(new Fixture("wall", 100, 100, 500, 325, true));
        fixtures.add(new Fixture("wall", -500, -100, -300, 100, true));
        fixtures.add(new Fixture("wall", 300, -100, 500, 100, true));
        fixtures.add(new Fixture("gateTop", -100, -325, 100, -250, false));
        fixtures.add(new Fixture("gateBot", -100, 250, 100, 325, false));
        for (int i = 0; i < RND.nextInt(50); i++) {
            String tempstr = selectItemType();
            generateItemAtRandPos(tempstr, selectPrefix() + tempstr + selectEpithet());
        }
    }

    // MODIFIES: This
    // EFFECTS: Creates the second dungeon room interior
    public void dungeon2Interior() {
        fixtures.add(new Fixture("gateTop", -100, -325, 100, -250, false));
        fixtures.add(new Fixture("gateBot", -100, 250, 100, 325, false));
        for (int i = 0; i < RND.nextInt(50); i++) {
            String tempstr = selectItemType();
            generateItemAtRandPos(tempstr, selectPrefix() + tempstr + selectEpithet());
        }
    }

    // MODIFIES: This
    // EFFECTS: Creates the third dungeon room interior
    public void dungeon3Interior() {
        fixtures.add(new Fixture("wall", -500, -325, -100, -250, true));
        fixtures.add(new Fixture("wall", 100, -325, 500, -250, true));
        fixtures.add(new Fixture("wall", -500, 250, -100, 325, true));
        fixtures.add(new Fixture("wall", 100, 250, 500, 325, true));
        fixtures.add(new Fixture("wall", -500, -250, -400, 250, true));
        fixtures.add(new Fixture("wall", 400, -250, 500, 250, true));
        fixtures.add(new Fixture("gateTop", -100, -325, 100, -250, false));
        fixtures.add(new Fixture("gateBot", -100, 250, 100, 325, false));
        for (int i = 0; i < RND.nextInt(50); i++) {
            String tempstr = selectItemType();
            generateItemAtRandPos(tempstr, selectPrefix() + tempstr + selectEpithet());
        }
    }

    // EFFECTS: Randomly selects item type
    public String selectItemType() {
        switch (RND.nextInt(9)) {
            case 0:
                return "key";
            case 1:
                return "consumable";
            case 2:
                return "weapon";
            case 3:
                return "helmet";
            case 4:
                return "chestwear";
            case 5:
                return "armwear";
            case 6:
                return "gloves";
            case 7:
                return "pants";
            case 8:
                return "shoes";
        }
        return "errorItemNotFound";
    }

    // EFFECTS: Randomly selects item epithet
    public String selectEpithet() {
        switch (RND.nextInt(9)) {
            case 0:
                return " of Osuvox";
            case 1:
                return " of Northumbria";
            case 2:
                return " imbued with redness";
            case 3:
                return " of Great Happiness";
            case 4:
                return " of Luck";
            case 5:
                return " that happen to be broken";
            case 6:
                return " of Geminia";
            case 7:
                return " of Durin";
            case 8:
                return " of Oddsfolk";
        }
        return "errorNameNotFound";
    }

    // EFFECTS: Randomly selects item prefix
    public String selectPrefix() {
        switch (RND.nextInt(9)) {
            case 0:
                return "Dark ";
            case 1:
                return "Light ";
            case 2:
                return "Heavy ";
            case 3:
                return "Eldritch ";
            case 4:
                return "Heretical ";
            case 5:
                return "Bard's ";
            case 6:
                return "Holy ";
            case 7:
                return "Wicked ";
            case 8:
                return "Blood-stained ";
        }
        return "errorNameNotFound";
    }

    // MODIFIES: This
    // EFFECTS: Creates a set of test items
    public void setOfTestItems() {
        items.add(new Item("key", "item_no_effect", 20,20));
        items.add(new Item("key", "item_no_effect1", -20,20));
        items.add(new Item("key", "item_no_effect2", -20,-20));
    }

    // REQUIRES: type is key, consumable, weapon, helmet, chestwear,
    //           armwear, gloves, pants, or shoes
    //           name is a valid item name under its respective type
    // MODIFIES: This
    // EFFECTS: Generates an item at a random location
    public void generateItemAtRandPos(String type, String name) {
        int x = RND.nextInt(WIDTH) - WIDTH / 2;
        int y = RND.nextInt(HEIGHT) - HEIGHT / 2;
        if ((overlapWithFixture(x, y)) || (overlapWithItem(x, y))) {
            //generateItemAtRandPos(type, name);
        } else {
            items.add(new Item(type, name, x, y));
            EventLog.getInstance().logEvent(new Event("Following item was generated at x = " + x + " and y = "
                    + y + " in current room: "
                    + name));
        }
    }

    // EFFECTS: Returns true if item is overlapping with a fixture
    //          else false
    public Boolean overlapWithFixture(int x, int y) {
        for (Fixture f : fixtures) {
            if ((f.getXPos1() < x + ITEM_SIZE / 2)
                    && (x - ITEM_SIZE / 2 < f.getXPos2())
                    && (f.getYPos1() < y + ITEM_SIZE / 2)
                    && (y - ITEM_SIZE / 2 < f.getYPos2())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns true if item is overlapping with another item
    //          else false
    public Boolean overlapWithItem(int x, int y) {
        for (Item i : items) {
            if ((i.getXPos() < x + ITEM_SIZE)
                    && (x < i.getXPos() + ITEM_SIZE)
                    && (i.getYPos() < y + ITEM_SIZE)
                    && (y < i.getYPos() + ITEM_SIZE)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns contents of room as a JSON array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("room-fixtures", fixturesToJson(fixtures));
        json.put("room-items", itemsToJson(items));
        json.put("room-creatures", creaturesToJson(creatures));
        json.put("room-type", type);
        return json;
    }

    // EFFECTS: returns fixtures in fixtures as a JSON array
    private JSONArray creaturesToJson(ArrayList<Creature> creatures) {
        JSONArray jsonArray = new JSONArray();

        for (Creature c : creatures) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns fixtures in fixtures as a JSON array
    private JSONArray fixturesToJson(ArrayList<Fixture> fixtures) {
        JSONArray jsonArray = new JSONArray();

        for (Fixture f : fixtures) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns items in items as a JSON array
    private JSONArray itemsToJson(ArrayList<Item> items) {
        JSONArray jsonArray = new JSONArray();

        for (Item i : items) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: Draws a room
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        for (Fixture f : fixtures) {
            f.draw(g);
        }
        for (Item i : items) {
            i.draw(g);
        }
        //for (Creature c : creatures) {
        //    c.draw(g);
        //}
        g.setColor(origColor);
    }

    public ArrayList<Fixture> getFixtures() {
        return fixtures;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public String getType() {
        return this.type;
    }
}

package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/*
 * Represents a Playable Scene Manager
 */
public class PlayableSceneManager {
    /*
     * Represents window where player can move and
     * interact; the top-down adventure aspect of
     * WhiteSpace
     */

    private static final Random RND = WhiteSpaceGame.RND;

    private WhiteSpaceGame game;
    private final Player player;
    private final List<Item> toRemove;
    private List<Room> map;
    private Room currentRoom;
    private int roomIndicator = 0;
    private int playerLeft = 0;
    private int playerRight = 0;
    private int playerUp = 0;
    private int playerDown = 0;

    public PlayableSceneManager() {
        player = new Player(0,0);
        map = new ArrayList<>();
        toRemove = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon1' was generated"));
    }

    public void setGame(WhiteSpaceGame game) {
        this.game = game;
    }

    // EFFECTS: Sets current room and adds to map
    public void setCurrentRoom(Room room) {
        currentRoom = room;
        map.add(currentRoom);
    }

    // EFFECTS: Sets room indicator index and changes current room match
    public void setRoomIndicator(int roomIndicator) {
        this.roomIndicator = roomIndicator;
        setCurrentRoom(map.get(roomIndicator));
    }

    // MODIFIES: This, player, currentRoom
    // EFFECTS: Update player movements, removes items already picked up,
    //          clears the list removeAll, and resets variables
    public void update() {
        player.calculateMovement(playerLeft, playerRight, playerUp, playerDown);
        player.update();
        // Check if player is colliding for each collidable
        // present in current room
        for (Fixture i : currentRoom.getFixtures()) {
            if (i.getCollidable() && player.touchingFixture(i)) {
                player.collision(i);
            }
        }
        // Check if player is close enough to a hostile to
        // trigger combat
        for (Creature c : currentRoom.getCreatures()) {
            if (c.getHostile()) {
                if ((abs(c.getXPos() - player.getXPos()) - player.SIZE_X / 2 < 50)
                        && (abs(c.getYPos() - player.getYPos()) - player.SIZE_Y / 2 < 50)) {
                    game.setSceneToPlay("combat");
                    game.combatManager.startCombat(c);
                }
            }
        }
        currentRoom.getItems().removeAll(toRemove);
        resetVariables();
    }

    // MODIFIES: This
    // EFFECTS: Manages key presses in playable scenes;
    //          If WASD or arrow keys pressed, set corresponding variable
    //          If F pressed, pick up nearby item
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                playerLeft = 1;
                break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                playerRight = 1;
                break;
            case KeyEvent.VK_UP: case KeyEvent.VK_W:
                playerUp = 1;
                break;
            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                playerDown = 1;
                break;
            case KeyEvent.VK_F:
                playerPerformPickUp();
                break;
            case KeyEvent.VK_E:
                playerPerformInteract();
                break;
            case KeyEvent.VK_Q:
                player.changeInventoryVisibility();
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: scans room of if player touching any interactable
    //          fixture and plays the interaction
    private void playerPerformInteract() {
        for (Fixture f : currentRoom.getFixtures()) {
            if (!(f.getType() == "wall") && !(f.getType() == "floor") && player.touchingFixture(f)) {
                // Substring listed below is overarching name of interactable
                switch (f.getType().substring(0,4)) {
                    case "gate":
                        performGate(f.getType().substring(4));
                        break;
                }
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Changes currentRoom
    private void performGate(String gateID) {
        switch (gateID) {
            case "Top":
                roomIndicator += 1;
                if (roomIndicator == map.size()) {
                    addNewRoomToMapFromTop();
                }
                setCurrentRoom(map.get(roomIndicator));
                player.setXPos(0);
                player.setYPos(225);
                break;
            case "Bot":
                if (roomIndicator == 0) {
                    roomIndicator = 0;
                    addNewRoomToMapFromBot();
                } else {
                    roomIndicator -= 1;
                }
                setCurrentRoom(map.get(roomIndicator));
                player.setXPos(0);
                player.setYPos(-225);
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Adds a random new room to the map
    private void addNewRoomToMapFromTop() {
        switch (RND.nextInt(3)) {
            case 0:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon1' was generated"));
                map.add(new Room("Dungeon1"));
                break;
            case 1:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon2' was generated"));
                map.add(new Room("Dungeon2"));
                break;
            case 2:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon3' was generated"));
                map.add(new Room("Dungeon3"));
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Adds a random new room to the map
    private void addNewRoomToMapFromBot() {
        switch (RND.nextInt(3)) {
            case 0:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon1' was generated"));
                map.add(0, new Room("Dungeon1"));
                break;
            case 1:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon2' was generated"));
                map.add(0, new Room("Dungeon2"));
                break;
            case 2:
                EventLog.getInstance().logEvent(new Event("New room of type 'Dungeon3' was generated"));
                map.add(0, new Room("Dungeon3"));
                break;
        }
    }

    // MODIFIES: player, currentRoom
    // EFFECTS: scans room for if player touching any item
    //          adds item to player's inventory, then removes
    //          item from current room
    private void playerPerformPickUp() {
        for (Item i : currentRoom.getItems()) {
            if (player.touchingItem(i)) {
                player.addItem(i);
                EventLog.getInstance().logEvent(new Event("Following item was added to player's inventory: "
                        + i.getName()));
                toRemove.add(i);
            }
        }
    }

    // REQUIRES: creature exists in currentRoom
    // MODIFIES: currentRoom
    // EFFECTS: Removes creature from currentRoom
    public void kill(Creature creature) {
        currentRoom.getCreatures().remove(creature);
    }

    // MODIFIES: This
    // EFFECTS: Resets variables
    public void resetVariables() {
        playerLeft = 0;
        playerRight = 0;
        playerUp = 0;
        playerDown = 0;
    }

    // EFFECTS: returns items in inventory as a JSON array
    public JSONArray mapToJson(List<Room> map) {
        JSONArray jsonArray = new JSONArray();

        for (Room r : map) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: Removes all objects within map
    public void emptyMap() {
        map.clear();
    }

    // EFFECTS: Parses rooms from JSONObject and adds them to map
    public void addRoomToMap(JSONObject room) {
        map.add(new Room(addFixtures(room.getJSONArray("room-fixtures")),
                addItems(room.getJSONArray("room-items")),
                addCreatures(room.getJSONArray("room-creatures")),
                room.getString("room-type")));
    }

    // EFFECTS: Parses fixtures from JSONObject and adds them to associated room
    public ArrayList<Fixture> addFixtures(JSONArray jsonArray) {
        ArrayList<Fixture> fixtures = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextThing = (JSONObject) json;
            fixtures.add(new Fixture(nextThing.getString("fixture-type"),
                    nextThing.getInt("fixture-xpos1"),
                    nextThing.getInt("fixture-ypos1"),
                    nextThing.getInt("fixture-xpos2"),
                    nextThing.getInt("fixture-ypos2"),
                    nextThing.getBoolean("fixture-collidable")));
        }
        return fixtures;
    }

    // EFFECTS: Parses items from JSONObject and adds them to associated room
    public ArrayList<Item> addItems(JSONArray jsonArray) {
        ArrayList<Item> items = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextThing = (JSONObject) json;
            items.add(new Item(nextThing.getString("item-type"),
                    nextThing.getString("item-name"),
                    nextThing.getInt("item-xpos"),
                    nextThing.getInt("item-ypos")));
        }
        return items;
    }

    // EFFECTS: Parses creatures from JSONObject and adds them to associated room
    public ArrayList<Creature> addCreatures(JSONArray jsonArray) {
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextThing = (JSONObject) json;
            creatures.add(new Creature(nextThing.getString("creature-name"),
                    nextThing.getString("creature-type"),
                    nextThing.getInt("creature-maxHealth"),
                    nextThing.getInt("creature-damage"),
                    nextThing.getInt("creature-walkspeed"),
                    nextThing.getInt("creature-sight"),
                    nextThing.getBoolean("creature-hostile"),
                    nextThing.getInt("creature-xpos"),
                    nextThing.getInt("creature-ypos"),
                    nextThing.getInt("creature-accuracy"),
                    nextThing.getInt("creature-dodge"),
                    nextThing.getInt("creature-armor"),
                    nextThing.getInt("creature-catchchance")));
        }
        return creatures;
    }

    // EFFECTS: Draws playable scene
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WhiteSpaceGame.WIDTH, WhiteSpaceGame.HEIGHT);
        currentRoom.draw(g);
        player.draw(g);
        g.setColor(origColor);
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerLeft() {
        return playerLeft;
    }

    public int getPlayerRight() {
        return playerRight;
    }

    public int getPlayerUp() {
        return playerUp;
    }

    public int getPlayerDown() {
        return playerDown;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Room> getMap() {
        return map;
    }

    public int getRoomIndicator() {
        return roomIndicator;
    }

    public List<Item> getToRemove() {
        return toRemove;
    }
}

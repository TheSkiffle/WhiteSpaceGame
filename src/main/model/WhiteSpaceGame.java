package model;

import org.json.JSONArray;
import org.json.JSONObject;
import ui.Console;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Represents a WhiteSpace Game
 */
public class WhiteSpaceGame {
    /*
     * Contains methods all types of scenes should be able
     * to access (e.g. access escape menu, access player
     * inventory, etc.)
     */
    public static final Random RND = new Random();
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    public final PlayableSceneManager playManager = new PlayableSceneManager();
    public final CutSceneManager cutManager = new CutSceneManager();
    public final CombatSceneManager combatManager = new CombatSceneManager();

    private Console console;
    private final Player player = playManager.getPlayer();
    private String sceneToPlay;

    public WhiteSpaceGame() {
        sceneToPlay = "cutscene";
        cutManager.injectGame(this);
        injectPlayer();
        injectGame();
    }

    // REQUIRES: sceneToPlay must be one of the switch cases
    //           in this.update()
    // MODIFIES: This
    // EFFECTS: Sets sceneToPlay
    public void setSceneToPlay(String str) {
        sceneToPlay = str;
    }

    // MODIFIES: cutManager, combatManager
    // EFFECTS: Reference injection of console to managers
    public void injectConsole(Console console) {
        this.console = console;
        cutManager.injectConsole(console);
        combatManager.setConsole(console);
    }

    // MODIFIES: combatManager
    // EFFECTS: Reference injection of player to combatManager
    public void injectPlayer() {
        combatManager.setPlayer(player);
    }

    // MODIFIES: combatManager
    // EFFECTS: Reference injection of this to combatManager
    public void injectGame() {
        combatManager.setGame(this);
        playManager.setGame(this);
    }

    // REQUIRES: sceneToPlay must be one of the switch cases
    // MODIFIES: cutSceneManager, playableSceneManager, combatSceneManager
    // EFFECTS: Update manager for sceneToPlay every tick
    public void update() {
        switch (sceneToPlay) {
            case "cutscene":
                cutManager.update();
                break;
            case "playable":
                playManager.update();
                break;
            case "combat":
                combatManager.update();
                break;
        }
    }

    // REQUIRES: keyCode must be equivalent to a key event code
    // MODIFIES: CUT_MANAGER, PLAY_MANAGER, COMBAT_MANAGER
    // EFFECTS: Passes press codes to respective scene manager
    public void keyPressed(int keyCode) {
        switch (sceneToPlay) {
            case "cutscene":
                cutManager.keyPressed(keyCode);
                break;
            case "playable":
                playManager.keyPressed(keyCode);
                break;
            case "combat":
                combatManager.keyPressed(keyCode);
                break;
        }
    }

    public void draw(Graphics g) {
        switch (sceneToPlay) {
            case "cutscene":
                cutManager.draw(g);
                break;
            case "playable":
                playManager.draw(g);
                break;
            case "combat":
                //combatManager.draw(g);
                break;
        }
    }

    // REQUIRES: creature exists in current room of playManager
    // MODIFIES: playManager
    // EFFECTS: Removes creature from currentRoom
    public void kill(Creature creature) {
        playManager.kill(creature);
    }

    // EFFECTS: returns contents of game as a JSON Array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player-xpos", player.getXPos());
        json.put("player-ypos", player.getYPos());
        json.put("player-inventory", inventoryToJson(player.getInventory()));
        json.put("psm-map", playManager.mapToJson(playManager.getMap()));
        json.put("psm-roomIndicator", playManager.getRoomIndicator());
        return json;
    }

    // EFFECTS: returns items in inventory as a JSON array
    private JSONArray inventoryToJson(ArrayList<Item> inventory) {
        JSONArray jsonArray = new JSONArray();

        for (Item i : inventory) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: playManager, player
    // EFFECTS: Parses and replaces all dynamic fields from JSON object
    //          with data in whitespacegame.json
    public void fillWithLoadedData(JSONObject jsonObject) {
        player.setXPos(jsonObject.getInt("player-xpos"));
        player.setYPos(jsonObject.getInt("player-ypos"));

        JSONArray jsonArrayInventory = jsonObject.getJSONArray("player-inventory");
        for (Object json : jsonArrayInventory) {
            JSONObject nextThing = (JSONObject) json;
            addItem(new Item(nextThing.getString("item-type"),
                    nextThing.getString("item-name"),
                    nextThing.getInt("item-xpos"),
                    nextThing.getInt("item-ypos")));
        }

        JSONArray jsonArrayMap = jsonObject.getJSONArray("psm-map");
        playManager.emptyMap();
        for (Object json : jsonArrayMap) {
            JSONObject nextThingy = (JSONObject) json;
            playManager.addRoomToMap(nextThingy);
        }

        playManager.setRoomIndicator(jsonObject.getInt("psm-roomIndicator"));
    }

    public void addItem(Item item) {
        player.addItem(item);
    }

    public Player getPlayer() {
        return player;
    }

    public String getSceneToPlay() {
        return sceneToPlay;
    }

    public PlayableSceneManager getPlayableSceneManager() {
        return playManager;
    }

    public CombatSceneManager getCombatSceneManager() {
        return combatManager;
    }

    public CutSceneManager getCutSceneManager() {
        return cutManager;
    }

    public Console getConsole() {
        return console;
    }
}

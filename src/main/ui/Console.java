package ui;

import model.Player;
import model.WhiteSpaceGame;

/*
 * Represents all console displays and receives console commands
 */
public class Console {
    private WhiteSpaceGame whiteSpaceGame;
    private Player player;

    public Console(WhiteSpaceGame game) {
        whiteSpaceGame = game;
        player = whiteSpaceGame.getPlayer();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWhiteSpaceGame(WhiteSpaceGame whiteSpaceGame) {
        this.whiteSpaceGame = whiteSpaceGame;
    }

    // EFFECTS: A temporary method to observe variables in
    //          the absence of the availability of java.graphics
    public void update() {
        switch (whiteSpaceGame.getSceneToPlay()) {
            case "cutscene":
                // Does nothing
                break;
            case "playable":
                System.out.print("\r");
                System.out.print("Player X-Coordinate: ");
                System.out.print(player.getXPos());
                System.out.print(" | ");
                System.out.print("Player Y-Coordinate: ");
                System.out.print(player.getYPos());
                System.out.print(" | ");
                System.out.print("Player Inventory: ");
                System.out.print(player.printInventory());
                break;
            case "combat":
                // Does nothing
                break;
        }
    }

    // EFFECTS: prints on top of pre-existing line
    public void print(String str) {
        System.out.print("\r");
        System.out.print(str);
    }
}

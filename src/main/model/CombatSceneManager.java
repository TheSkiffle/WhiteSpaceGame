package model;

import ui.Console;
import ui.WhiteSpace;

import java.util.Random;

import static java.awt.event.KeyEvent.*;

/*
 * Represents a Combat Scene Manager
 */
public class CombatSceneManager {
    /*
     * Represents window where player is attacking
     * an opponent; the traditional RPG turn-based
     * combat aspect of WhiteSpace
     */

    private static final Random RND = WhiteSpaceGame.RND;

    private WhiteSpaceGame game;
    private Console console;
    private Player player;
    private Creature opponent;
    private Boolean playerTurn;
    private Boolean openSkillList;
    private Boolean openItemList;
    private String turnMenuSelection;
    private int skillSelector;
    private int itemSelector;
    private int numSkills;
    private int numItems;
    private int playerDmgFactor;
    private int enemyDmgFactor;
    private int playerHealth;
    private int enemyHealth;

    public CombatSceneManager() {
        playerTurn = true;
        openSkillList = false;
        openItemList = false;
        skillSelector = 0;
        itemSelector = 0;
        playerDmgFactor = 1;
        enemyDmgFactor = 1;
    }

    // MODIFIES: This
    // EFFECTS: Sets player and pulls number of skills and items
    public void setPlayer(Player player) {
        this.player = player;
        numSkills = player.getSkills().size();
        numItems = player.printInventory().length();
    }

    public void setGame(WhiteSpaceGame game) {
        this.game = game;
    }

    public void setOpponent(Creature creature) {
        opponent = creature;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    // REQUIRES: enemy.getHostile == true
    // MODIFIES: This
    // EFFECTS: Begins combat with an enemy by setting the opponent, pulling
    //          the health values and calling console to print display
    public void startCombat(Creature enemy) {
        setOpponent(enemy);
        playerHealth = player.getHealth();
        enemyHealth = opponent.getHealth();
        playerTurn = true;
        console.print("Player's turn: attack(1)  skill(2)  item(3)  flee(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
    }

    // MODIFIES: This
    // EFFECTS: Monitors whether at any point it is not the player's turn,
    //          or if player won/lost
    public void update() {
        if (playerHealth <= 0) {
            loss();
        }
        if (enemyHealth <= 0) {
            win();
        }
        if (!playerTurn) {
            enemyTurn();
        }
    }

    // MODIFIES: player, opponent, game
    // EFFECTS: Returns to playable scene and most recent room
    private void win() {
        player.setHealth(playerHealth);
        game.kill(opponent);
        game.setSceneToPlay("playable");
    }

    // EFFECTS: Turns off game
    private void loss() {
        // Lose Screen
        System.exit(0); // This is a temporary measure
    }

    // MODIFIES: player, opponent, game
    // EFFECTS: Returns to playable scene and most recent room
    private void flee() {
        player.setHealth(playerHealth);
        opponent.setHealth(enemyHealth);
        game.setSceneToPlay("playable");
    }

    // MODIFIES: This
    // EFFECTS: Plays enemy turn, making them perform a basic attack
    private void enemyTurn() {
        if (RND.nextInt(100) > opponent.getAccuracy() - player.getDodge() * 10) {
            playerHealth -= opponent.getDamage() * enemyDmgFactor * (10 - player.getArmor()) / 10;
        } else {
            // Missed
        }

        console.print("Player's turn: attack(1)  skill(2)  item(3)  flee(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
        playerTurn = true;
    }

    // MODIFIES: This
    // EFFECTS: Handles key presses and passes them to current
    //          selection screen
    public void keyPressed(int keyCode) {
        if (playerTurn) {
            if (!openSkillList && !openItemList) {
                regularSelection(keyCode);
            } else if (openSkillList) {
                skillSelection(keyCode);
            } else if (openItemList) {
                itemSelection(keyCode);
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Selects type of action to be performed in the player's
    //          phase
    public void regularSelection(int keyCode) {
        switch (keyCode) {
            case VK_1: // Attack
                attackSelected();
                break;
            case VK_2: // Use skill
                skillSelected();
                break;
            case VK_3: // Use item
                itemSelected();
                break;
            case VK_4: // Attempt to flee
                fleeSelected();
                break;
            case VK_ENTER: // Confirm selection
                confirmSelection();
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Highlights selected menu item (attack)
    private void attackSelected() {
        console.print("Player's turn: ATTACK(1)  skill(2)  item(3)  flee(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
        turnMenuSelection = "attack";
    }

    // MODIFIES: This
    // EFFECTS: Highlights selected menu item (skill)
    private void skillSelected() {
        console.print("Player's turn: attack(1)  SKILL(2)  item(3)  flee(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
        turnMenuSelection = "skill";
    }

    // MODIFIES: This
    // EFFECTS: Highlights selected menu item (item)
    private void itemSelected() {
        console.print("Player's turn: attack(1)  skill(2)  ITEM(3)  flee(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
        turnMenuSelection = "item";
    }

    // MODIFIES: This
    // EFFECTS: Highlights selected menu item (flee)
    private void fleeSelected() {
        console.print("Player's turn: attack(1)  skill(2)  item(3)  FLEE(4)  Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
        turnMenuSelection = "flee";
    }

    // MODIFIES: This
    // EFFECTS: Selects type of skill to play in player's turn
    public void skillSelection(int keyCode) {
        switch (keyCode) {
            case VK_LEFT: case VK_A:
                leftSkillSelector();
                break;
            case VK_RIGHT: case VK_D:
                rightSkillSelector();
                break;
            case VK_ENTER:
                playSkill();
                playerTurn = false;
                break;
            case VK_ESCAPE:
                openSkillList = false;
                console.print("Player's turn: attack(1)  skill(2)  item(3)  flee(4)  Player Health:"
                        + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                        + enemyHealth + "/" + opponent.getMaxHealth());
                break;
        }
        openSkillList = false;
    }

    // MODIFIES: This
    // EFFECTS: Changes to left item during skill selection
    private void leftSkillSelector() {
        if (skillSelector == 0) {
            skillSelector = numSkills - 1;
        } else {
            skillSelector -= 1;
        }
        console.print("Skill selected:" + player.getSkills().get(skillSelector).getEffect() + " Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
    }

    // MODIFIES: This
    // EFFECTS: Changes to right item during skill selection
    private void rightSkillSelector() {
        if (skillSelector == numSkills - 1) {
            skillSelector = 0;
        } else {
            skillSelector += 1;
        }
        console.print("Skill selected:" + player.getSkills().get(skillSelector).getEffect() + " Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
    }

    // MODIFIES: This
    // EFFECTS: Plays skill (contains a list of skills playable by character)
    private void playSkill() {
        switch (player.getSkills().get(skillSelector).getEffect()) {
            case "Guard":
                enemyDmgFactor -= 0.5;
                break;
        }
        playerTurn = false;
    }

    // MODIFIES: This
    // EFFECTS: Selects type of item to play in player's turn
    private void itemSelection(int keyCode) {
        switch (keyCode) {
            case VK_LEFT: case VK_A:
                leftItemSelector();
                break;
            case VK_RIGHT: case VK_D:
                rightItemSelector();
                break;
            case VK_ENTER:
                playItem();
                playerTurn = false;
                break;
            case VK_ESCAPE:
                openItemList = false;
                console.print("Player's turn: attack(1)  skill(2)  item(3)  flee(4)  Player Health:"
                        + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                        + enemyHealth + "/" + opponent.getMaxHealth());
                break;
        }
        openItemList = false;
    }

    // MODIFIES: This
    // EFFECTS: Changes to left item during item selection
    private void leftItemSelector() {
        if (itemSelector == 0) {
            itemSelector = numItems - 1;
        } else {
            itemSelector -= 1;
        }
        console.print("Item selected:" + player.getInventory().get(itemSelector).getName() + " Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
    }

    // MODIFIES: This
    // EFFECTS: Changes to right item during item selection
    private void rightItemSelector() {
        if (itemSelector == numItems - 1) {
            itemSelector = 0;
        } else {
            itemSelector += 1;
        }
        console.print("Item selected:" + player.getInventory().get(itemSelector).getName() + " Player Health:"
                + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                + enemyHealth + "/" + opponent.getMaxHealth());
    }

    // MODIFIES: This
    // EFFECTS: Plays item
    private void playItem() {
        // stub
        playerTurn = false;
    }

    // MODIFIES: This
    // EFFECTS: Confirms item from regular menu selection
    public void confirmSelection() {
        switch (turnMenuSelection) {
            case "attack":
                confirmAttack();
                break;
            case "skill":
                confirmSkill();
                break;
            case "item":
                confirmItem();
                break;
            case "flee":
                confirmFlee();
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Calculates a basic attack performed by the player
    private void confirmAttack() {
        if (RND.nextInt(100) < player.getAccuracy() - opponent.getDodge() * 10) {
            enemyHealth -= player.getDamage() * playerDmgFactor * (10 - opponent.getArmor()) / 10;
        } else {
            // Missed
        }
        playerTurn = false;
    }

    // MODIFIES: This
    // EFFECTS: Changes to skill selection screen
    private void confirmSkill() {
        if (!player.getSkills().isEmpty()) {
            openSkillList = true;
            console.print("Skill selected:" + player.getSkills().get(skillSelector).getEffect() + " Player Health:"
                    + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                    + enemyHealth + "/" + opponent.getMaxHealth());
        }
    }

    // MODIFIES: This
    // EFFECTS: Changes to item selection screen
    private void confirmItem() {
        if (!player.getInventory().isEmpty()) {
            openItemList = true;
            console.print("Item selected:" + player.getInventory().get(itemSelector).getName() + " Player Health:"
                    + playerHealth + "/" + player.getMaxHealth() + " Enemy Health:"
                    + enemyHealth + "/" + opponent.getMaxHealth());
        }
    }

    // MODIFIES: This
    // EFFECTS: Calculates flee chance and if flee success, performs
    //          flee action
    private void confirmFlee() {
        if (RND.nextInt(100) > opponent.getCatchChance()) {
            flee();
        } else {
            playerTurn = false;
        }
    }
}

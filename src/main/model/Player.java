package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.*;

/*
 * Represents a Player
 */
public class Player {
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 40;
    private static final int INTERVAL = 8;

    private static final int PICKUP_RANGE = 10;
    private final ArrayList<Item> inventory;
    private final ArrayList<Skill> skills;
    private int maxHealth = 20;
    private int walkSpeed = 4;
    private int health;
    private int damage;
    private int hmove = 0;
    private int vmove = 0;
    private int xpos;
    private int ypos;
    private int accuracy = 80;
    private int dodge = 1;
    private int armor = 0;
    private int facing = 1;
    private int timerIndex = 0;
    private int spriteIndex = 0;
    private Boolean playerMovingBool = false;
    private Boolean inventoryVisibility = false;

    private Image idle0;
    private Image idle1;
    private Image idle2;
    private Image idle3;
    private Image walking0;
    private Image walking1;
    private Image walking2;
    private Image walking3;

    // REQUIRES: -WhiteSpaceGame.WIDTH/2 < x < WhiteSpaceGame.WIDTH/2
    //           -WhiteSpaceGame.HEIGHT/2 < y < WhiteSpaceGame.HEIGHT/2
    public Player(int x, int y) {
        inventory = new ArrayList<>();
        skills = new ArrayList<>();
        health = maxHealth;
        damage = 10;
        xpos = x;
        ypos = y;
        skills.add(new Skill("guard", 0, false));
        setImages();
    }

    @SuppressWarnings("methodlength")
    private void setImages() {
        try {
            idle0 = ImageIO.read(
                    new File("./data/sprites/Player/IdleCycle/08ada6cd-0357-4d71-a452-657f93e6b2a7.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            idle1 = ImageIO.read(
                    new File("./data/sprites/Player/IdleCycle/edbad6f4-1dbc-4dc2-8f2b-8f8272cd70f9.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            idle2 = ImageIO.read(
                    new File("./data/sprites/Player/IdleCycle/f68cd4d7-5245-4c00-8f8c-1d9e4d158efe.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            idle3 = ImageIO.read(
                    new File("./data/sprites/Player/IdleCycle/feeda174-55b6-472a-ac21-ee27ddbffd43.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            walking3 = ImageIO.read(
                    new File("./data/sprites/Player/WalkingCycle/6df1f267-216b-4423-a84a-e9e245660bee.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            walking2 = ImageIO.read(
                    new File("./data/sprites/Player/WalkingCycle/47593315-8f4a-4cc9-9e04-1580e709bc9a.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            walking1 = ImageIO.read(
                    new File("./data/sprites/Player/WalkingCycle/d6492a25-4b4a-42b2-ac02-3f5b40c4dbe3.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
            walking0 = ImageIO.read(
                    new File("./data/sprites/Player/WalkingCycle/f3a4d7b2-8082-4f64-8db7-dc628bbbfc81.png"))
                    .getScaledInstance(50, 60, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            // Should not reach
        }
    }

    // REQUIRES: -WhiteSpaceGame.WIDTH/2 < x < WhiteSpaceGame.WIDTH/2
    public void setXPos(int xpos) {
        this.xpos = xpos;
    }

    // REQUIRES: -WhiteSpaceGame.HEIGHT/2 < y < WhiteSpaceGame.HEIGHT/2
    public void setYPos(int ypos) {
        this.ypos = ypos;
    }

    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPlayerMovingBool(Boolean b) {
        playerMovingBool = b;
    }

    public void update() {
        timerIndex += 1;
    }

    // REQUIRES: left, right, up, and down are either 0 or 1
    // MODIFIES: This
    // EFFECTS: Calculates player movement while ensuring
    //          diagonal movement doesn't have greater
    //          walk speed than in the cardinal directions
    public void calculateMovement(int left, int right, int up, int down) {
        hmove = right - left;
        vmove = down - up;

        if (hmove != 0) {
            facing = hmove;
        }

        if ((hmove != 0) || (vmove != 0)) {
            playerMovingBool = true;
            // Get direction of movement
            double direction = atan2(vmove, hmove);

            // Apply movement by each axis' component
            xpos += (int)round(cos(direction) * walkSpeed);
            ypos += (int)round(sin(direction) * walkSpeed);
        }
    }

    // MODIFIES: This
    // EFFECTS: To be run on collision; moves player
    //          backwards until not clipped in collidable
    //          fixture
    public void collision(Fixture collidable) {
        // Set target values
        int targetx = xpos;
        int targety = ypos;

        // Move back, presumably out of collision
        if ((hmove != 0) || (vmove != 0)) {
            double direction = atan2(vmove, hmove);

            xpos -= (int)round(cos(direction) * walkSpeed);
            ypos -= (int)round(sin(direction) * walkSpeed);
        }

        // Find distance we want to move
        int distancex = abs(targetx - xpos);
        int distancey = abs(targety - ypos);

        // Move as far as possible in x until touching collidable
        for (int i = 0; i < distancex; i++) {
            if (touchingFixture(collidable)) {
                xpos += signum(targetx - xpos);
            }
        }

        // Move as far as possible in y until touching collidable
        for (int i = 0; i < distancey; i++) {
            if (touchingFixture(collidable)) {
                ypos += signum(targety - ypos);
            }
        }
    }

    // EFFECTS: Return true if player is touching a fixture
    //          else false
    public Boolean touchingFixture(Fixture c) {
        return (c.getXPos1() < xpos + SIZE_X / 2)
                && (xpos - SIZE_X / 2 < c.getXPos2())
                && (c.getYPos1() < ypos + SIZE_Y / 2)
                && (ypos - SIZE_Y / 2 < c.getYPos2());
    }

    // EFFECTS: Return true if player is touching an item
    //          else false
    public Boolean touchingItem(Item i) {
        return (xpos + SIZE_X / 2 + PICKUP_RANGE > i.getXPos())
                && (i.getXPos() > xpos - SIZE_X / 2 - PICKUP_RANGE)
                && (ypos + SIZE_Y / 2 + PICKUP_RANGE > i.getYPos())
                && (i.getYPos() > ypos - SIZE_Y / 2 - PICKUP_RANGE);
    }

    // MODIFIES: This
    // EFFECTS: Adds item in player's inventory
    public void addItem(Item i) {
        inventory.add(i);
    }

    // MODIFIES: This
    // EFFECTS: Adds skill in player's list of skills
    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    // EFFECTS: returns contents of inventory in a string
    //          returns "Empty" if no item in inventory
    public String printInventory() {
        if (inventory.isEmpty()) {
            return "Empty";
        } else {
            /*String itemListString = inventory.get(0).getName();
            * for (int i = 1; i < inventory.size(); i++) {
            *     itemListString = itemListString + ", " + inventory.get(i).getName();
            * }
            * return itemListString;
            */

            StringBuilder output = new StringBuilder(110);
            output.append(inventory.get(0).getName());
            for (int i = 1; i < inventory.size(); i++) {
                output.append(", ");
                output.append(inventory.get(i).getName());
            }
            return output.toString();
        }
    }

    // EFFECTS: Draws player
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        if (!playerMovingBool) {
            drawIdle(g);
        } else {
            drawWalking(g);
        }
        if (timerIndex >= INTERVAL) {
            timerIndex = 0;
            if (spriteIndex == 3) {
                spriteIndex = 0;
                playerMovingBool = false;
            } else {
                spriteIndex += 1;
            }
        }
        if (inventoryVisibility) {
            drawInventory(g);
        }
        g.setColor(origColor);
    }

    // EFFECTS: Draws idle player animation frame depending on spriteIndex
    private void drawIdle(Graphics g) {
        int offsetValue = 0;
        if (facing != 1) {
            offsetValue = 50;
        } else {
            offsetValue = 0;
        }
        Color origColor = g.getColor();
        switch (spriteIndex) {
            case 0: g.drawImage(idle0, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 1: g.drawImage(idle1, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 2: g.drawImage(idle2, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 3: g.drawImage(idle3, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
        }
        g.setColor(origColor);
    }

    // EFFECTS: Draws walking player animation frame depending on spriteIndex
    private void drawWalking(Graphics g) {
        int offsetValue;
        if (facing != 1) {
            offsetValue = 50;
        } else {
            offsetValue = 0;
        }
        Color origColor = g.getColor();
        switch (spriteIndex) {
            case 0: g.drawImage(walking0, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 1: g.drawImage(walking1, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 2: g.drawImage(walking2, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
            case 3: g.drawImage(walking3, xpos + WhiteSpaceGame.WIDTH / 2 - 25 + offsetValue,
                        ypos + WhiteSpaceGame.HEIGHT / 2 - 40, 50 * facing, 60, null);
                break;
        }
        g.setColor(origColor);
    }

    // EFFECTS: Draws contents of inventory
    // NOTES: Eventually, items should be displayed as distinct from eachother
    private void drawInventory(Graphics g) {
        Color origColor = g.getColor();
        int hspacing = 0;
        int vspacing = 0;
        for (Item i : inventory) {
            g.setColor(Color.BLACK);
            g.fillRect(30 + vspacing, 30 + hspacing, 18, 18);
            g.setColor(Color.YELLOW);
            g.fillRect(34 + vspacing, 34 + hspacing, 10, 10);
            g.setColor(Color.GRAY);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g.drawString(i.getType(), 30 + vspacing, 30 + hspacing + 25);
            hspacing += 30;
            if (hspacing >= 700) {
                hspacing = 0;
                vspacing += 60;
            }
        }
        g.setColor(origColor);
    }

    public void changeInventoryVisibility() {
        inventoryVisibility = !inventoryVisibility;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public int getXPos() {
        return xpos;
    }

    public int getYPos() {
        return ypos;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getDodge() {
        return dodge;
    }

    public int getArmor() {
        return armor;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}

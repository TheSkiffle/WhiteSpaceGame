package model;

import org.json.JSONObject;

/*
 * Represents a Creature
 */
public class Creature {
    private final String name;
    private final String type;
    private final int maxHealth;
    private final int damage;
    private final int walkSpeed;
    private final int sight;
    private int health;
    private int xpos;
    private int ypos;
    private Boolean hostile;
    private int accuracy;
    private int dodge;
    private int armor;
    private int catchChance;

    public Creature(String name, String type, int maxHealth, int damage, int walkSpeed, int sight, Boolean hostile,
                    int xpos, int ypos, int accuracy, int dodge, int armor, int catchChance) {
        this.name = name;
        this.type = type;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.walkSpeed = walkSpeed;
        this.sight = sight;
        health = this.maxHealth;
        this.xpos = xpos;
        this.ypos = ypos;
        this.hostile = hostile;
        this.accuracy = accuracy;
        this.dodge = dodge;
        this.armor = armor;
        this.catchChance = catchChance;
    }

    // EFFECTS: Writes JSON representation of creature to file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("creature-type", type);
        json.put("creature-name", name);
        json.put("creature-maxHealth", maxHealth);
        json.put("creature-damage", damage);
        json.put("creature-walkspeed", walkSpeed);
        json.put("creature-sight", sight);
        json.put("creature-health", health);
        json.put("creature-xpos", xpos);
        json.put("creature-ypos", ypos);
        json.put("creature-hostile", hostile);
        json.put("creature-accuracy", accuracy);
        json.put("creature-dodge", dodge);
        json.put("creature-armor", armor);
        json.put("creature-catchchance", catchChance);
        return json;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public int getSight() {
        return sight;
    }

    public int getHealth() {
        return health;
    }

    public Boolean getHostile() {
        return hostile;
    }

    public int getXPos() {
        return xpos;
    }

    public int getYPos() {
        return ypos;
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

    public int getCatchChance() {
        return catchChance;
    }
}

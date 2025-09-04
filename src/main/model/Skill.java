package model;

/*
 * Represents a Skill
 */
public class Skill {
    /*
     * Represents a skill usable by the
     * player during Playable Scenes
     */

    private String effect;
    private int numModifier;
    private Boolean passive;

    public Skill(String effect, int numModifier, Boolean passive) {
        this.effect = effect;
        this.numModifier = numModifier;
        this.passive = passive;
    }

    public String getEffect() {
        return effect;
    }

    public int getNumModifier() {
        return numModifier;
    }

    public Boolean getPassive() {
        return passive;
    }
}

package model;

import ui.Console;

import java.awt.*;
import java.util.ArrayList;

/*
 * Represents a cutscene
 */
public class CutScene {
    private static final int INTERVAL = 3;

    private final String dialogueID;
    private final ArrayList<String> dialogueSeq;
    private WhiteSpaceGame game;
    private Console dialoguePlayer;
    private int seqIndex;
    private int timerIndex;
    private int characterIndex;
    private String currentString = "";

    // REQUIRES: dialogueID must be one of the switch cases
    public CutScene(String dialogueID) {
        this.dialogueID = dialogueID;
        dialogueSeq = new ArrayList<>();
        seqIndex = 0;
        timerIndex = 0;
        characterIndex = 0;
        updateDialogueSeq();
    }

    public void setGame(WhiteSpaceGame game) {
        this.game = game;
    }

    public void setConsole(Console console) {
        dialoguePlayer = console;
    }

    public void setSeqIndex(int seqIndex) {
        this.seqIndex = seqIndex;
    }

    // MODIFIES: This
    // EFFECTS: Sets dialogue based on dialogueID by calling the appropriate
    //          method
    public void updateDialogueSeq() {
        // dialogueID determines which dialogue the cutscene is coded for
        // Second last line indicates type of scene to transition to
        // Last line indicates sub-scene to transition to (e.g. room)
        switch (this.dialogueID) {
            case "Waiting for James":
                dialogueWaitingForJames();
                dialogueMeetingMorrel();
                break;
        }
    }

    // MODIFIES: This
    // EFFECTS: Adds dialogue for the scene: "Waiting For James" to
    //          dialogueSeq
    private void dialogueWaitingForJames() {
        dialogueSeq.add("(Press SPACE to continue)");
        dialogueSeq.add("Jim: When do you think he's gonna come back up?");
        dialogueSeq.add("Dalia: No clue.");
        dialogueSeq.add("Dalia: What are we waiting here for? He's still the seeker you know.");
        dialogueSeq.add("Jim: Yea, but what if-");
        dialogueSeq.add("Dalia: You're worrying way too much.");
        dialogueSeq.add("Dalia: We did this because he always ignores us when we tell him"
                + " to give us more time.");
        dialogueSeq.add("Dalia: Now, we'll have a lot more time to hide.");
        dialogueSeq.add("Dalia: So stop wasting the prank and let's go!");
        dialogueSeq.add("... (image1)");
        dialogueSeq.add("... (image2)");
        dialogueSeq.add("... (image3)");
        dialogueSeq.add("... (image4)");
    }

    // MODIFIES: This
    // EFFECTS: Adds dialogue for the scene: "Waiting For James" to
    //          dialogueSeq
    @SuppressWarnings("methodlength")
    private void dialogueMeetingMorrel() {
        dialogueSeq.add("Unknown person: ...");
        dialogueSeq.add("Unknown person: aaaaaaand...");
        dialogueSeq.add("Unknown person: WELCOME, HUMAN TO THE WONDERFUL WORLD OF-");
        dialogueSeq.add("Unknown person: SOMEWHERE!!!!!");
        dialogueSeq.add("Unknown person: HOW FABULOUS! HOW SINGULAR! HOW EXCITING!");
        dialogueSeq.add("Jim: Uh. Excuse me but who are you and why can't I see anything?");
        dialogueSeq.add("Unknown person: FEAR NOT!! FOR I AM THE MAGNIFICENT MORREL!");
        dialogueSeq.add("Morrel: HERE TO ASSIST YOU AT MY OWN CONVENIENCE AND ENTERTAINMENT!!!");
        dialogueSeq.add("Jim: Why am I blind, mister...?");
        dialogueSeq.add("Morrel: YOU'RE BLIND BECAUSE-");
        dialogueSeq.add("Morrel: Wait. You're blind?");
        dialogueSeq.add("Jim: Yea.");
        dialogueSeq.add("Morrel: Oh dear. I believe I forgot to fully integrate your cortex. How silly of me."
                + " I can't believe I've done this to my very first guest. My most severest of apologies my venerable"
                + " visitant.");
        dialogueSeq.add("... (image5)");
        dialogueSeq.add("Morrel: That should do it. How do you feel?");
        dialogueSeq.add("Jim: Where am I? Is this Heaven? Did I die?");
        dialogueSeq.add("Morrel: Somewhere, no, and yes.");
        dialogueSeq.add("Jim: What.");
        dialogueSeq.add("Morrel: Oh let's move off of these bore-some topics and get to the EXCITING BITS!");
        dialogueSeq.add("Morrel: All you need to know is this is a place you don't want to be in and in order to"
                + " leave, you must find your way through these parts to successfully escape.");
        dialogueSeq.add("Morrel: However, your travels will be hampered by dreaded MONSTERS that will seek to tear"
                + " you limb from limb.");
        dialogueSeq.add("Morrel: Your journey will be one for the history books, of splendor and heroism or tragedy"
                + " depending on what you choose to do.");
        dialogueSeq.add("Morrel: Not like you have much to choose from...");
        dialogueSeq.add("Morrel: IN ANY CASE I MUST MAKE MY EXIT! MY ADVICE, MAKE HASTE! GOODBYE!");
        dialogueSeq.add("Jim: WAIT! Wait!");
        dialogueSeq.add("Jim: What even is this sh*t.");
        dialogueSeq.add("Jim: ...");
        dialogueSeq.add("Jim: I need to get out of here.");
        dialogueSeq.add("playable");
        dialogueSeq.add("Dungeon1");
    }

    // MODIFIES: This
    // EFFECTS: Moves to next string in dialogue sequence and resetting
    //          characterIndex
    public void playCutScene() {
        seqIndex += 1;
        characterIndex = 0;
    }

    // MODIFIES: This
    // EFFECTS: Increments timer index and plays dialogue sequence
    public void update() {
        timerIndex += 1;
        playDialogueSequence(timerIndex);
    }

    // MODIFIES: This
    // EFFECTS: Runs dialogue sequence by one character every couple ticks;
    //          if at last two lines of dialogue, changes scene type and subscene;
    public void playDialogueSequence(int t) {
        if (seqIndex > dialogueSeq.size() - 3) {
            switch (dialogueSeq.get(seqIndex)) {
                case "playable":
                    game.setSceneToPlay("playable");
                    game.getPlayableSceneManager().setCurrentRoom(new Room(dialogueSeq.get(seqIndex + 1)));
                    break;
                case "cutscene":
                    game.setSceneToPlay("cutscene");
                    break;
                case "combat":
                    game.setSceneToPlay("combat");
                    startFight(dialogueSeq.get(seqIndex + 1));
                    break;
            }
            return;
        }
        // If character index is not ouside of string length and INTERVAL ticks has passed,
        // then print substring from 0 to characterIndex and increment character index
        if ((t >= INTERVAL) && (characterIndex < dialogueSeq.get(seqIndex).length() + 1)) {
            currentString = dialogueSeq.get(seqIndex).substring(0, characterIndex);
            dialoguePlayer.print(currentString);
            timerIndex = 0;
            characterIndex += 1;
        }
    }

    // REQUIRES: String s must be one of the below switch cases
    // EFFECTS: Begins fight sequence
    public void startFight(String s) {
        switch (s) {
            case "combatscene1":
                game.combatManager.startCombat(new Creature("Jerrod", "TestMob", 30, 1,
                        2,5, true, 0, 0, 100, 0, 2, 20));
                break;
        }
    }

    // EFFECTS: Draws cutscene and dialogue
    public void draw(Graphics g) {
        Color origColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(currentString, 30, WhiteSpaceGame.HEIGHT - 100);
        g.setColor(origColor);
    }

    public int getInterval() {
        return INTERVAL;
    }

    public String getDialogueID() {
        return dialogueID;
    }

    public ArrayList<String> getDialogueSeq() {
        return dialogueSeq;
    }

    public int getSeqIndex() {
        return seqIndex;
    }

    public int getTimerIndex() {
        return timerIndex;
    }

    public int getCharacterIndex() {
        return characterIndex;
    }
}

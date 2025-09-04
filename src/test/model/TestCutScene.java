package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Console;

import static org.junit.jupiter.api.Assertions.*;

public class TestCutScene {
    private CutScene cutScene;

    @BeforeEach
    public void runBefore() {
        cutScene = new CutScene("Waiting for James");
    }

    // Test constructor
    @Test
    public void testConstructor() {
        assertEquals("Waiting for James", cutScene.getDialogueID());
        assertEquals(0, cutScene.getSeqIndex());
        assertEquals(0, cutScene.getTimerIndex());
        assertEquals(0, cutScene.getCharacterIndex());
        assertEquals(43, cutScene.getDialogueSeq().size());
    }

    // Test dialogue sequence updater
    @Test
    public void testUpdateDialogueSeq() {
        // Nothing to be tested yet
    }

    // Test play cutscene
    @Test
    public void testPlayCutscene() {
        cutScene.playCutScene();
        assertEquals(1, cutScene.getSeqIndex());
        assertEquals(0, cutScene.getTimerIndex());
    }

    // Test updater
    @Test
    public void testUpdate() {
        cutScene.update();
        assertEquals(1, cutScene.getTimerIndex());
    }

    // Test dialogue sequence player when at first line
    @Test
    public void testPlayDialogueSequenceFirstLine() {
        cutScene.playDialogueSequence(cutScene.getInterval() - 1);
        assertEquals(0, cutScene.getCharacterIndex());

        WhiteSpaceGame game = new WhiteSpaceGame();
        Console console = new Console(game);
        cutScene.setConsole(console);
        cutScene.playDialogueSequence(cutScene.getInterval());
        assertEquals(1, cutScene.getCharacterIndex());
        cutScene.playDialogueSequence(cutScene.getInterval() + 1);
        assertEquals(2, cutScene.getCharacterIndex());
    }

    // Test dialogue sequence player when beyond read-intended line
    @Test
    public void testPlayDialogueSequenceLastLine() {
        cutScene.setSeqIndex(cutScene.getDialogueSeq().size() - 2);
        WhiteSpaceGame game = new WhiteSpaceGame();
        cutScene.setGame(game);
        cutScene.playDialogueSequence(0);
        assertEquals("playable", game.getSceneToPlay());
        assertEquals("Dungeon1", game.getPlayableSceneManager().getCurrentRoom().getType());
    }
}

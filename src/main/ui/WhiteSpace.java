package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import model.Event;

import model.EventLog;
import model.WhiteSpaceGame;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * Represents the window wherein WhiteSpace game is played
 */
public class WhiteSpace extends JFrame {
    private static final int INTERVAL = 10;
    private static final String JSON_STORE = "./data/whitespacegame.json";
    private static WhiteSpaceGame game = new WhiteSpaceGame();
    private static Console console = new Console(game);
    private static GamePanel gp = new GamePanel(game);
    private Timer timer;
    private static JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private static JsonReader jsonReader = new JsonReader(JSON_STORE);

    // EFFECTS: Sets up window and intitializes game components as well as
    //          in-game clock
    public WhiteSpace() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event next : EventLog.getInstance()) {
                    console.print(next.toString() + "\n\n");
                }
            }
        });
        try {
            setIconImage(ImageIO.read(
                    new File("./data/sprites/Player/IdleCycle/08ada6cd-0357-4d71-a452-657f93e6b2a7.png"))
                    .getScaledInstance(100, 120, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            // Should not reach
        }
        setVariables();
    }

    // EFFECTS: Sets variables
    public void setVariables() {
        jsonReader = new JsonReader(JSON_STORE);
        game.injectConsole(console);
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
        setVisible(true);
        addTimer();
    }

    // EFFECTS: Plays the game
    public static void main(String[] args) {
        new WhiteSpace();
    }

    // EFFECTS: Creates a timer in milliseconds to update the game regularly
    private void addTimer() {
        timer = new Timer(INTERVAL, e -> {
            game.update();
            console.update();
            gp.repaint();
        });

        timer.start();
    }

    // EFFECTS: Handles key events
    private static class KeyHandler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Does nothing
        }

        @Override
        public synchronized void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_O) {
                saveGame();
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                loadGame();
            }
            game.keyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Does nothing
        }
    }

    // MODIFIES: This
    // EFFECTS: Saves all dynamic fields of WhiteSpaceGame
    private static void saveGame() {
        System.out.print("\r");
        System.out.print("Saving game...");
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
        } catch (Exception e) {
            System.exit(130);
        }
    }

    // MODIFIES: This
    // EFFECTS: Reads and loads all dynamic fields of WhiteSpaceGame from
    //          whitespacegame.json
    private static void loadGame() {
        System.out.print("\r");
        System.out.print("Loading game...");
        try {
            game = jsonReader.read();
            game.injectConsole(console);
            game.setSceneToPlay("playable");
            console.setPlayer(game.getPlayer());
            console.setWhiteSpaceGame(game);
        } catch (IOException e) {
            System.exit(130);
        }
        gp.setGame(game);
    }

}

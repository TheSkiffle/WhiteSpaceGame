package ui;

import model.WhiteSpaceGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private WhiteSpaceGame game;

    // EFFECTS: Sets size and background color and sets game
    public GamePanel(WhiteSpaceGame g) {
        setPreferredSize(new Dimension(WhiteSpaceGame.WIDTH, WhiteSpaceGame.HEIGHT));
        setBackground(Color.WHITE);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);
    }

    // MODIFIES: g
    // EFFECTS: Draws game onto g
    private void drawGame(Graphics g) {
        game.draw(g);
    }

    public void setGame(WhiteSpaceGame game) {
        this.game = game;
    }

}

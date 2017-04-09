package sim;

import quandry.QuandaryBoard;
import sim.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 08/04/17.
 */
public class Surface extends JPanel implements ActionListener {

    private final int DELAY = 500;
    private Timer timer;
    private Set<Sprite> sprites;

    public Surface() {
        initTimer();
        initSprites();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    private void initSprites() {
        sprites = new HashSet<Sprite>();

        QuandaryBoard board = new QuandaryBoard(new Point(0, 0), Sim.WIDTH, Sim.HEIGHT);
        board.initSquares();
        board.initHighlights();
        sprites.add(board);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the Sprites.
        for (Sprite sprite : sprites) {
            sprite.draw(g2d);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}

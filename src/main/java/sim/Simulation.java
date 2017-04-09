package sim;

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
public abstract class Simulation extends JPanel implements ActionListener {

    private final int DELAY = 500;
    private Timer timer;
    private Set<Sprite> sprites = new HashSet<Sprite>();

    public Simulation() {
        initTimer();
        initSim();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    abstract public void initSim();
    abstract public void draw(Graphics2D g2d);

    public Set<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(Set<Sprite> sprites) {
        this.sprites = sprites;
    }

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        draw((Graphics2D) g);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}

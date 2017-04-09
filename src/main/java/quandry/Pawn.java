package quandry;

import sim.sprite.BaseSprite;

import java.awt.*;

/**
 * Created by kimbsy on 09/04/17.
 */
public class Pawn extends BaseSprite {

    private int size;
    private Color color;

    public Pawn(Point pos, int size, Color color) {
        super(pos);
        this.size = size;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(getPos().x + (size / 4), getPos().y + (size / 4), size / 2, size / 2);
    }
}

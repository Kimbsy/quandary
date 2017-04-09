package quandry;

import sim.sprite.BaseSprite;

import java.awt.*;

/**
 * Created by kimbsy on 09/04/17.
 */
public class Highlight extends BaseSprite {

    private Color color;
    private int size;

    public Highlight(Point pos, int size, Color color) {
        super(pos);
        this.size = size;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(getPos().x + 2, getPos().y + 2, getSize() - 4, getSize() - 4);
    }
}

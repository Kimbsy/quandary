package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.sim.sprite.BaseSprite;
import com.kimbsy.sim.sprite.Sprite;

import java.awt.*;

/**
 * This class is drawn on top of a {@link Square} {@link Sprite} to draw attention to it.
 *
 * @author kimbsy
 */
public class Highlight extends BaseSprite {

    private final Point coords;
    private final int size;
    private final HighlightColor highlightColor;

    public Highlight(Point pos, Point coords, int size, HighlightColor highlightColor) {
        super(pos);
        this.coords = coords;
        this.size = size - 2;
        this.highlightColor = highlightColor;
    }

    public Point getCoords() {
        return coords;
    }

    private int getSize() {
        return size;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(highlightColor.getColor());
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(getPos().x + 2, getPos().y + 2, getSize() - 2, getSize() - 2);
    }
}

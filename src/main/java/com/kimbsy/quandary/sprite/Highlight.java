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
    private HighlightColor highlightColor;

    public Highlight(Point pos, Point coords, int size, HighlightColor highlightColor) {
        super(pos);
        this.coords = coords;
        this.size = size - 4;
        this.highlightColor = highlightColor;
    }

    public Point getCoords() {
        return coords;
    }

    public void setHighlightColor(HighlightColor highlightColor) {
        this.highlightColor = highlightColor;
    }

    public HighlightColor getHighlightColor() {
        return highlightColor;
    }

    private int getSize() {
        return size;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(highlightColor.getColor());
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(getPos().x + 4, getPos().y + 4, getSize() - 4, getSize() - 4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Highlight highlight = (Highlight) o;

        if (size != highlight.size) return false;
        if (coords != null ? !coords.equals(highlight.coords) : highlight.coords != null) return false;
        return highlightColor == highlight.highlightColor;
    }

    @Override
    public int hashCode() {
        int result = coords != null ? coords.hashCode() : 0;
        result = 31 * result + size;
        result = 31 * result + (highlightColor != null ? highlightColor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("coords", coords)
                .append("size", size)
                .append("highlightColor", highlightColor)
                .toString();
    }
}

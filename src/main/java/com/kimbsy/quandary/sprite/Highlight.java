package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.sim.sprite.BaseSprite;
import com.kimbsy.sim.sprite.Sprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    /**
     * Class constructor specifying the position, coordinates and color of the Highlight along with the size of the
     * {@link Square} it is highlighting.
     *
     * @param pos            The position of the Highlight.
     * @param coords         The board coordinates of the Highlight.
     * @param highlightColor The color of the highlight.
     * @param size           The size of the highlighted square.
     */
    Highlight(Point pos, Point coords, HighlightColor highlightColor, int size) {
        super(pos);
        this.coords = coords;
        this.highlightColor = highlightColor;
        this.size = size - 4;
    }

    /**
     * Get the coordinates of the Highlight.
     *
     * @return The board coordinates.
     */
    public Point getCoords() {
        return coords;
    }

    /**
     * Set the color of the Highlight.
     *
     * @param highlightColor The new Hilight color.
     */
    public void setHighlightColor(HighlightColor highlightColor) {
        this.highlightColor = highlightColor;
    }

    /**
     * Get the size of the Highlight {@link Sprite}.
     *
     * @return The size of the Highlight.
     */
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
        return new ToStringBuilder(this)
                .append("coords", coords)
                .append("size", size)
                .append("highlightColor", highlightColor)
                .toString();
    }
}

package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.sim.sprite.BaseSprite;
import com.kimbsy.sim.sprite.Sprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;

/**
 * @author kimbsy
 */
public class Square extends BaseSprite {

    private final Point coords;
    private final int size;
    private final SquareColor squareColor;

    public Square(Point pos, Point coords, int size, SquareColor squareColor) {
        super(pos);
        this.coords = coords;
        this.size = size;
        this.squareColor = squareColor;
    }

    public Point getCoords() {
        return coords;
    }

    private int getSize() {
        return size;
    }

    public SquareColor getSquareColor() {
        return squareColor;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(squareColor.getColor());
        g2d.fillRect(getPos().x, getPos().y, getSize(), getSize());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        if (size != square.size) return false;
        if (coords != null ? !coords.equals(square.coords) : square.coords != null) return false;
        return squareColor == square.squareColor;
    }

    @Override
    public int hashCode() {
        int result = coords != null ? coords.hashCode() : 0;
        result = 31 * result + size;
        result = 31 * result + (squareColor != null ? squareColor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("coords", coords)
                .append("size", size)
                .append("squareColor", squareColor)
                .toString();
    }
}

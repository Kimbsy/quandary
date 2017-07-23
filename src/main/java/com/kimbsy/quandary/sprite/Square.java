package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.sim.sprite.BaseSprite;
import com.kimbsy.sim.sprite.Sprite;

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

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(squareColor.getColor());
        g2d.fillRect(getPos().x, getPos().y, getSize(), getSize());
    }
}

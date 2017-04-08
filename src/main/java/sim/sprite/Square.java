package sim.sprite;

import quandry.SquareColor;

import java.awt.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public class Square extends BaseSprite {

    private SquareColor squareColor;
    private int size;

    public Square(Point pos, int size, SquareColor color) {
        super(pos);
        this.size = size;
        this.squareColor = color;
    }

    public SquareColor getSquareColor() {
        return squareColor;
    }

    public void setSquareColor(SquareColor squareColor) {
        this.squareColor = squareColor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(squareColor.getColor());
        g2d.fillRect(getPos().x, getPos().y, getSize() - 5, getSize() - 5);
    }
}

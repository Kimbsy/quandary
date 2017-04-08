package quandry;

import sim.sprite.CompoundSprite;
import sim.sprite.Sprite;
import sim.sprite.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 08/04/17.
 */
public class QuandaryBoard extends CompoundSprite {

    private static final int SIZE = 12;

    private int width, height;

    public QuandaryBoard(Point pos, int width, int height) {
        super(pos, new HashSet<Sprite>());
        this.width = width;
        this.height = height;
    }

    public void initSquares() {
        Set<Sprite> squares = new HashSet<Sprite>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Square s = new Square(getSquarePosition(i, j), width / SIZE, getSquareColor(i, j));
                squares.add(s);
            }
        }

        setChildren(squares);
    }

    private Point getSquarePosition(int i, int j) {
        int x = width / SIZE * i;
        int y = height / SIZE * j;

        return new Point(x, y);
    }

    private SquareColor getSquareColor(int i, int j) {
        return SquareColor.BLUE;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(getPos().x, getPos().y, getWidth(), getHeight());
    }
}

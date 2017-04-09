package quandry;

import sim.sprite.CompoundSprite;
import sim.sprite.Sprite;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 08/04/17.
 */
public class QuandaryBoard extends CompoundSprite {

    private static final int SIZE = 12;

    private static SquareColor[][] colors = new SquareColor[][] {
            {SquareColor.BLUE, SquareColor.ORANGE, SquareColor.GREY, SquareColor.PINK, SquareColor.YELLOW, SquareColor.PURPLE, SquareColor.GREEN, SquareColor.WHITE, SquareColor.PINK, SquareColor.GREY, SquareColor.BLUE, SquareColor.GREEN},
            {SquareColor.YELLOW, SquareColor.GREEN, SquareColor.WHITE, SquareColor.PURPLE, SquareColor.ORANGE, SquareColor.GREY, SquareColor.PINK, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.ORANGE, SquareColor.WHITE, SquareColor.YELLOW},
            {SquareColor.GREY, SquareColor.BLUE, SquareColor.PINK, SquareColor.YELLOW, SquareColor.GREEN, SquareColor.WHITE, SquareColor.PURPLE, SquareColor.GREY, SquareColor.YELLOW, SquareColor.PINK, SquareColor.GREEN, SquareColor.ORANGE},
            {SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.WHITE, SquareColor.BLUE, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.PINK, SquareColor.WHITE, SquareColor.PURPLE, SquareColor.GREY, SquareColor.BLUE},
            {SquareColor.WHITE, SquareColor.PINK, SquareColor.GREY, SquareColor.GREEN, SquareColor.PURPLE, SquareColor.GREY, SquareColor.BLUE, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.YELLOW, SquareColor.PURPLE},
            {SquareColor.ORANGE, SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.PINK, SquareColor.GREEN, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.GREY, SquareColor.WHITE, SquareColor.ORANGE, SquareColor.PINK},
            {SquareColor.YELLOW, SquareColor.GREEN, SquareColor.ORANGE, SquareColor.GREY, SquareColor.YELLOW, SquareColor.PURPLE, SquareColor.PINK, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.PINK, SquareColor.GREEN, SquareColor.WHITE},
            {SquareColor.BLUE, SquareColor.WHITE, SquareColor.PINK, SquareColor.PURPLE, SquareColor.ORANGE, SquareColor.WHITE, SquareColor.GREY, SquareColor.GREEN, SquareColor.ORANGE, SquareColor.GREY, SquareColor.BLUE, SquareColor.YELLOW},
            {SquareColor.GREY, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.WHITE, SquareColor.GREY, SquareColor.PINK, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.WHITE, SquareColor.PURPLE, SquareColor.PINK},
            {SquareColor.PURPLE, SquareColor.PINK, SquareColor.GREEN, SquareColor.ORANGE, SquareColor.YELLOW, SquareColor.GREEN, SquareColor.ORANGE, SquareColor.WHITE, SquareColor.BLUE, SquareColor.GREEN, SquareColor.GREY, SquareColor.ORANGE},
            {SquareColor.GREEN, SquareColor.GREY, SquareColor.WHITE, SquareColor.PINK, SquareColor.PURPLE, SquareColor.WHITE, SquareColor.GREY, SquareColor.YELLOW, SquareColor.PURPLE, SquareColor.ORANGE, SquareColor.PINK, SquareColor.GREEN},
            {SquareColor.ORANGE, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.BLUE, SquareColor.PINK, SquareColor.GREEN, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.GREY},
    };

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
        return QuandaryBoard.colors[j][i];
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

package quandry;

import sim.sprite.CompoundSprite;
import sim.sprite.Sprite;

import java.awt.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by kimbsy on 08/04/17.
 */
public class QuandaryBoard extends CompoundSprite {

    public enum ChildType {
        SQUARE,
        HIGHLIGHT,
        PAWN,
    }

    private static final int SIZE = 12;

    private static SquareColor[][] colors = new SquareColor[][] {
            {SquareColor.BLUE, SquareColor.YELLOW, SquareColor.GREY, SquareColor.PURPLE, SquareColor.WHITE, SquareColor.ORANGE, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.GREY, SquareColor.PURPLE, SquareColor.GREEN, SquareColor.ORANGE},
            {SquareColor.ORANGE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.YELLOW, SquareColor.PINK, SquareColor.PURPLE, SquareColor.GREEN, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.PINK, SquareColor.GREY, SquareColor.BLUE},
            {SquareColor.GREY, SquareColor.WHITE, SquareColor.PINK, SquareColor.ORANGE, SquareColor.GREY, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.PINK, SquareColor.BLUE, SquareColor.GREEN, SquareColor.WHITE, SquareColor.PURPLE},
            {SquareColor.PINK, SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.WHITE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.GREY, SquareColor.PURPLE, SquareColor.WHITE, SquareColor.ORANGE, SquareColor.PINK, SquareColor.YELLOW},
            {SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.PINK, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.GREY, SquareColor.YELLOW, SquareColor.PURPLE, SquareColor.ORANGE},
            {SquareColor.PURPLE, SquareColor.GREY, SquareColor.WHITE, SquareColor.ORANGE, SquareColor.GREY, SquareColor.GREEN, SquareColor.PURPLE, SquareColor.WHITE, SquareColor.PINK, SquareColor.GREEN, SquareColor.WHITE, SquareColor.BLUE},
            {SquareColor.GREEN, SquareColor.PINK, SquareColor.PURPLE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.WHITE, SquareColor.PINK, SquareColor.GREY, SquareColor.BLUE, SquareColor.ORANGE, SquareColor.GREY, SquareColor.PINK},
            {SquareColor.WHITE, SquareColor.BLUE, SquareColor.GREY, SquareColor.PINK, SquareColor.ORANGE, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.GREEN, SquareColor.PURPLE, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.GREEN},
            {SquareColor.PINK, SquareColor.PURPLE, SquareColor.YELLOW, SquareColor.WHITE, SquareColor.GREEN, SquareColor.GREY, SquareColor.PURPLE, SquareColor.ORANGE, SquareColor.YELLOW, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.WHITE},
            {SquareColor.GREY, SquareColor.ORANGE, SquareColor.PINK, SquareColor.PURPLE, SquareColor.BLUE, SquareColor.WHITE, SquareColor.PINK, SquareColor.GREY, SquareColor.WHITE, SquareColor.GREEN, SquareColor.ORANGE, SquareColor.YELLOW},
            {SquareColor.BLUE, SquareColor.WHITE, SquareColor.GREEN, SquareColor.GREY, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.GREY, SquareColor.PINK, SquareColor.BLUE},
            {SquareColor.GREEN, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.PINK, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.PINK, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.GREY      },
    };


    private int width, height;

    public QuandaryBoard(Point pos, int width, int height) {
        super(pos, new HashMap<QuandaryBoard.ChildType, Set<Sprite>>());
        this.width = width;
        this.height = height;
    }

    public void initSquares() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Square s = new Square(getSquarePosition(i, j), getSquareSize(), getSquareColor(i, j));
                addChild(ChildType.SQUARE, s);
            }
        }
    }

    public int getSquareSize() {
        return width / SIZE;
    }

    private Point getSquarePosition(int i, int j) {
        int x = width / SIZE * i;
        int y = height / SIZE * j;

        return new Point(x, y);
    }

    private SquareColor getSquareColor(int i, int j) {
        return QuandaryBoard.colors[i][j];
    }

    public void initHighlights() {
        addChild(ChildType.HIGHLIGHT, new Highlight(getSquarePosition(2, 3), getSquareSize(), Color.RED));
        addChild(ChildType.HIGHLIGHT, new Highlight(getSquarePosition(3, 3), getSquareSize(), Color.BLUE));
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

    @Override
    public void draw(Graphics2D g2d) {
        // Draw self.
        drawSelf(g2d);

        // Draw Squares.
        for (Sprite child : getChildren().get(ChildType.SQUARE)) {
            child.draw(g2d);
        }

        // Draw Highlights.
        for (Sprite child : getChildren().get(ChildType.HIGHLIGHT)) {
            child.draw(g2d);
        }
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(getPos().x, getPos().y, getWidth(), getHeight());
    }
}

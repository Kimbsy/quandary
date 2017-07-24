package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.sim.sprite.CompoundSprite;
import com.kimbsy.sim.sprite.Sprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.kimbsy.quandary.domain.ChildSpriteType.HIGHLIGHT;
import static com.kimbsy.quandary.domain.ChildSpriteType.PAWN;
import static com.kimbsy.quandary.domain.ChildSpriteType.SQUARE;

/**
 * @author kimbsy
 */
public class QuandaryBoard extends CompoundSprite<ChildSpriteType> {

    private int size, width, height;

    public QuandaryBoard(int size, int width, int height) {
        super(new Point(0, 0), new HashMap<ChildSpriteType, Set<Sprite>>());
        this.size = size;
        this.width = width;
        this.height = height;

        initSquares();
        initHighlights();
        initPawns();
    }

    private void initSquares() {
        SquareColor[][] colors = new SquareColor[][]{
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
                {SquareColor.GREEN, SquareColor.YELLOW, SquareColor.ORANGE, SquareColor.BLUE, SquareColor.PURPLE, SquareColor.PINK, SquareColor.WHITE, SquareColor.YELLOW, SquareColor.PINK, SquareColor.ORANGE, SquareColor.GREEN, SquareColor.GREY}
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Point coords = new Point(i, j);
                Square s = new Square(getPosFromCoords(coords), coords, width / size, colors[i][j]);
                addChild(ChildSpriteType.SQUARE, s);
            }
        }
    }

    private void initHighlights() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Point coords = new Point(i, j);
                Highlight h = new Highlight(getPosFromCoords(coords), coords, width / size, HighlightColor.LIGHT);
                h.setVisible(false);
                addChild(ChildSpriteType.HIGHLIGHT, h);
            }
        }
    }

    private void initPawns() {
        List<Integer> positions = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            positions.add(i);
        }

        Collections.shuffle(positions);
        for (int i = 0; i < 4; i++) {
            Point coords1 = new Point(positions.get(i), 0);
            Pawn p1 = new Pawn(getPosFromCoords(coords1), coords1, Player.ONE);
            addChild(ChildSpriteType.PAWN, p1);
        }

        Collections.shuffle(positions);
        for (int i = 0; i < 4; i++) {
            Point coords2 = new Point(positions.get(i), size - 1);
            Pawn p2 = new Pawn(getPosFromCoords(coords2), coords2, Player.TWO);
            addChild(ChildSpriteType.PAWN, p2);
        }
    }

    public int getSize() {
        return size;
    }

    public Point getCoordsFromPos(Point pos) {
        return new Point((pos.x * size) / width, (pos.y * size) / height);
    }

    public Point getPosFromCoords(Point coords) {
        return new Point((coords.x * width) / size, (coords.y * width) / size);
    }

    @Override
    public LinkedHashSet<ChildSpriteType> getKeyOrder() {
        LinkedHashSet<ChildSpriteType> order = new LinkedHashSet<ChildSpriteType>();
        order.add(SQUARE);
        order.add(HIGHLIGHT);
        order.add(PAWN);
        return order;
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuandaryBoard that = (QuandaryBoard) o;

        if (size != that.size) return false;
        if (width != that.width) return false;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("size", size)
                .append("width", width)
                .append("height", height)
                .toString();
    }
}

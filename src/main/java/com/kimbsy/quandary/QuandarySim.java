package com.kimbsy.quandary;

import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.quandary.sprite.Highlight;
import com.kimbsy.quandary.sprite.Pawn;
import com.kimbsy.quandary.sprite.QuandaryBoard;
import com.kimbsy.quandary.sprite.Square;
import com.kimbsy.sim.BaseSim;
import com.kimbsy.sim.sprite.Sprite;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kimbsy
 */
public class QuandarySim extends BaseSim {

    public static final int SIZE = 12;

    private QuandaryBoard quandaryBoard;

    @Override
    public void initSprites() {
        quandaryBoard = new QuandaryBoard(SIZE, getWidth(), getHeight());

        Set<Sprite> sprites = new HashSet<Sprite>();
        sprites.add(quandaryBoard);

        setSprites(sprites);
    }

    @Override
    public void onMouseClicked(MouseEvent mouseEvent) {
        Point coords = quandaryBoard.getCoordsFromPos(mouseEvent.getPoint());

        Highlight highlight = getHighlightAtCoords(coords);

        if (highlight != null) {
            highlight.setVisible(true);
        } else {
            Highlight newHightlight = new Highlight(quandaryBoard.getPosFromCoords(coords), coords, quandaryBoard.getHighLightSize(), HighlightColor.LIGHT);
            quandaryBoard.addChild(ChildSpriteType.HIGHLIGHT, newHightlight);
        }
    }

    public Square getSquareAtCoords(Point targetCoords) {
        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.SQUARE);

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Square square = (Square) s;
                if (square.getCoords().equals(targetCoords)) {
                    return square;
                }
            }
        }

        return null;
    }

    public Highlight getHighlightAtCoords(Point targetCoords) {
        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.HIGHLIGHT);

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Highlight highlight = (Highlight) s;
                if (highlight.getCoords().equals(targetCoords)) {
                    return highlight;
                }
            }
        }

        return null;
    }

    public Pawn getPawnAtCoords(Point targetCoords) {
        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (pawn.getCoords().equals(targetCoords)) {
                    return pawn;
                }
            }
        }

        return null;
    }
}

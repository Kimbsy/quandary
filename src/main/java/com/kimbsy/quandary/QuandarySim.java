package com.kimbsy.quandary;

import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.quandary.sprite.Highlight;
import com.kimbsy.quandary.sprite.Pawn;
import com.kimbsy.quandary.sprite.QuandaryBoard;
import com.kimbsy.quandary.sprite.Square;
import com.kimbsy.sim.BaseSim;
import com.kimbsy.sim.sprite.Sprite;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kimbsy
 */
public class QuandarySim extends BaseSim {

    enum State {
        SELECTING_PAWN,
        SELECTING_MOVE,
        WINNER_DETERMINED,
    }

    public static final int SIZE = 12;

    private QuandaryBoard quandaryBoard;
    private Player currentTurn;
    private Player winner;
    private Pawn selectedPawn;
    private ArrayList<Highlight> visibleHighlights;
    private ArrayList<Point> availableMoves;
    private State state;

    @Override
    public void initSprites() {
        quandaryBoard = new QuandaryBoard(SIZE, getWidth(), getHeight());
        currentTurn = Player.ONE;
        visibleHighlights = new ArrayList<Highlight>();
        availableMoves = new ArrayList<Point>();
        state = State.SELECTING_PAWN;

        Set<Sprite> sprites = new HashSet<Sprite>();
        sprites.add(quandaryBoard);
        highlightAvailablePawns();

        setSprites(sprites);
    }

    @Override
    public void onMouseClicked(MouseEvent mouseEvent) {
        Point coords = quandaryBoard.getCoordsFromPos(mouseEvent.getPoint());

        switch (state) {
            case SELECTING_PAWN:
                selectPawn(coords);
                break;
            case SELECTING_MOVE:
                movePawn(coords);
                break;
            case WINNER_DETERMINED:
        }
    }

    private void movePawn(Point coords) {
        if (availableMoves.contains(coords)) {
            selectedPawn.setCoords(coords);
            selectedPawn.setPos(quandaryBoard.getPosFromCoords(coords));
            checkVictory(coords);
//            toggleTurn();
        }
        if (!state.equals(State.WINNER_DETERMINED)) {
            state = State.SELECTING_PAWN;
        }
        deselect();
    }

    private void checkVictory(Point coords) {
        if (currentTurn.equals(Player.ONE) && coords.y == quandaryBoard.getSize() - 1) {
            System.out.println("winner 1");
            winner = Player.ONE;
            state = State.WINNER_DETERMINED;
        }
        if (currentTurn.equals(Player.TWO) && coords.y == 0) {
            System.out.println("winner 2");
            winner = Player.TWO;
            state = State.WINNER_DETERMINED;
        }
    }

    private void selectPawn(Point coords) {
        Pawn pawn = getPawnAtCoords(coords);

        // Highlight moves or deselect.
        if (pawn != null && pawn.getPlayer().equals(currentTurn)) {
            selectedPawn = pawn;
            resetHighlights();
            highlightSelectedPawn();
            highlightMoves();
            state = State.SELECTING_MOVE;
        } else {
            deselect();
        }
    }

    private void highlightAvailablePawns() {
        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (pawn.getPlayer().equals(currentTurn)) {
                    Highlight highlight = getHighlightAtCoords(pawn.getCoords());
                    highlight.setVisible(true);
                    highlight.setHighlightColor(HighlightColor.LIGHT);
                    visibleHighlights.add(highlight);
                }
            }
        }
    }

    private void highlightSelectedPawn() {
        Highlight highlight = getHighlightAtCoords(selectedPawn.getCoords());
        highlight.setVisible(true);
        highlight.setHighlightColor(HighlightColor.LIGHT);
        visibleHighlights.add(highlight);
    }

    private void highlightMoves() {
        int direction = getDirection(currentTurn);
        Point coords = selectedPawn.getCoords();

        for (int i = -1; i < 2; i++) {
            Point moveCoords = new Point(coords.x + i, coords.y + direction);

            Square square = getSquareAtCoords(moveCoords);

            if (square != null) {
                Highlight highlight = getHighlightAtCoords(moveCoords);
                highlight.setVisible(true);
                visibleHighlights.add(highlight);

                if (isLegal(square)) {
                    highlight.setHighlightColor(HighlightColor.LIGHT);
                    availableMoves.add(highlight.getCoords());
                } else {
                    highlight.setHighlightColor(HighlightColor.DARK);
                }
            }
        }
    }

    private void deselect() {
        selectedPawn = null;
        resetHighlights();
        availableMoves = new ArrayList<Point>();
        highlightAvailablePawns();
    }

    private int getDirection(Player player) {
        return player.equals(Player.ONE) ? 1 : -1;
    }

    private void resetHighlights() {
        for (Highlight highlight : visibleHighlights) {
            highlight.setVisible(false);
        }
        visibleHighlights = new ArrayList<Highlight>();
    }

    private boolean isLegal(Square square) {
        if (getPawnAtCoords(square.getCoords()) != null) {
            return false;
        }

        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);
        Set<SquareColor> blockedColors = new HashSet<SquareColor>();

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (!pawn.getPlayer().equals(currentTurn)) {
                    int direction = -1 * getDirection(currentTurn);
                    Point inFront = new Point(pawn.getCoords().x, pawn.getCoords().y + direction);
                    blockedColors.add(getSquareAtCoords(inFront).getSquareColor());
                }
            }
        }

        return !blockedColors.contains(square.getSquareColor());
    }

    public void toggleTurn() {
        if (currentTurn.equals(Player.ONE)) {
            currentTurn = Player.TWO;
        } else {
            currentTurn = Player.ONE;
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

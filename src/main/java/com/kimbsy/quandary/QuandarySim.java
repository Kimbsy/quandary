package com.kimbsy.quandary;

import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.quandary.sprite.Modal;
import com.kimbsy.quandary.sprite.Highlight;
import com.kimbsy.quandary.sprite.Pawn;
import com.kimbsy.quandary.sprite.QuandaryBoard;
import com.kimbsy.quandary.sprite.Square;
import com.kimbsy.sim.BaseSim;
import com.kimbsy.sim.sprite.Sprite;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author kimbsy
 */
public class QuandarySim extends BaseSim {

    enum State {
        SELECTING_PAWN,
        SELECTING_MOVE,
        MODAL,
        WINNER_DETERMINED,
    }

    private static final int SIZE = 12;

    private QuandaryBoard quandaryBoard;
    private Player currentTurn;
    private Player winner;
    private Modal activeModal;
    private Modal skipTurn;
    private Modal displayWinner;
    private Pawn selectedPawn;
    private ArrayList<Highlight> visibleHighlights;
    private ArrayList<Point> availableMoves;
    private State state;

    @Override
    public void initSim() {
        // Set sprites.
        quandaryBoard = new QuandaryBoard(SIZE, getWidth(), getHeight());
        skipTurn = new Modal(
                Modal.ModalType.SKIP_TURN,
                new Point(getWidth() / 8, 100),
                getWidth() * 3 / 4,
                150,
                "You have no available moves",
                new ArrayList<String>(Arrays.asList("skip turn"))
        );
        skipTurn.setVisible(false);
        displayWinner = new Modal(
                Modal.ModalType.DISPLAY_WINNER,
                new Point(getWidth() / 8, 100),
                getWidth() * 3 / 4,
                150,
                "A winner is you!",
                new ArrayList<String>(Arrays.asList("new game", "quit"))
        );
        displayWinner.setVisible(false);

        Set<Sprite> sprites = new LinkedHashSet<Sprite>();
        sprites.add(quandaryBoard);
        sprites.add(skipTurn);
        sprites.add(displayWinner);

        // Set state.
        currentTurn = Player.ONE;
        visibleHighlights = new ArrayList<Highlight>();
        availableMoves = new ArrayList<Point>();
        state = State.SELECTING_PAWN;
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
            case MODAL:
                chooseOption(mouseEvent.getPoint());
            case WINNER_DETERMINED:
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

    private void movePawn(Point coords) {
        if (availableMoves.contains(coords)) {
            selectedPawn.setCoords(coords);
            selectedPawn.setPos(quandaryBoard.getPosFromCoords(coords));
            checkVictory(coords);
            toggleTurn();
            checkMoveLock();
        }
        if (!state.equals(State.MODAL)) {
            state = State.SELECTING_PAWN;
        }
        deselect();
    }

    private void checkVictory(Point coords) {
        if (currentTurn.equals(Player.ONE) && coords.y == quandaryBoard.getSize() - 1) {
            winner = Player.ONE;
            displayWinner.setBodyText(Player.ONE.getName() + " Wins!");
            displayWinner.setVisible(true);
            activeModal = displayWinner;
            state = State.MODAL;
        }
        if (currentTurn.equals(Player.TWO) && coords.y == 0) {
            winner = Player.TWO;
            displayWinner.setBodyText(Player.TWO.getName() + " Wins!");
            displayWinner.setVisible(true);
            activeModal = displayWinner;
            state = State.MODAL;
        }
    }

    private void checkMoveLock() {

        int direction = getDirection(currentTurn);

        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);

        boolean noMoves = true;

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (pawn.getPlayer().equals(currentTurn)) {

                    Point coords = pawn.getCoords();

                    for (int i = -1; i < 2; i++) {
                        Point moveCoords = new Point(coords.x + i, coords.y + direction);

                        Square square = getSquareAtCoords(moveCoords);

                        if (square != null) {
                            if (isLegal(square)) {
                                noMoves = false;
                            }
                        }
                    }
                }
            }
        }

        if (noMoves) {
            skipTurn.setVisible(true);
            activeModal = skipTurn;
            state = State.MODAL;
        }
    }

    private void chooseOption(Point coords) {
        switch (activeModal.getModalType()) {
            case SKIP_TURN:
                handleSkipTurnModal(coords);
                break;
            case DISPLAY_WINNER:
                handleDisplayWinnerModal(coords);
                break;
        }
    }

    private void handleSkipTurnModal(Point coords) {
        switch (activeModal.getChoice(coords)) {
            case 0:
                skipTurn();
                break;
            case -1:
                // no-op.
                break;
        }
    }

    private void skipTurn() {
        activeModal = null;
        skipTurn.setVisible(false);
        state = State.SELECTING_PAWN;
        toggleTurn();
        checkMoveLock();
        deselect();
    }

    private void handleDisplayWinnerModal(Point coords) {
        switch (activeModal.getChoice(coords)) {
            case 0:
                newGame();
                break;
            case 1:
                quit();
                break;
            case -1:
                // no-op.
                break;
        }
    }

    private void newGame() {
        activeModal = null;
        displayWinner.setVisible(false);
        state = State.SELECTING_PAWN;
        initSim();
    }

    private void quit() {
        System.exit(0);
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

    private void toggleTurn() {
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

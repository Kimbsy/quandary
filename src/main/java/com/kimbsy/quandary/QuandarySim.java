package com.kimbsy.quandary;

import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.HighlightColor;
import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.domain.SquareColor;
import com.kimbsy.quandary.recording.GameRecorder;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class facilitates the simulation of the board game Quandary.
 *
 * @author kimbsy
 */
public class QuandarySim extends BaseSim {

    enum State {
        SELECTING_PAWN,
        SELECTING_MOVE,
        MODAL,
    }

    private static final int SIZE = 12;

    private QuandaryBoard quandaryBoard;
    private Player currentPlayer;
    private Modal activeModal;
    private Modal skipTurn;
    private Modal endGame;
    private Pawn selectedPawn;
    private ArrayList<Highlight> visibleHighlights;
    private ArrayList<Point> availableMoves;
    private State state;
    private boolean possibleDraw;
    private GameRecorder gameRecorder;

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
                new ArrayList<>(Collections.singletonList("skip turn"))
        );
        skipTurn.setVisible(false);
        endGame = new Modal(
                Modal.ModalType.END_GAME,
                new Point(getWidth() / 8, 100),
                getWidth() * 3 / 4,
                150,
                "A winner is you!",
                new ArrayList<>(Arrays.asList("new game", "quit"))
        );
        endGame.setVisible(false);

        Set<Sprite> sprites = new LinkedHashSet<>();
        sprites.add(quandaryBoard);
        sprites.add(skipTurn);
        sprites.add(endGame);

        // Set state.
        currentPlayer = Player.ONE;
        visibleHighlights = new ArrayList<>();
        availableMoves = new ArrayList<>();
        state = State.SELECTING_PAWN;
        possibleDraw = false;
        gameRecorder = new GameRecorder(this);
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
                // If move failed, check if re-selecting,
                if (!movePawn(coords)) {
                    selectPawn(coords);
                }
                break;
            case MODAL:
                handleModal(mouseEvent.getPoint());
        }
    }

    /**
     * Get the {@link QuandaryBoard}.
     *
     * @return The {@link QuandaryBoard} object.
     */
    public QuandaryBoard getQuandaryBoard() {
        return quandaryBoard;
    }

    /**
     * Get the current {@link Player}.
     *
     * @return The current {@link Player}.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the currently selected {@link Pawn}.
     *
     * @return The selected {@link Pawn}.
     */
    public Pawn getSelectedPawn() {
        return selectedPawn;
    }

    /**
     * Attempt to select a {@link Pawn} on the board at a set of board coordinates.
     *
     * @param coords The board coordinates of the potential {@link Pawn}.
     */
    private void selectPawn(Point coords) {
        Pawn pawn = getPawnAtCoords(coords);

        // Highlight moves or deselect.
        if (pawn != null && pawn.getPlayer().equals(currentPlayer)) {
            selectedPawn = pawn;
            resetHighlights();
            highlightSelectedPawn();
            highlightMoves();
            state = State.SELECTING_MOVE;
        } else {
            deselect();
        }
    }

    /**
     * Attempt to move the currently selected {@link Pawn} to the specified set of board coordinates.
     *
     * @param coords The board coordinates to move to.
     * @return True if the {@link Pawn} was successfully moved.
     */
    private boolean movePawn(Point coords) {
        boolean moved = false;

        if (availableMoves.contains(coords)) {
            gameRecorder.saveMove(coords);
            selectedPawn.setCoords(coords);
            selectedPawn.setPos(quandaryBoard.getPosFromCoords(coords));
            checkVictory(coords);
            toggleTurn();
            checkMoveLock();
            deselect();
            possibleDraw = false;
            moved = true;
        }

        if (!state.equals(State.MODAL)) {
            state = State.SELECTING_PAWN;
        }

        deselect();

        return moved;
    }

    /**
     * Check if either {@link Player} has managed to get a {@link Pawn} to the opposite side of the board.
     * <p>
     * If so, activate the DIPLAY_WINNER {@link Modal}, export the recorded game to file and get ready to record a new
     * game.
     *
     * @param coords The board coordinates of the most recently moved {@link Pawn}.
     */
    private void checkVictory(Point coords) {
        Player winner = null;

        if (currentPlayer.equals(Player.ONE) && coords.y == quandaryBoard.getSize() - 1) {
            winner = Player.ONE;
        }
        if (currentPlayer.equals(Player.TWO) && coords.y == 0) {
            winner = Player.TWO;
        }

        if (winner != null) {
            endGame.setBodyText(winner.getName() + " Wins!");
            endGame.setVisible(true);
            activeModal = endGame;
            state = State.MODAL;
            gameRecorder.setWinner(winner);
            gameRecorder.export();
            gameRecorder.reset();
        }
    }

    /**
     * Determine if the current {@link Player} has no legal moves.
     * <p>
     * If so, display the SKIP_TURN {@link Modal}.
     */
    private void checkMoveLock() {

        int direction = getDirection(currentPlayer);

        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);

        boolean noMoves = true;

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (pawn.getPlayer().equals(currentPlayer)) {

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
            if (possibleDraw) {
                gameRecorder.reset();
                endGame.setVisible(true);
                endGame.setBodyText("No moves available, the game is a draw.");
                activeModal = endGame;
                state = State.MODAL;
            } else {
                skipTurn.setVisible(true);
                activeModal = skipTurn;
                state = State.MODAL;
                possibleDraw = true;
            }
        }
    }

    /**
     * Determine which {@link Modal} handler method to use.
     *
     * @param pos The position of the mouse click event.
     */
    private void handleModal(Point pos) {
        switch (activeModal.getModalType()) {
            case SKIP_TURN:
                handleSkipTurnModal(pos);
                break;
            case END_GAME:
                handleDisplayWinnerModal(pos);
                break;
        }
    }

    /**
     * Handle a click on the SKIP_TURN {@link Modal}.
     *
     * @param pos The position of the mouse click event.
     */
    private void handleSkipTurnModal(Point pos) {
        switch (activeModal.getChoice(pos)) {
            case 0:
                skipTurn();
                break;
            case -1:
                // no-op.
                break;
        }
    }

    /**
     * Skip the current {@link Player}s turn and deactivate the SKIP_TURN {@link Modal}.
     */
    private void skipTurn() {
        activeModal = null;
        skipTurn.setVisible(false);
        state = State.SELECTING_PAWN;
        toggleTurn();
        checkMoveLock();
        deselect();
    }

    /**
     * Handle a click on the END_GAME {@link Modal}.
     *
     * @param pos The position of the mouse click event.
     */
    private void handleDisplayWinnerModal(Point pos) {
        switch (activeModal.getChoice(pos)) {
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

    /**
     * Start a new game and deactivate the END_GAME {@link Modal}.
     */
    private void newGame() {
        activeModal = null;
        endGame.setVisible(false);
        state = State.SELECTING_PAWN;
        initSim();
    }

    /**
     * Quit the simulation.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Show the {@link Highlight} {@link Sprite}s at board coordinates corresponding to the current {@link Player}s
     * {@link Pawn}s.
     */
    private void highlightAvailablePawns() {
        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (pawn.getPlayer().equals(currentPlayer)) {
                    Highlight highlight = getHighlightAtCoords(pawn.getCoords());
                    if (highlight != null) {
                        highlight.setVisible(true);
                        highlight.setHighlightColor(HighlightColor.LIGHT);
                        visibleHighlights.add(highlight);
                    }
                }
            }
        }
    }

    /**
     * Show the {@link Highlight} {@link Sprite}s at board coordinates corresponding to the currently selected {@link
     * Pawn}.
     */
    private void highlightSelectedPawn() {
        Highlight highlight = getHighlightAtCoords(selectedPawn.getCoords());
        if (highlight != null) {
            highlight.setVisible(true);
            highlight.setHighlightColor(HighlightColor.LIGHT);
            visibleHighlights.add(highlight);
        }
    }

    /**
     * Show the appropriately colored {@link Highlight} {@link Sprite}s corresponding the coordinates of the moves
     * available to the currently selected {@link Pawn}.
     */
    private void highlightMoves() {
        int direction = getDirection(currentPlayer);
        Point coords = selectedPawn.getCoords();

        for (int i = -1; i < 2; i++) {
            Point moveCoords = new Point(coords.x + i, coords.y + direction);

            Square square = getSquareAtCoords(moveCoords);

            if (square != null) {
                Highlight highlight = getHighlightAtCoords(moveCoords);
                if (highlight != null) {
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
    }

    /**
     * Deselect the currently selected {@link Pawn}.
     */
    private void deselect() {
        selectedPawn = null;
        resetHighlights();
        availableMoves = new ArrayList<>();
        highlightAvailablePawns();
    }

    /**
     * Determine the appropriate integer to add to a y coordinate to indicate the row "in front" of another row from the
     * specified {@link Player}s perspective.
     *
     * @param player The {@link Player}.
     * @return The required integer.
     */
    private int getDirection(Player player) {
        return player.equals(Player.ONE) ? 1 : -1;
    }

    /**
     * Stop showing all visible {@link Highlight} {@link Sprite}s.
     */
    private void resetHighlights() {
        for (Highlight highlight : visibleHighlights) {
            highlight.setVisible(false);
        }
        visibleHighlights = new ArrayList<>();
    }

    /**
     * Determine if a {@link Square} is a legal move for the current {@link Player} based on the colors being allowed by
     * the non active {@link Player}'s {@link Pawn}s.
     *
     * @param square The square to check.
     * @return True if the move is allowed.
     */
    private boolean isLegal(Square square) {
        if (getPawnAtCoords(square.getCoords()) != null) {
            return false;
        }

        Set<Sprite> sprites = quandaryBoard.getChildren().get(ChildSpriteType.PAWN);
        Set<SquareColor> allowedColors = new HashSet<>();

        if (sprites != null && !sprites.isEmpty()) {
            for (Sprite s : sprites) {
                Pawn pawn = (Pawn) s;
                if (!pawn.getPlayer().equals(currentPlayer)) {
                    int direction = -1 * getDirection(currentPlayer);
                    Point inFront = new Point(pawn.getCoords().x, pawn.getCoords().y + direction % (quandaryBoard.getSize() - 1));
                    Square inFrontSquare = getSquareAtCoords(inFront);
                    if (inFrontSquare != null) {
                        allowedColors.add(inFrontSquare.getSquareColor());
                    }
                }
            }
        }

        return allowedColors.contains(square.getSquareColor());
    }

    /**
     * Switch from one {@link Player}'s turn to the next.
     */
    private void toggleTurn() {
        if (currentPlayer.equals(Player.ONE)) {
            currentPlayer = Player.TWO;
        } else {
            currentPlayer = Player.ONE;
        }
    }

    /**
     * Get the {@link Square} {@link Sprite} at the corresponding board coordinates.
     *
     * @param targetCoords The board coordinates.
     * @return The {@link Square} object at those coordinates or null if none found.
     */
    private Square getSquareAtCoords(Point targetCoords) {
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

    /**
     * Get the {@link Highlight} {@link Sprite} at the corresponding board coordinates.
     *
     * @param targetCoords The board coordinates.
     * @return The {@link Highlight} object at those coordinates or null if none found.
     */
    private Highlight getHighlightAtCoords(Point targetCoords) {
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

    /**
     * Get the {@link Pawn} {@link Sprite} at the corresponding board coordinates.
     *
     * @param targetCoords The board coordinates.
     * @return The {@link Pawn} object at those coordinates or null if none found.
     */
    private Pawn getPawnAtCoords(Point targetCoords) {
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

package com.kimbsy.quandary.recording;

import com.kimbsy.quandary.QuandarySim;
import com.kimbsy.quandary.domain.ChildSpriteType;
import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.sprite.Pawn;
import com.kimbsy.sim.sprite.Sprite;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for recording all of the moves during the game and for writing them to a file when the game
 * is over.
 *
 * @author kimbsy
 */
public class GameRecorder {

    private QuandarySim quandarySim;
    private Set<GameState> gameStates;

    /**
     * Class constructor specifying the the {@link QuandarySim} to reference.
     *
     * @param quandarySim The running {@link QuandarySim} object.
     */
    public GameRecorder(QuandarySim quandarySim) {
        this.quandarySim = quandarySim;
        gameStates = new HashSet<>();
    }

    /**
     * Save the current move.
     * <p>
     * The {@link QuandarySim} '{@code selectedPawn}' is being moved to the board coordinates '{@code to}'.
     *
     * @param to The board coordinates the {@link Pawn} is moving to.
     */
    public void saveMove(Point to) {
        Point from = quandarySim.getSelectedPawn().getCoords();

        GameState gameState = new GameState();
        gameState.setCurrentPlayer(quandarySim.getCurrentPlayer());
        gameState.setFrom(from);
        gameState.setTo(to);

        Set<Sprite> sprites = quandarySim.getQuandaryBoard().getChildren().get(ChildSpriteType.PAWN);

        Set<Pawn> p1Pawns = new HashSet<>();
        Set<Pawn> p2Pawns = new HashSet<>();

        for (Sprite s : sprites) {
            Pawn pawn = (Pawn) s;
            if (pawn.getPlayer().equals(Player.ONE)) {
                p1Pawns.add(pawn);
            } else {
                p2Pawns.add(pawn);
            }
        }

        gameState.setP1Pawns(p1Pawns);
        gameState.setP2Pawns(p2Pawns);

        gameStates.add(gameState);
    }

    /**
     * Set the {@link Player} who ended up winning the game.
     *
     * @param winner The winning {@link Player}.
     */
    public void setWinner(Player winner) {
        for (GameState gameState : gameStates) {
            gameState.setWinner(winner);
        }
    }

    /**
     * Export the list of moves to file.
     */
    public void export() {
        Path path = Paths.get("/tmp/quandarydata/quandarygame_" + System.currentTimeMillis() + ".csv");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (GameState gameState : gameStates) {
                writer.write(gameState.getCsvOutput());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset the list of moves ready to record a new game.
     */
    public void reset() {
        this.gameStates = new HashSet<>();
    }
}

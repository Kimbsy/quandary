package com.kimbsy.quandary.recording;

import com.kimbsy.quandary.domain.Player;
import com.kimbsy.quandary.sprite.Pawn;

import java.awt.*;
import java.util.Set;

/**
 * This class represents a single move of a {@link Pawn} from board coordinates '{@code from}' to '{@code to}'.
 * <p>
 * The position of all {@link Pawn}s is also recorded along with who the current {@link Player} is and which {@link
 * Player} ended up winning the game.
 *
 * @author kimbsy
 */
class GameState {

    private Player currentPlayer;
    private Point from;
    private Point to;
    private Set<Pawn> p1Pawns;
    private Set<Pawn> p2Pawns;
    private Player winner;

    /**
     * Set the current {@link Player}.
     *
     * @param currentPlayer The current {@link Player}.
     */
    void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Set the board coordinates of the {@link Pawn} before being moved.
     *
     * @param from The board coordinates.
     */
    void setFrom(Point from) {
        this.from = from;
    }

    /**
     * Set the board coordinates of the {@link Pawn} after being moved.
     *
     * @param to The board coordinates.
     */
    void setTo(Point to) {
        this.to = to;
    }

    /**
     * Set the {@link Pawn}s for {@link Player#ONE}.
     *
     * @param p1Pawns The set of {@link Pawn}s belonging to {@link Player#ONE}.
     */
    void setP1Pawns(Set<Pawn> p1Pawns) {
        this.p1Pawns = p1Pawns;
    }

    /**
     * Set the {@link Pawn}s for {@link Player#TWO}.
     *
     * @param p2Pawns The set of {@link Pawn}s belonging to {@link Player#TWO}.
     */
    void setP2Pawns(Set<Pawn> p2Pawns) {
        this.p2Pawns = p2Pawns;
    }

    /**
     * Set the {@link Player} woh ended up winning the game.
     *
     * @param winner The winning {@link Player}.
     */
    void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Get this object as a comma separated string of values.
     *
     * @return The csv string.
     */
    String getCsvOutput() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(currentPlayer.getId())
                .append(",")
                .append(from.x)
                .append(",")
                .append(from.y)
                .append(",")
                .append(to.x)
                .append(",")
                .append(to.y)
                .append(",");

        for (Pawn pawn : p1Pawns) {
            stringBuilder.append(pawn.getCoords().x)
                    .append(",")
                    .append(pawn.getCoords().y)
                    .append(",");
        }
        for (Pawn pawn : p2Pawns) {
            stringBuilder.append(pawn.getCoords().x)
                    .append(",")
                    .append(pawn.getCoords().y)
                    .append(",");
        }

        stringBuilder.append(winner.getId())
                .append("\n");

        return stringBuilder.toString();
    }
}

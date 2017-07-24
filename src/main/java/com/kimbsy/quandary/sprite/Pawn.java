package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.Player;
import com.kimbsy.sim.sprite.BaseSprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;

/**
 * This class represents one of a {@link Player}s four pawns.
 *
 * @author kimbsy
 */
public class Pawn extends BaseSprite {

    private Point coords;
    private final Player player;
    private int width, height;
    private final int squareSize;

    /**
     * Class constructor specifying the position and board coordinates of the Pawn along with the {@link Player} it
     * belongs to and the size of the squares it will be drawn in.
     *
     * @param pos        The position of the Pawn.
     * @param coords     The board coordinates of the Pawn.
     * @param player     The owning {@link Player}.
     * @param squareSize The size of the squares the Pawn will be displayed in.
     */
    Pawn(Point pos, Point coords, Player player, int squareSize) {
        super(pos);
        this.player = player;
        this.coords = coords;
        this.width = squareSize / 3;
        this.height = squareSize / 3;
        this.squareSize = squareSize;
    }

    /**
     * Set the board coordinates of the Pawn.
     *
     * @param coords The board coordinates.
     */
    public void setCoords(Point coords) {
        this.coords = coords;
    }

    /**
     * Get the board coordinates of the Pawn.
     *
     * @return The board coordinates.
     */
    public Point getCoords() {
        return coords;
    }

    /**
     * Get the {@link Player} this Pawn belongs to.
     *
     * @return The owning {@link Player}.
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(player.getPrimaryColor());
        g2d.fillOval(getPos().x + squareSize / 3, getPos().y + squareSize / 3, width, height);

        g2d.setColor(player.getSecondaryColor());
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(getPos().x + squareSize / 3, getPos().y + squareSize / 3, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pawn pawn = (Pawn) o;

        if (squareSize != pawn.squareSize) return false;
        if (width != pawn.width) return false;
        if (height != pawn.height) return false;
        if (player != pawn.player) return false;
        return coords != null ? coords.equals(pawn.coords) : pawn.coords == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + squareSize;
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("player", player)
                .append("squareSize", squareSize)
                .append("width", width)
                .append("height", height)
                .append("coords", coords)
                .toString();
    }
}

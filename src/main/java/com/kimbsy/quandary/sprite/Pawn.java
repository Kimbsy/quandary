package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.Player;
import com.kimbsy.sim.sprite.BaseSprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;

/**
 * @author kimbsy
 */
public class Pawn extends BaseSprite {

    private final Player player;
    private int squareSize;
    private int width, height;
    private Point coords;

    public Pawn(Point pos, int squareSize, Point coords, Player player) {
        super(pos);
        this.squareSize = squareSize;
        this.width = squareSize / 3;
        this.height = squareSize / 3;
        this.coords = coords;
        this.player = player;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }

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

        if (player != pawn.player) return false;
        return coords != null ? coords.equals(pawn.coords) : pawn.coords == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("player", player)
                .append("coords", coords)
                .toString();
    }
}

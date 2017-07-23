package com.kimbsy.quandary.sprite;

import com.kimbsy.quandary.domain.PlayerColor;
import com.kimbsy.sim.sprite.BaseSprite;

import java.awt.*;

/**
 * @author kimbsy
 */
public class Pawn extends BaseSprite {

    private final Point coords;
    private final PlayerColor playerColor;

    public Pawn(Point pos, Point coords, PlayerColor playerColor) {
        super(pos);
        this.coords = coords;
        this.playerColor = playerColor;
    }

    public Point getCoords() {
        return coords;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(playerColor.getPrimaryColor());
        g2d.fillRect(getPos().x + 10, getPos().y + 10, 10, 10);

        g2d.setColor(playerColor.getSecondaryColor());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(getPos().x + 10, getPos().y + 10, 10, 10);
    }
}

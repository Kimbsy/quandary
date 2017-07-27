package com.kimbsy.quandary.domain;

import java.awt.*;

/**
 * This enum lists the players in the game along with their display name and pawn color choices.
 *
 * @author kimbsy
 */
public enum Player {

    ONE(1, "Player 1", new Color(226, 169, 255), new Color(102, 78, 120)),
    TWO(2, "Player 2", new Color(155, 255, 200), new Color(74, 120, 93));

    private int id;
    private String name;
    private Color primaryColor;
    private Color secondaryColor;

    Player(int id, String name, Color primaryColor, Color secondaryColor) {
        this.id = id;
        this.name = name;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }
}

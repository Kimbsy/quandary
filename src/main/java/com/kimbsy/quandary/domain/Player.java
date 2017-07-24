package com.kimbsy.quandary.domain;

import java.awt.*;

/**
 * @author kimbsy
 */
public enum Player {

    ONE(new Color(226, 169, 255), new Color(102, 78, 120)),
    TWO(new Color(155, 255, 200), new Color(74, 120, 93));

    private Color primaryColor;
    private Color secondaryColor;

    Player(Color primaryColor, Color secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    public Color getSecondaryColor() {
        return this.secondaryColor;
    }
}

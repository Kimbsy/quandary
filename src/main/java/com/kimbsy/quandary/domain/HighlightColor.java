package com.kimbsy.quandary.domain;

import com.kimbsy.quandary.sprite.Highlight;
import com.kimbsy.quandary.sprite.QuandaryBoard;

import java.awt.*;

/**
 * This enum lists the available colors of {@link Highlight}s on the {@link QuandaryBoard}.
 *
 * @author kimbsy
 */
public enum HighlightColor {

    LIGHT(new Color(77, 247, 255)),
    DARK(new Color(29, 3, 8));

    private Color color;

    HighlightColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}

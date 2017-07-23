package com.kimbsy.quandary.domain;

import com.kimbsy.quandary.sprite.QuandaryBoard;
import com.kimbsy.quandary.sprite.Square;

import java.awt.*;

/**
 * This enum lists the available colors of {@link Square}s on the {@link QuandaryBoard}.
 *
 * @author kimbsy
 */
public enum SquareColor {

    BLUE(new Color(20, 101, 154)),
    ORANGE(new Color(177, 62, 31)),
    GREY(new Color(117, 108, 103)),
    PINK(new Color(180, 28, 85)),
    YELLOW(new Color(204, 157, 17)),
    PURPLE(new Color(72, 34, 83)),
    GREEN(new Color(20, 104, 54)),
    WHITE(new Color(208, 191, 183));

    private Color color;

    public Color getColor() {
        return color;
    }

    SquareColor(Color color) {
        this.color = color;
    }
}

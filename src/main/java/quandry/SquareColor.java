package quandry;

import java.awt.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public enum SquareColor {

    BLUE(Color.BLUE),
    ORANGE(Color.ORANGE),
    GREY(Color.LIGHT_GRAY),
    PINK(Color.PINK),
    YELLOW(Color.YELLOW),
    PURPLE(Color.MAGENTA),
    GREEN(Color.GREEN),
    WHITE(Color.WHITE),

    ;

    private Color color;

    public Color getColor() {
        return color;
    }

    SquareColor(Color color) {
        this.color = color;
    }
}

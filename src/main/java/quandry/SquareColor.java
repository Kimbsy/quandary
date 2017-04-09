package quandry;

import java.awt.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public enum SquareColor {

    BLUE(new Color(20, 101, 154)),
    ORANGE(new Color(177, 62, 31)),
    GREY(new Color(117, 108, 103)),
    PINK(new Color(180, 28, 85)),
    YELLOW(new Color(204, 157 ,17)),
    PURPLE(new Color(72, 34, 83)),
    GREEN(new Color(20, 104, 54)),
    WHITE(new Color(208, 191, 183)),

    ;

    private Color color;

    public Color getColor() {
        return color;
    }

    SquareColor(Color color) {
        this.color = color;
    }
}

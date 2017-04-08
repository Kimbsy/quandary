package quandry;

import sim.sprite.CompoundSprite;
import sim.sprite.Sprite;

import java.awt.*;
import java.util.HashSet;

/**
 * Created by kimbsy on 08/04/17.
 */
public class QuandaryBoard extends CompoundSprite {

    private int width, height;

    public QuandaryBoard(Point pos, int width, int height) {
        super(pos, new HashSet<Sprite>());
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.BLACK);

        g.fillRect(getPos().x, getPos().y, getWidth(), getHeight());
        g.fillRect(20, 20, 50, 50);
    }
}

package sim.sprite;

import java.awt.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public abstract class BaseSprite implements Sprite {

    private Point pos;

    public BaseSprite() {
        this.pos = new Point(0, 0);
    }

    public BaseSprite(Point pos) {
        this.pos = pos;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public abstract void draw(Graphics2D g2d);
}

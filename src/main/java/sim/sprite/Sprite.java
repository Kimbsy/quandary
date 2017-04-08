package sim.sprite;

import java.awt.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public interface Sprite {

    /**
     * Get the position of the {@link Sprite}.
     * @return The coordinates of the sprite.
     */
    Point getPos();

    /**
     * Draw the sprite.
     * @param g2d The graphics object.
     */
    void draw(Graphics2D g2d);
}

package sim.sprite;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 08/04/17.
 */
public abstract class CompoundSprite extends BaseSprite {

    private Set<Sprite> children;

    public CompoundSprite() {
        super();
        children = new HashSet<Sprite>();
    }

    public CompoundSprite(Point pos, Set<Sprite> children) {
        super(pos);
        this.children = children;
    }

    public Set<Sprite> getChildren() {
        return children;
    }

    public void setChildren(Set<Sprite> children) {
        this.children = children;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Draw self.
        drawSelf(g2d);

        // Draw children.
        for (Sprite child : children) {
            child.draw(g2d);
        }
    }

    abstract public void drawSelf(Graphics2D g);
}

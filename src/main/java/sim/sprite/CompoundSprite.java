package sim.sprite;

import quandry.QuandaryBoard;

import java.awt.*;
import java.util.*;

/**
 * Created by kimbsy on 08/04/17.
 */
public abstract class CompoundSprite extends BaseSprite {

    private Map<QuandaryBoard.ChildType, Set<Sprite>> children;

    public CompoundSprite() {
        super();
        children = new HashMap<QuandaryBoard.ChildType, Set<Sprite>>();
    }

    public CompoundSprite(Point pos, Map<QuandaryBoard.ChildType, Set<Sprite>> children) {
        super(pos);
        this.children = children;
    }

    public Map<QuandaryBoard.ChildType, Set<Sprite>> getChildren() {
        return children;
    }

    public void setChildren(Map<QuandaryBoard.ChildType, Set<Sprite>> children) {
        this.children = children;
    }

    public void addChild(QuandaryBoard.ChildType type, Sprite child) {
        if (null == children.get(type)) {
            children.put(type, new HashSet<Sprite>());
        }
        children.get(type).add(child);
    }

    public void removeChild(QuandaryBoard.ChildType type, Sprite child) {
        if (null == children.get(type)) {
            children.put(type, new HashSet<Sprite>());
        }
        children.get(type).remove(child);
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Draw self.
        drawSelf(g2d);

        // Draw children.
        for (Set<Sprite> childType : children.values()) {
            for (Sprite child : childType) {
                child.draw(g2d);
            }
        }
    }

    abstract public void drawSelf(Graphics2D g);
}

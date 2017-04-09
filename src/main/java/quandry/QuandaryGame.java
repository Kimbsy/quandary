package quandry;

import sim.SimFrame;
import sim.Simulation;
import sim.sprite.Sprite;

import java.awt.*;

/**
 * Created by kimbsy on 09/04/17.
 */
public class QuandaryGame extends Simulation {

    QuandaryBoard board;

    public void initSim() {
        board = new QuandaryBoard(new Point(0, 0), SimFrame.WIDTH, SimFrame.HEIGHT);
        board.initSquares();
        board.initHighlights();
        addSprite(board);


    }

    public void draw(Graphics2D g2d) {
        // Draw the Sprites.
        for (Sprite sprite : getSprites()) {
            sprite.draw(g2d);
        }
    }
}

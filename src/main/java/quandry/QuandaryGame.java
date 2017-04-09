package quandry;

import sim.SimFrame;
import sim.Simulation;
import sim.sprite.Sprite;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 09/04/17.
 */
public class QuandaryGame extends Simulation {

    private QuandaryBoard board;

    private Set<Player> players;

    public void initSim() {
        board = new QuandaryBoard(new Point(0, 0), SimFrame.WIDTH, SimFrame.HEIGHT);
        board.initSquares();
        board.initHighlights();
        addSprite(board);

        players = new HashSet<Player>();
        players.add(new Player(board, Player.PlayerPosition.TOP));
        players.add(new Player(board, Player.PlayerPosition.BOTTOM));
    }

    public void draw(Graphics2D g2d) {
        // Draw the Sprites.
        for (Sprite sprite : getSprites()) {
            sprite.draw(g2d);
        }

        for (Player p : players) {
            p.drawSprites(g2d);
        }
    }
}

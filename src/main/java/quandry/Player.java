package quandry;

import sim.sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimbsy on 09/04/17.
 */
public class Player {

    public enum PlayerPosition {
        TOP,
        BOTTOM,
    }

    private Set<Sprite> pawns = new HashSet<Sprite>();
    private QuandaryBoard board;
    private PlayerPosition playerPosition;

    public Player(QuandaryBoard board, PlayerPosition playerPosition) {
        this.board = board;
        this.playerPosition = playerPosition;
        initPawns();
    }

    private void initPawns() {
        int row;
        Color color;
        switch (playerPosition) {
            case TOP:
                row = 0;
                color = Color.CYAN;
                break;
            case BOTTOM:
                row = 11;
                color = Color.DARK_GRAY;
                break;
            default:
                row = 0;
                color =  Color.BLACK;
        }

        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (int i = 0; i < 12; i++) {
            cols.add(i);
        }
        Collections.shuffle(cols);

        for (int i = 0; i < 4; i++) {
            int column = cols.get(i);

            Point position = board.getSquarePosition(column, row);
            pawns.add(new Pawn(position, board.getSquareSize(), color));
        }
    }

    public void drawSprites(Graphics2D g2d) {
        for (Sprite sprite : pawns) {
            sprite.draw(g2d);
        }
    }
}

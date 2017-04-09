package sim;

import quandry.QuandaryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimFrame extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    public SimFrame() {
        initUI();
    }

    public void initUI() {

        final Simulation quandaryGame = new QuandaryGame();
        add(quandaryGame);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = quandaryGame.getTimer();
                timer.stop();
            }
        });

        setTitle("Points");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                SimFrame example = new SimFrame();
                example.setVisible(true);
            }
        });
    }
}

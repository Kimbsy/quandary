package sim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Sim extends JFrame {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public Sim() {
        initUI();
    }

    public void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
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
                Sim example = new Sim();
                example.setVisible(true);
            }
        });
    }
}

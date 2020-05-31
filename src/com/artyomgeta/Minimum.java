package com.artyomgeta;

import javax.swing.*;
import java.awt.*;

public class Minimum extends JFrame implements Runnable {
    private JPanel panel1;
    public JButton stopButton;
    private JLabel label;
    private final Main parent;

    public Minimum(Main main) {
        parent = main;
    }

    @Override
    public void run() {
        setBounds(new Rectangle(0, 0, 100, 100));
        setUndecorated(true);
        setVisible(true);
        setContentPane(panel1);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    public void update(int now, int all) {
        label.setText(now + 1 + " / " + all);
        pack();
    }

}

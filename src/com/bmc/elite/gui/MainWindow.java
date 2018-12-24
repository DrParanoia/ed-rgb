package com.bmc.elite.gui;

import com.bmc.elite.EliteProcessWatcherRunnable;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JPanel contentPanel;

    public MainWindow() {
        super("Logitech RGB for Elite: Dangerous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        add(contentPanel);

        setVisible(true);
        new Thread(new EliteProcessWatcherRunnable()).start();
    }
}

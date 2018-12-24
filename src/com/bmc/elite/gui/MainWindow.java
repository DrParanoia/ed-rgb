package com.bmc.elite.gui;

import com.bmc.elite.EliteProcessWatcherRunnable;

import javax.swing.*;
import java.io.PrintStream;

public class MainWindow extends JFrame {
    private JPanel contentPanel;
    private JTextArea consoleOutputTextArea;
    private JScrollPane mainScrollPane;

    public MainWindow() {
        super("Logitech RGB for Elite: Dangerous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        add(contentPanel);

        PrintStream systemOutputStream = new PrintStream(new TextAreaOutputStream(consoleOutputTextArea));

        System.setOut(systemOutputStream);
        System.setErr(systemOutputStream);

        setVisible(true);
        new Thread(new EliteProcessWatcherRunnable()).start();
    }
}

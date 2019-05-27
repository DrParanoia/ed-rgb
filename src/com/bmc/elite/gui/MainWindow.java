package com.bmc.elite.gui;

import com.bmc.elite.EliteProcessWatcherRunnable;
import com.bmc.elite.config.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.PrintStream;

public class MainWindow extends JFrame {
	private JPanel contentPanel;
	private JTextArea consoleOutputTextArea;
	private JScrollPane mainScrollPane;

    public static boolean IN_FOCUS = false;

    public MainWindow() {
        super("Logitech RGB for Elite: Dangerous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        add(contentPanel);

        if (Application.USE_WINDOW)
        {
            PrintStream systemOutputStream = new PrintStream(new TextAreaOutputStream(consoleOutputTextArea));

			System.setOut(systemOutputStream);
			System.setErr(systemOutputStream);
		}

		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				IN_FOCUS = true;
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				IN_FOCUS = false;
			}
		});

		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/img/ed_logo_32.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}

        setVisible(Application.USE_WINDOW);
        new Thread(new EliteProcessWatcherRunnable()).start();
    }
}

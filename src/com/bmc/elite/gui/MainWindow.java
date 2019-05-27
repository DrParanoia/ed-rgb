package com.bmc.elite.gui;

import com.bmc.elite.EliteProcessWatcherRunnable;
import com.bmc.elite.config.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.PrintStream;

public class MainWindow extends JFrame {
	private JPanel contentPanel;
	private JTextArea consoleOutputTextArea;
	private JScrollPane mainScrollPane;

	public static boolean IN_FOCUS = false;
	public static boolean USE_WINDOW;

	public MainWindow(boolean useWindow) {
		super("Logitech RGB for Elite: Dangerous");
		USE_WINDOW = useWindow;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		add(contentPanel);

		if (USE_WINDOW) {
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

		setVisible(USE_WINDOW);
		new Thread(new EliteProcessWatcherRunnable()).start();
	}

	//this is for if the binds file cant be found. it will ask the user to manually select it.
	public static File MissingBinds() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Preset files", "binds");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Please select your preset file.");
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.CANCEL_OPTION || returnVal == JFileChooser.ERROR_OPTION) {
			System.exit(1);
		}
		return chooser.getSelectedFile();
	}
}
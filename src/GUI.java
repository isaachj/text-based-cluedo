import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public abstract class GUI {

	// Methods which must be implemented
	public abstract void resetGame();
	public abstract void redraw(Graphics g);
	//public abstract void onMove(Move m); todo: implement
	public abstract void onClick(MouseEvent e);
	protected abstract void startGame();

	public enum Move {
		NORTH, SOUTH, EAST, WEST
	}

	protected JFrame frame;

	private JPanel menu;
	private JComponent drawing;
	private JTextArea textOutputArea;

	private static final int DEFAULT_DRAWING_HEIGHT = 600; // todo: pick proper values. these are tests.
	private static final int DEFAULT_DRAWING_WIDTH = 600;
	private static final int TEXT_OUTPUT_ROWS = 5;

	//private JTextField textInput; For picking names?

	public GUI() { initialise(); }

	public void redraw() {
		frame.repaint();
	}

	public JTextArea getTextOutputArea() {
		return textOutputArea;
	}

	private void initialise() {

		// Do the menu
		JMenuBar menuBar = new JMenuBar();
		JMenu subMenu = new JMenu("Menu");
		menuBar.add(subMenu);

		// Start Button
		JMenuItem startButton = new JMenuItem("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startGame();
				redraw();
			}
		});
		subMenu.add(startButton);

		JMenuItem resetButton = new JMenuItem("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				resetGame();
				redraw();
			}
		});
		subMenu.add(resetButton);

		// Quit Button
		JMenuItem quitButton = new JMenuItem("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				/*System.exit(0); // cleanly end the program.*/

				// JDialog exit = new JDialog();
				int n = JOptionPane.showConfirmDialog(
						frame,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) { System.exit(0); }
			}
		});
		subMenu.add(quitButton);

		/*
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				/*System.exit(0); // cleanly end the program.

				// JDialog exit = new JDialog();
				int n = JOptionPane.showConfirmDialog(
						frame,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) { System.exit(0); }
			}
		});

		JButton restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				resetGame();
				redraw();
			}
		});

		JButton start = new JButton("Start Game");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startGame();
				redraw();
			}
		});*/


		menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.LINE_AXIS));

		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		menu.setBorder(edge);

		/*JPanel quitReset = new JPanel();
		quitReset.setLayout(new GridLayout(3, 2));
		quitReset.setMaximumSize(new Dimension(100, 100));
		quitReset.add(start);
		quitReset.add(restart);
		quitReset.add(quit);
		menu.add(quitReset);
		menu.add(quitReset);*/
		// rigid areas are invisible components that can be used to space
		// components out.
		menu.add(Box.createRigidArea(new Dimension(15, 0)));

		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH, DEFAULT_DRAWING_HEIGHT));
		drawing.setVisible(true);

		drawing.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClick(e);
				redraw();
			}
		});

		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true); // pretty line wrap.
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);

		DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings

		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(drawing);
		split.setBottomComponent(scroll);

		frame = new JFrame("Cluedo");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setJMenuBar(menuBar); // Add the menu bar
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(menu, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}

}
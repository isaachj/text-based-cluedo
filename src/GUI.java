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

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
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
		});


		/*      todo: Don't technically need this, might want it though.
		JButton west = new JButton("\u2190");
		west.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onMove(Move.WEST);
				redraw();
			}
		});

		JButton east = new JButton("\u2192");
		east.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onMove(Move.EAST);
				redraw();
			}
		});

		JButton north = new JButton("\u2191");
		north.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onMove(Move.NORTH);
				redraw();
			}
		});

		JButton south = new JButton("\u2193");
		south.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onMove(Move.SOUTH);
				redraw();
			}
		});
		*/

		/*
		// next, make the search box at the top-right. we manually fix
		// it's size, and add an action listener to call your code when
		// the user presses enter.
		search = new JTextField(SEARCH_COLS);
		search.setMaximumSize(new Dimension(0, 25));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSearch();
				redraw();
			}
		});*/

		/*if (UPDATE_ON_EVERY_CHARACTER) { todo: make key listener (this is just original code from comp
			// this forces an action event to fire on every key press, so the
			// user doesn't need to hit enter for results.
			search.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					// don't fire an event on backspace or delete
					if (e.getKeyCode() == 8 || e.getKeyCode() == 127)
						return;
					search.postActionEvent();
				}
			});
		}*/

		menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.LINE_AXIS));
		// make an empty border so the components aren't right up against the
		// frame edge.
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		menu.setBorder(edge);

		JPanel quitReset = new JPanel();
		quitReset.setLayout(new GridLayout(3, 2));
		// manually set a fixed size for the panel containing the load and quit
		// buttons (doesn't change with window resize).
		quitReset.setMaximumSize(new Dimension(100, 100));
		quitReset.add(start);
		quitReset.add(restart);
		quitReset.add(quit);
		menu.add(quitReset);
		// rigid areas are invisible components that can be used to space
		// components out.
		menu.add(Box.createRigidArea(new Dimension(15, 0)));

		/* todo: Maybe put these in?
		JPanel navigation = new JPanel();
		navigation.setMaximumSize(new Dimension(150, 60));
		navigation.setLayout(new GridLayout(2, 3)); todo: This grid layout won't work however.

		navigation.add(north);
		navigation.add(west);
		navigation.add(south);
		navigation.add(east);
		menu.add(navigation);
		menu.add(Box.createRigidArea(new Dimension(15, 0)));
		*/

		// glue is another invisible component that grows to take up all the
		// space it can on resize.

		/*
		 * then make the drawing canvas, which is really just a boring old
		 * JComponent with the paintComponent method overridden to paint
		 * whatever we like. this is the easiest way to do drawing.
		 */

		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH, DEFAULT_DRAWING_HEIGHT));
		// this prevents a bug where the component won't be
		// drawn until it is resized.
		drawing.setVisible(true);

		drawing.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClick(e);
				redraw();
			}
		});

		/*
		 * then make the JTextArea that goes down the bottom. we put this in a
		 * JScrollPane to get scroll bars when necessary.
		 */

		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true); // pretty line wrap.
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);
		// these two lines make the JScrollPane always scroll to the bottom when
		// text is appended to the JTextArea.
		DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/*
		 * finally, make the outer JFrame and put it all together. this is more
		 * complicated than it could be, as we put the drawing and text output
		 * components inside a JSplitPane so they can be resized by the user.
		 * the JScrollPane and the top bar are then added to the frame.
		 */

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings
		// JSplitPanes have a default border that makes an ugly row of pixels at
		// the top, remove it.
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(drawing);
		split.setBottomComponent(scroll);

		frame = new JFrame("Cluedo");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(menu, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}

}
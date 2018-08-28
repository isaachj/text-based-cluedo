import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the game logic and sets up the default game state
 */
public class CluedoGUI extends GUI {

	private final int BOARD_WIDTH = 24;

	private ArrayList<Room> rooms = new ArrayList<>();
	private Board board = new Board();
	private int numPlayers;
	private ArrayList<Weapon> weapons = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Card> deck = new ArrayList<>();
	private ArrayList<Card> tempDeck = new ArrayList<>();
	private ArrayList<Suggestion> accusations = new ArrayList<>();
	private Suggestion murder;
	private Player currentPlayer;
	private boolean gameInProgress;

	/**
	 * Constructor
	 */
	private CluedoGUI(){ setUpBoard(); }

	//-------------------------------------
	// Core Methods
	//-------------------------------------

	/**
	 * Decides what to do when the user(s) click the mouse
	 * @param e - the mouse event representing the click.
	 */
	public void onClick(MouseEvent e) {
		if(gameInProgress && board.isMouseOn(e)) { // if the click is on the board

			// Find out what row/col the mouse clicked in.
			int row = 0, col = 0;
			for(int y = 0; y < board.getBoard().length; y++) {
				for(int x = 0; x < board.getBoard()[0].length; x++) {
					if(board.get(y, x).isMouseOn(e)) {
						row = y;
						col = x;
					}
				}
			}

			if(currentPlayer.moves > 0 && currentPlayer.isValidMove(board.get(row, col))) {
				if(currentPlayer.getLocation().getRoom() == null) {currentPlayer.moves -= 1;} // Reduce move count if not in a room.
				currentPlayer.moveTo(board.get(row, col)); // Move the player.
			}

			checkPlayerTurn();
		}
	}

	/**
	 * Happens when the player decides to make a suggestion.
	 */
	protected void getASuggestion() {
		Player p = currentPlayer;

		if(p.getLocation().getRoom() == null || (p.getLocation().getRoom().equals(p.startingRoom) && !p.getJustMoved()) ) {
			return;
		}

		p.setJustMoved(false);

		Suggestion s = getSuggestion();

		if(s == null) {
			return;
		}



		if(refute(s)) {

		}

		nextPlayer();
	}

	//-------------------------------------
	// Reused Methods
	//-------------------------------------

	/**
	 * Draws the board etc.
	 * @param g - The graphics object
	 */
	public void redraw(Graphics g){
		if (g == null) { return; }
		System.out.flush(); // does nothing?
		board.draw(g);
		printRoomLabels(g);
	}

	/**
	 * Draws the key to link each room to its corresponding colour
	 * @param g graphics object to modify
	 */
	public void printRoomLabels(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("Kitchen", 50, 550);
		g.drawString("Ball Room", 150, 550);
		g.drawString("Conservatory", 250, 550);
		g.drawString("Billiard Room", 350, 550);
		g.drawString("Library", 450, 550);
		g.drawString("Study", 50, 565);
		g.drawString("Hall", 150, 565);
		g.drawString("Lounge", 250, 565);
		g.drawString("Dining Room", 350, 565);

		int i = 0;
		g.setColor(rooms.get(i++).color);
		g.fillRect(40, 540, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(140, 540, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(240, 540, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(340, 540, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(440, 540, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(40, 555, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(140, 555, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(240, 555, 10, 10);
		g.setColor(rooms.get(i++).color);
		g.fillRect(340, 555, 10, 10);
	}

	/**
	 * Print the provided cards in a list
	 * @param l - the list
	 */
	public void printCardList(List<Card> l) {
		int i = 0;
		for(Card c : l) {
			String output = i + ": " + c.getName() + " (" + c.getType() + ")\n";
			getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
			i++;
		}
	}

	/**
	 * Reset the game to a state as though it hadn't been played.
	 */
	public void resetGame(){
		rooms = new ArrayList<>();
		board = new Board();
		numPlayers = 0;
		players = new ArrayList<>();
		deck = new ArrayList<>();
		tempDeck = new ArrayList<>();
		accusations = new ArrayList<>();
		murder = null;
		currentPlayer = null;
		gameInProgress = false;
		weapons = new ArrayList<Weapon>();
		setUpBoard();
		super.redraw();
	}

	/**
	 * Tell the player it's their turn (and how many moves they have left).
	 */
	private String getPlayerTurnText() {
		String s = currentPlayer.getName()
				+ " ("
				+ (players.indexOf(currentPlayer) + 1)
				+ ") it's your turn!\n"
				+ "Click on an adjacent tile to move to it (no diagonals).\n"
				+ currentPlayer.getText();
		return s;
	}

	/**
	 * End the current player's turn
	 */
	private void nextPlayer() {
		currentPlayer.moves = 0;
		currentPlayer = currentPlayer.next;
		currentPlayer.moves = roll();
		currentPlayer.startingRoom = currentPlayer.getLocation().getRoom();
		getTextOutputArea().setText(getPlayerTurnText());
	}

	/**
	 * See if the player's turn has ended.
	 */
	private void checkPlayerTurn() {
		if(currentPlayer.moves == 0){// && currentPlayer.getLocation().getRoom() == null) {
			nextPlayer();
			return;
		} else {
			getTextOutputArea().setText(getPlayerTurnText());
		}
		//todo: finish this?
	}

	private Suggestion getSuggestion(){
		RoomCard room = null;
		WeaponCard weapon = null;
		CharacterCard character = null;

		for (Card c : deck){
			if (c instanceof RoomCard && currentPlayer.getLocation().getRoom().getName().equals(c.getName())){
				room = (RoomCard) c;
			}
		}

		String[] options = new String[weapons.size()];

		for (int i = 0; i < options.length; i++){
			options[i] = weapons.get(i).s;
		}

		Object selected = JOptionPane.showInputDialog(null, "Choose a weapon to suggest", "Selection", JOptionPane.DEFAULT_OPTION, null, options, "0");
		if ( selected != null ){//null if the user cancels.
			String selectedString = selected.toString();

			for (Card c : deck){
				if (c instanceof WeaponCard && selectedString.equals(c.getName())){
					weapon = (WeaponCard) c;
				}
			}
		}

		options = new String[6];

		for (int i = 0; i < options.length; i++){
			for (Card c : deck){
				if (c instanceof WeaponCard){
					options[i] = c.getName();
				}
			}
		}

		selected = JOptionPane.showInputDialog(null, "Choose a character to accuse", "Selection", JOptionPane.DEFAULT_OPTION, null, options, "0");
		if ( selected != null ){//null if the user cancels.
			String selectedString = selected.toString();

			for (Card c : deck){
				if (c instanceof CharacterCard && selectedString.equals(c.getName())){
					character = (CharacterCard) c;
				}
			}
		}

		if(weapon == null || room == null || character == null) {
			return null;
		}

		return new Suggestion(weapon, room, character);
	}


	//-------------------------------------
	// Utility Methods
	//-------------------------------------

	/**
	 * represents rolling two 6-sided dice
	 * @return the sum of the two dice rolls
	 */
	private int roll(){
		return (int) ((Math.random() * 5 + 1) + (Math.random() * 5 + 1));
	}

	//-------------------------------------
	// Setup Methods
	//-------------------------------------

	/**
	 * Fills the board array with empty tiles
	 */
	public void setUpBoard(){
		for (int i = 0; i < board.getBoard().length; i++){
			for (int j = 0; j < board.getBoard()[1].length; j++){
				board.getBoard()[i][j] = new Tile(i,j);
			}
		}

		setUpRooms();
		setUpCards();
	}

	/**
	 * Creates the rooms objects
	 */
	public void setUpRooms(){

		int[] safeTiles1 = {0,1,2,3,6,7};
		rooms.add(new Room("Kitchen", board, "K", safeTiles1, new Color(4, 34, 84)));
		int[] safeTiles2 = {0,1,2,3,6,7};
		rooms.add(new Room("Ball Room", board, "B", safeTiles2, new Color(3, 79, 56)));
		int[] safeTiles3 = {0,1,2,3,11,12};
		rooms.add(new Room("Conservatory", board, "C", safeTiles3, new Color(15, 79, 2)));
		int[] safeTiles4 = {2,3,4,6,7,8};
		rooms.add(new Room("Billiard Room", board, "I", safeTiles4, new Color(155, 112, 27)));
		int[] safeTiles5 = {1,3,4,12,13,14};
		rooms.add(new Room("Library", board, "L", safeTiles5, new Color(109, 6, 6)));
		int[] safeTiles6 = {5,10,7,8,6,9};
		rooms.add(new Room("Study", board, "S", safeTiles6, new Color(47, 6, 109)));
		int[] safeTiles7 = {2,5,6,11,14,15};
		rooms.add(new Room("Hall", board, "H", safeTiles7, new Color(99, 11, 44)));
		int[] safeTiles8 = {1,2,3,4,16,17};
		rooms.add(new Room("Lounge", board, "O", safeTiles8, new Color(35, 71, 114)));
		int[] safeTiles9 = {0,1,2,6,7,8};
		rooms.add(new Room("Dining Room", board, "D", safeTiles9, new Color(142, 34, 4)));

		parseLayout();

	}

	/**
	 * Adds the appropriate tiles to their respective rooms based on the layout string
	 * The layout string represents the boad with each character representing a different room
	 * 'X' represents inaccessible areas of the map, such as walls
	 * '_' represents accessible tiles that are not part of a room
	 * 'E' represents the end of the string
	 */
	public void parseLayout(){
		String layout = "XXXXXXXXX_XXXX_XXXXXXXXX" +
				"XXXXXXX___XXXX___XXXXXXX" +
				"XKKKKX__XXXBBXXX__XCCCCX" +
				"XKKKKX__XBBBBBBX__XCCCCX" +
				"XKKKKX__XBBBBBBX__CCCCCX" +
				"XXKKKX__BBBBBBBB___XXXXX" +
				"XXXXKX__XBBBBBBX________" +
				"________XBXXXXBX_______X" +
				"X_________________XXXXXX" +
				"XXXXX_____________IIIIIX" +
				"XDDDXXXX__XXXXX___XIIIIX" +
				"XDDDDDDX__XXXXX___XIIIIX" +
				"XDDDDDDD__XXXXX___XXXXIX" +
				"XDDDDDDX__XXXXX________X" +
				"XDDDDDDX__XXXXX___XXLXXX" +
				"XXXXXXDX__XXXXX__XXLLLLX" +
				"X_________XXXXX__LLLLLLX" +
				"_________________XXLLLLX" +
				"X________XXHHXX___XXXXXX" +
				"XXXXXOX__XHHHHX_________" +
				"XOOOOOX__XHHHHH________X" +
				"XOOOOOX__XHHHHX__XSXXXXX" +
				"XOOOOOX__XHHHHX__XSSSSSX" +
				"XOOOOOX__XHHHHX__XSSSSSX" +
				"XXXXXXX_XXXXXXXX_XXXXXXXE";

		for (int i = 0; layout.charAt(i) != 'E'; i++) {
			if (layout.charAt(i) == 'X') {
				board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH].setAccessible();
			} else {
				for (Room r : rooms) {
					if (layout.charAt(i) == r.getPrintable().charAt(0)) {
						r.addTile(board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH]);
					}
				}
			}
		}

		// adds the rooms to the tiles
		for (Room r : rooms){
			r.setPrintable();
		}

		doWeapons();
	}

	/**
	 *
	 */
	private void doWeapons() {
		// Weapons
		Tile startTile = rooms.get((int) (Math.random() * rooms.size())).getTiles().stream().filter(tile -> tile.getContains() == null).findAny().get();
		weapons.add(new Weapon("Candlestick", board));
		weapons.add(new Weapon("Dagger", board));
		weapons.add(new Weapon("Lead Pipe", board));
		weapons.add(new Weapon("Revolver", board));
		weapons.add(new Weapon("Rope", board));
		weapons.add(new Weapon("Spanner", board));

		for (Weapon w : weapons) {
			rooms.get((int) (Math.random() * rooms.size())).putWeapon(w);
		}
	}

	/**
	 * Pick a room at random then returns the first free tile in the room
	 * @return first tile in a randomly selected room
	 */
	private Tile randomTile(){
		return rooms.get((int) (Math.random() * rooms.size())).getTiles().stream().skip(rooms.size() - 2).filter(tile -> tile.getContains() == null).findFirst().get();
	}

	/**
	 * Generates all the cards and adds them to the deck
	 */
	private void setUpCards(){

		// Character cards
		deck.add(new CharacterCard("Mrs White", 0, 9));
		deck.add(new CharacterCard("Mr Green", 0, 14));
		deck.add(new CharacterCard("Mrs Peacock", 6, 23));
		deck.add(new CharacterCard("Prof Plum", 19, 23));
		deck.add(new CharacterCard("Miss Scarlett", 24, 7));
		deck.add(new CharacterCard("Col Mustard", 17, 0));

		// Weapon Cards
		deck.add(new WeaponCard("Candlestick"));
		deck.add(new WeaponCard("Dagger"));
		deck.add(new WeaponCard("Lead Pipe"));
		deck.add(new WeaponCard("Revolver"));
		deck.add(new WeaponCard("Rope"));
		deck.add(new WeaponCard("Spanner"));

		// Room Cards
		deck.add(new RoomCard("Kitchen"));
		deck.add(new RoomCard("Ball Room"));
		deck.add(new RoomCard("Conservatory"));
		deck.add(new RoomCard("Billiard Room"));
		deck.add(new RoomCard("Library"));
		deck.add(new RoomCard("Study"));
		deck.add(new RoomCard("Hall"));
		deck.add(new RoomCard("Lounge"));
		deck.add(new RoomCard("Dining Room"));
	}

	/**
	 * Generates the murder circumstances
	 * @return a suggestion containing the murder weapon, the locations of the murder and the identity of the murderer
	 */
	private Suggestion generateMurder(){
		WeaponCard murderWeapon;
		RoomCard murderRoom;
		CharacterCard murderEr;

		int i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof  WeaponCard)) {
			i = (int) (Math.random() * tempDeck.size());
		}
		murderWeapon = (WeaponCard) tempDeck.get(i);
		tempDeck.remove(murderWeapon);

		i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof  RoomCard)) {
			i = (int) (Math.random() * tempDeck.size());
		}
		murderRoom = (RoomCard) tempDeck.get(i);
		tempDeck.remove(murderRoom);

		i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof  CharacterCard)) {
			i = (int) (Math.random() * tempDeck.size());
		}
		murderEr = (CharacterCard) tempDeck.get(i);
		tempDeck.remove(murderEr);

		return new Suggestion(murderWeapon, murderRoom, murderEr);
	}

	/**
	 * Set up the currentPlayer variable
	 */
	protected void getPlayers() {
		if(gameInProgress) {return;}
		// Get the number of players
		numPlayers = getNumPlayers();

		ArrayList<Player> playerOptions = new ArrayList<>();
		playerOptions.add(new Player(new CharacterCard("Mrs White", 0, 9), board, Color.gray));
		playerOptions.add(new Player(new CharacterCard("Mr Green", 0, 14), board, Color.GREEN));
		playerOptions.add(new Player(new CharacterCard("Mrs Peacock", 6, 23), board, Color.CYAN));
		playerOptions.add(new Player(new CharacterCard("Prof Plum", 19, 23), board, Color.pink));
		playerOptions.add(new Player(new CharacterCard("Miss Scarlett", 24, 7), board, Color.red));
		playerOptions.add(new Player(new CharacterCard("Col Mustard", 17, 0), board, Color.yellow));


		JRadioButton[] options = new JRadioButton[6];
		JPanel p = new JPanel(new GridLayout(3,10));
		ButtonGroup bg = new ButtonGroup ();

		for(int j = 0; j < options.length; j++) {
			options[j] = new JRadioButton(playerOptions.get(j).getName());
			bg.add (options[j]);
			p.add(options[j]);
		}

		for (int i = 0; i < numPlayers; i++){

			options[i].setSelected(true);

			JOptionPane.showMessageDialog (null, p,"Choose a character", JOptionPane.PLAIN_MESSAGE);
			for (int k = 0; k < bg.getButtonCount(); k++) {
				if (options[k].isSelected()) {
					String output = "Player " + i + 1 + " has chosen to play as" + playerOptions.get(k).getName();
					getTextOutputArea().setText(output);
					players.add(playerOptions.get(k));
					playerOptions.get(k).addToBoard();
					options[k].setEnabled(false);
				}
			}
		}

		// Add next player to each player
		for(int i = 0; i < players.size()-1; i++) {
			players.get(i).next = players.get(i+1);
		}
		players.get(players.size()-1).next = players.get(0);

		currentPlayer = players.get(0);
	}

	/**
	 * @return A number between 3 and 6 decided on by the player.
	 */
	private int getNumPlayers() {
		Object[] possibilities = null;
		String s = (String) JOptionPane.showInputDialog(
				frame,
				"Enter the number of players (3-6):",
				"Start Game",
				JOptionPane.PLAIN_MESSAGE,
				null,
				possibilities,
				"");
		int n = Integer.valueOf(s);

		return (3 <= n && n <= 6) ? n : getNumPlayers();
	}

	/**
	 * Deals every card in the deck to a player
	 * @param numPlayers the current number of players
	 */
	private void dealCards(int numPlayers){
		int i = 0;
		while (!tempDeck.isEmpty()){
			Card c = tempDeck.get((int) (Math.random() * tempDeck.size()));
			players.get(i).addToHand(c);
			tempDeck.remove(c);
			i++;
			if (i == numPlayers) { i = 0; }
		}
	}

	private boolean refute(Suggestion s){
		Player p = currentPlayer.next;
		while(p.next != currentPlayer){

			ArrayList<String> options = new ArrayList<>();

			if (p.getHand().contains(s.getCharacter())) { options.add(s.getCharacter().getName()); }
			if (p.getHand().contains(s.getWeapon())) { options.add(s.getWeapon().getName()); }
			if (p.getHand().contains(s.getRoom())) { options.add(s.getRoom().getName()); }

			if (options.size() > 0){
				Object selected = JOptionPane.showInputDialog(null, "Choose a card to refute with", "Selection", JOptionPane.DEFAULT_OPTION, null, options.toArray(), "0");
				if ( selected != null ){ // null if the user cancels.
					String selectedString = selected.toString();

					JOptionPane.showConfirmDialog(
							frame,
							p.getName() + " has refuted the suggestion with " + selectedString,
							"Refuted",
							JOptionPane.OK_OPTION);
				}

				return true;
			}

		}
		return false;
	}

	protected void startGame() {
		// Setup the players
		getPlayers();

		// set the game to be in progress
		gameInProgress = true;
		currentPlayer.moves = roll();

		// generate murder
		tempDeck.addAll(deck);
		murder = generateMurder();

		// distribute cards
		dealCards(numPlayers);

		// Tell the player they can start
		getTextOutputArea().setText(getPlayerTurnText());
	}



	public static void main(String[] args) {
		CluedoGUI game = new CluedoGUI();
	}

}
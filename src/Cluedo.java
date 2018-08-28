import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains the game logic and sets up the default game state
 */
public class Cluedo extends GUI {

    public void resetGame(){} // todo: finish implementing this (preferably elsewhere in the file)
	public void startGame(){};

    private final int BOARD_WIDTH = 24;

    private ArrayList<Room> rooms = new ArrayList<>();
    private Board board = new Board();
    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> tempDeck = new ArrayList<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<Suggestion> accusations = new ArrayList<>();
    private Suggestion murder;

    private Cluedo(){ setUpBoard(); }

    /**
     * game logic
     */
    private void run(){

        // Setup
        Scanner in = new Scanner(System.in);

        while(numPlayers == 0) {
            try {
                Thread.sleep(1);
            } catch(InterruptedException e) {}
        }

        // setup players
        players.add(new Player(new CharacterCard("Mrs White", 0, 9), board, Color.gray));
        players.add(new Player(new CharacterCard("Mr Green", 0, 14), board, Color.GREEN));
        players.add(new Player(new CharacterCard("Mrs Peacock", 6, 23), board, Color.CYAN));
        if (numPlayers > 3) { players.add(new Player(new CharacterCard("Prof Plum", 19, 23), board, Color.pink)); }
        if (numPlayers > 4) { players.add(new Player(new CharacterCard("Miss Scarlett", 24, 7), board, Color.red)); }
        if (numPlayers > 5) { players.add(new Player(new CharacterCard("Col Mustard", 17, 0), board, Color.yellow));}

        /*for (Player p : players){
            p.setPrintable(String.valueOf(players.indexOf(p) + 1));
        }*/

        tempDeck.addAll(deck);
        // generate murder
        murder = generateMurder();

        // distribute cards
        dealCards(numPlayers);

        // game logic
        int count = 0;
        boolean gameWon = false;
        while (!gameWon){
            for (Player p : players) {

                // Do the player's turn.
                gameWon = doTurn(p, in);

                if(gameWon) { // Tell the player that they won.
                    String output = "Congratulations " + p.getName() + " you won!\n" +
                            "The murder circumstances:\n It was " + murder.getCharacter().getName() + " in the " + murder.getRoom().getName() + " with the " + murder.getWeapon().getName() + ".\n";
                    getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                }

            }

            if (count == 20) { break; }

            count++;
        }

    }

    /**
     * @param p - The player
     * @param in - The scanner (for getting input)
     * @return true if the game is over
     */
    private boolean doTurn(Player p, Scanner in) {

        String output = "It is " + p.getName() + "'s (Player " + String.valueOf(players.indexOf(p) + 1) + ") turn\n";
        getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        boolean moving = true;
        Room startingRoom = p.getLocation().getRoom();
        boolean suggesting = false;

        if(p.getJustMoved()) {
            p.setJustMoved(false);
            getTextOutputArea().replaceRange("You can choose to move or make a suggestion.\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            getTextOutputArea().replaceRange("Do you want to move? (Y/N)\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

            String ans = in.nextLine().toUpperCase();
            if(ans.equals("N")) {
                moving = false;
                suggesting = true;
            }
        }

        if(moving) { // If the player has decided to move

            redraw();

            int moves = roll(); // Find out how many moves the player will have
            output = "You rolled: " + moves + "\n";
            getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

            while (moves != 0) {
                getTextOutputArea().replaceRange("Enter direction to move: (WASD) or press 'H' to view your hand or 'G' to make a guess (suggestion) at the murder circumstances\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                String dir = in.nextLine();

                if (dir.toUpperCase().equals("H")) {

                    getTextOutputArea().replaceRange(p.printHand(), getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

                } else if (dir.toUpperCase().equals("G")) {
                    Room currentRoom = p.getLocation().getRoom();
                    if (currentRoom != null && !currentRoom.equals(startingRoom)) {
                        suggesting = true;
                        moves = 0;
                    } else {
                        System.out.println();
                        getTextOutputArea().replaceRange("Cannot make a suggestion from this room.\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                    }
                } else if (p.parseInput(dir)) {

                    moves--;
                    output = "You have " + moves + " moves left\n";
                    getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

                }

                redraw();

            }
        }

        // Work out of the player wants to suggest
        if(!suggesting && p.getLocation().getRoom() != null && !p.getLocation().getRoom().equals(startingRoom)) {
            getTextOutputArea().replaceRange("Do you want to make a suggestion? (Y/N)\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            String ans = in.nextLine().toUpperCase();
            if(ans.equals("Y")) {
                suggesting = true;
            }
        }

        if(suggesting) { // If the player has decided to make a suggestion
            getTextOutputArea().replaceRange("Your hand for reference: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

            getTextOutputArea().replaceRange(p.printHand(), getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            System.out.println(" ");
            Suggestion s = suggest(p, in);

            if(s == null) {
                return false;
            }
            // Initiate refuting of suggestion
            boolean refuted = refuteSuggestion(s, in);

            // move suggested character to a random tile in the suggested room
            for (Player pl : players){
                //assert s != null;
                if (s != null && pl.getName().equals(s.getCharacter().getName())){
                    for (Room r : rooms){
                        if (r.getName().equals(s.getRoom().getName())){
                            Tile movingTo = r.getTiles().stream().filter(tile -> tile.getContains() == null).findFirst().get();
                            pl.moveTo(movingTo);
                            pl.setJustMoved(true);
                        }
                    }
                }
            }

            // if the suggestion cannot be refuted add it as a possible accusation
            if (!refuted){
                assert s != null;
                s.setIsAccusation(true);
                accusations.add(s);
            }

            // check that there are possible accusations before accusing
            if (!accusations.isEmpty()) {
                getTextOutputArea().replaceRange("Do you want to make an accusation? (Y/N)\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                String ans = in.nextLine().toUpperCase();
                if(ans.equals("Y")) {
                    Suggestion accuse = getAccusation(p, in);

                    if(accuse.equals(murder)) {
                        getTextOutputArea().replaceRange("Congratulations!\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                        return true;
                    } else {
                        getTextOutputArea().replaceRange("Sorry! You're out!\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                        p.lose();
                    }
                }
            }
        }

        return false;
    }

    /**
     * Allows the player to create a suggestion
     * @param player the player making the suggestion
     * @param in the scanner to take input
     * @return the created suggestion
     */
    private Suggestion getAccusation(Player player, Scanner in){
        Card roomCard = null;
        Card weaponCard = null;
        Card charCard = null;


        int i = 0;
        getTextOutputArea().replaceRange("Pick 1 card of each type to make a suggestion!\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        for(Card c : deck) { // print the deck
            String output = i + ": " + c.getName() + " (" + c.getType() + ")\n";
            getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            i++;
        }

        getTextOutputArea().replaceRange("Pick 1 room card: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        while (!(roomCard instanceof RoomCard)) {
            try {
                roomCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
                return null;
            }
        }

        getTextOutputArea().replaceRange("Pick 1 weapon card: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        while (!(weaponCard instanceof WeaponCard)) {
            try {
                weaponCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format.");
                return null;
            }
        }

        getTextOutputArea().replaceRange("Pick 1 character card: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        while (!(charCard instanceof CharacterCard)) {
            try {
                charCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format.");
                return null;
            }
        }

        return new Suggestion(player, true, (WeaponCard) weaponCard, (RoomCard) roomCard, (CharacterCard) charCard); // Get the accusation.

    }

    /**
     * Allows players to refute suggestions
     * @param s the suggestion to be refuted
     * @param in scanner to parse inputs
     * @return true if the suggestion has been refuted
     */
    public boolean refuteSuggestion(Suggestion s, Scanner in) {

        for (Player p : players){
            if(p.equals(s.getSuggestor())) {
                continue;
            }

            boolean canRefute = false;
            ArrayList<Card> refutable = new ArrayList<>();

            for(Card c : p.getHand()) {
                if(s.contains(c)) {
                    canRefute = true;
                    refutable.add(c);
                }
            }

            if(refutable.size() > 0) {
                String output = "It is " + p.getName() + "'s (Player " + String.valueOf(players.indexOf(p) + 1) + ") turn to refute\n";
                getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
                printCardList(refutable);
                getTextOutputArea().replaceRange("The card you wish to refute with: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

                String input = in.nextLine();

                Card c = refutable.get(Integer.parseInt(input));

                if (s.contains(c)) {
                    return true;
                }

            } else {
                String output = p.getName() + " cannot refute.\n";
                getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            }
        }

        return false;
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

    public void redraw(Graphics g){
        if (g == null) { return; }
        System.out.flush(); // does nothing?
        board.draw(g);
    }

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
        rooms.add(new Room("Kitchen", board, "K", safeTiles1));
	    int[] safeTiles2 = {0,1,2,3,6,7};
        rooms.add(new Room("Ball Room", board, "B", safeTiles2));
	    int[] safeTiles3 = {0,1,2,3,11,12};
        rooms.add(new Room("Conservatory", board, "C", safeTiles3));
	    int[] safeTiles4 = {2,3,4,6,7,8};
        rooms.add(new Room("Billiard Room", board, "I", safeTiles4));
	    int[] safeTiles5 = {1,3,4,12,13,14};
	    rooms.add(new Room("Library", board, "L", safeTiles5));
	    int[] safeTiles6 = {5,10,7,8,6,9};
	    rooms.add(new Room("Study", board, "S", safeTiles6));
	    int[] safeTiles7 = {2,5,6,11,14,15};
        rooms.add(new Room("Hall", board, "H", safeTiles7));
	    int[] safeTiles8 = {1,2,3,4,16,17};
        rooms.add(new Room("Lounge", board, "O", safeTiles8));
	    int[] safeTiles9 = {0,1,2,6,7,8};
        rooms.add(new Room("Dining Room", board, "D", safeTiles9));

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
                //board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH].setPrintable("+");
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

    /**
     * Allows the player to create a suggestion
     * @param player the player making the suggestion
     * @param in the scanner to take input
     * @return the created suggestion
     */
    private Suggestion suggest(Player player, Scanner in){
        Card roomCard = null;
        Card weaponCard = null;
        Card charCard = null;


        int i = 0;
        getTextOutputArea().replaceRange("Pick 1 card of each type to make a suggestion\n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        for(Card c : deck) { // print the deck
            String output = i + ": " + c.getName() + " (" + c.getType() + ")\n";
            getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
            i++;

            // Get the room card
            if(c instanceof RoomCard && c.getName().equals(player.getLocation().getRoom().getName())) {
                roomCard = c;
            }
        }

        String output = "The room is: " + player.getLocation().getRoom().getName() + "\n";
        getTextOutputArea().replaceRange(output, getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());

        getTextOutputArea().replaceRange("Pick 1 weapon card: ", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        while (!(weaponCard instanceof WeaponCard)) {
            try {
                weaponCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
                return null;
            }
        }

        getTextOutputArea().replaceRange("Pick 1 character card: \n", getTextOutputArea().getSelectionStart(), getTextOutputArea().getSelectionEnd());
        while (!(charCard instanceof CharacterCard)) {
            try {
                charCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
                return null;
            }
        }

        return new Suggestion(player, false, (WeaponCard) weaponCard, (RoomCard) roomCard, (CharacterCard) charCard); // Get the suggestion.

    }

	/**
	 * Decides what to do when the user(s) click the mouse
	 * @param e - the mouse event representing the click.
	 */
	public void onClick(MouseEvent e) {
		if(board.isMouseOn(e)) { // if the click is on the board

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

			System.out.println(row + " " + col);
			//todo: help the player move here.
		}
    }

    /**
     * represents rolling two 6-sided dice
     * @return the sum of the two dice rolls
     */
    private int roll(){
        return (int) ((Math.random() * 5 + 1) + (Math.random() * 5 + 1));
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.run();
    }

}

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains the game logic and sets up the default game state
 */
public class Cluedo {

    private final int BOARD_WIDTH = 24;

    private ArrayList<Room> rooms = new ArrayList<>();
    private Board board = new Board();
    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> tempDeck;

    private Cluedo(){ setUpBoard(); }

    /**
     * game logic
     */
    private void run(){

        // Setup
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Please enter the number of players(3 - 6): ");
            try {
                numPlayers = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }

            System.out.println("You entered : " + numPlayers);
        } while (numPlayers > 6 || numPlayers < 3);

        // setup players
        players.add(new Player(new CharacterCard("Mrs White", 0, 9), board));
        players.add(new Player(new CharacterCard("Mr Green", 0, 14), board));
        players.add(new Player(new CharacterCard("Mrs Peacock", 6, 23), board));
        if (numPlayers > 3) { players.add(new Player(new CharacterCard("Prof Plum", 19, 23), board)); }
        if (numPlayers > 4) { players.add(new Player(new CharacterCard("Miss Scarlett", 24, 7), board)); }
        if (numPlayers > 5) { players.add(new Player(new CharacterCard("Col Mustard", 17, 0), board));}

        for (Player p : players){
            p.setPrintable(String.valueOf(players.indexOf(p) + 1));
        }

        tempDeck = deck;
        // generate murder
        Suggestion murder = generateMurder();

        // distribute cards
        dealCards(numPlayers);

        // game logic
        int count = 0;
        redraw();
        boolean gameWon = false;
        while (!gameWon){
            for (Player p : players) {

                // Do the player's turn.
                gameWon = doTurn(p, in);

            }

            redraw();

            if (count == 20) { break; }

            count++;
        }

    }

    /**
     * @param p - The player
     * @param in - The scanner (for getting input)
     * @return if the game is over
     */
    private boolean doTurn(Player p, Scanner in) {

        System.out.println("It is " + p.getName() + "'s (Player " + (p.getPrintable()) + ") turn");

        int moves = roll(); // Find out how many moves the player will have
        System.out.println("You rolled: " + moves);

        while (moves != 0) {

            System.out.println("Enter direction to move: (WASD) or press 'H' to view your hand");
            String dir = in.nextLine();

            if ( dir.toUpperCase().equals("H") ){

                p.printHand(); // for testing dealing

            } else if(p.parseInput(dir)){

                redraw();
                moves--;
                System.out.println("You have " + moves + " moves left");

            } else {

                redraw();

            }

        }

        return false;
    }

    public void redraw(){
        System.out.flush(); // does nothing?
        board.draw();
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

        rooms.add(new Room("Kitchen", board, "K"));
        rooms.add(new Room("Ball Room", board, "B"));
        rooms.add(new Room("Conservatory", board, "C"));
        rooms.add(new Room("Billiard Room", board, "I"));
        rooms.add(new Room("Library", board, "L"));
        rooms.add(new Room("Study", board, "S"));
        rooms.add(new Room("Hall", board, "H"));
        rooms.add(new Room("Lounge", board, "O"));
        rooms.add(new Room("Dining Room", board, "D"));

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
                board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH].setPrintable("+");
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
     * TODO: account for object locations
     * @param player the player making the suggestion
     * @param in the scanner to take input
     * @return the created suggestion
     */
    private Suggestion suggest(Player player, Scanner in){
        Card roomCard = null;
        Card weaponCard = null;
        Card charCard = null;


        int i = 0;
        System.out.println("Pick 1 card of each type to make a suggestion");
        for(Card c : deck) {

            System.out.println(i + ": " + c.getName() + " (" + c.getType() + ")\n");
            i++;
        }

        System.out.println("Pick 1 room card: ");
        while (!(roomCard instanceof RoomCard)) {
            try {
                roomCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }
        }

        System.out.println("Pick 1 weapon card: ");
        while (!(weaponCard instanceof WeaponCard)) {
            try {
                weaponCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }
        }

        System.out.println("Pick 1 character card: ");
        while (!(charCard instanceof CharacterCard)) {
            try {
                charCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }
        }

        return new Suggestion((WeaponCard) weaponCard, (RoomCard) roomCard, (CharacterCard) charCard);

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

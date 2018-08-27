import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains the game logic and sets up the default game state
 */
public class Cluedo extends GUI {

    public void resetGame(){} // todo: finish implementing this (preferably elsewhere in the file)

    private final int BOARD_WIDTH = 24;

    private ArrayList<Room> rooms = new ArrayList<>();
    private Board board = new Board();
    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> tempDeck = new ArrayList<>();
    private ArrayList<Suggestion> accusations = new ArrayList<>();
    private Suggestion murder;

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
                    System.out.println("Congratulations " + p.getName() + " you won!");
                    System.out.println("The murder circumstances:\n It was " + murder.getCharacter().getName() + " in the " + murder.getRoom().getName() + " with the " + murder.getWeapon().getName() + ".");
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

        System.out.println("It is " + p.getName() + "'s (Player " + String.valueOf(players.indexOf(p) + 1) + ") turn");
        boolean moving = true;
        Room startingRoom = p.getLocation().getRoom();
        boolean suggesting = false;

        if(p.getJustMoved()) {
            p.setJustMoved(false);
            System.out.println("You can choose to move or make a suggestion.");
            System.out.println("Do you want to move? (Y/N)");

            String ans = in.nextLine().toUpperCase();
            if(ans.equals("N")) {
                moving = false;
                suggesting = true;
            }
        }

        if(moving) { // If the player has decided to move

            redraw();

            int moves = roll(); // Find out how many moves the player will have
            System.out.println("You rolled: " + moves);

            while (moves != 0) {

                System.out.println("Enter direction to move: (WASD) or press 'H' to view your hand or 'G' to make a guess (suggestion) at the murder circumstances");
                String dir = in.nextLine();

                if (dir.toUpperCase().equals("H")) {

                    p.printHand(); // for testing dealing

                } else if (dir.toUpperCase().equals("G")) {
                    Room currentRoom = p.getLocation().getRoom();
                    if (currentRoom != null && !currentRoom.equals(startingRoom)) {
                        suggesting = true;
                        moves = 0;
                    } else {
                        System.out.println("Cannot make a suggestion from this room.");
                    }
                } else if (p.parseInput(dir)) {

                    moves--;
                    System.out.println("You have " + moves + " moves left");

                }

                redraw();

            }
        }

        // Work out of the player wants to suggest
        if(!suggesting && p.getLocation().getRoom() != null && !p.getLocation().getRoom().equals(startingRoom)) {
            System.out.println("Do you want to make a suggestion? (Y/N)");
            String ans = in.nextLine().toUpperCase();
            if(ans.equals("Y")) {
                suggesting = true;
            }
        }

        if(suggesting) { // If the player has decided to make a suggestion
            System.out.println("Your hand for reference: ");
            p.printHand();
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
                System.out.println("Do you want to make an accusation? (Y/N");
                String ans = in.nextLine().toUpperCase();
                if(ans.equals("Y")) {
                    Suggestion accuse = getAccusation(p, in);

                    if(accuse.equals(murder)) {
                        System.out.println("Congratulations!");
                        return true;
                    } else {
                        System.out.println("Sorry! You're out!");
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
        System.out.println("Pick 1 card of each type to make a suggestion");
        for(Card c : deck) { // print the deck
            System.out.println(i + ": " + c.getName() + " (" + c.getType() + ")");
            i++;
        }

        System.out.println("Pick 1 room card: ");
        while (!(roomCard instanceof RoomCard)) {
            try {
                roomCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
                return null;
            }
        }

        System.out.println("Pick 1 weapon card: ");
        while (!(weaponCard instanceof WeaponCard)) {
            try {
                weaponCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format.");
                return null;
            }
        }

        System.out.println("Pick 1 character card: ");
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
                System.out.println("It is " + p.getName() + "'s (Player " + String.valueOf(players.indexOf(p) + 1) + ") turn to refute");
                printCardList(refutable);
                System.out.println("The card you wish to refute with: ");

                String input = in.nextLine();

                Card c = refutable.get(Integer.parseInt(input));

                if (s.contains(c)) {
                    return true;
                }

            } else {
                System.out.println(p.getName() + " cannot refute.");
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
            System.out.println(i + ": " + c.getName() + " (" + c.getType() + ")");
            i++;
        }
    }

    public void redraw(Graphics g){
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
        System.out.println("Pick 1 card of each type to make a suggestion");
        for(Card c : deck) { // print the deck
            System.out.println(i + ": " + c.getName() + " (" + c.getType() + ")");
            i++;

            // Get the room card
            if(c instanceof RoomCard && c.getName().equals(player.getLocation().getRoom().getName())) {
                roomCard = c;
            }
        }

        System.out.println("The room is: " + player.getLocation().getRoom().getName());

        System.out.println("Pick 1 weapon card: ");
        while (!(weaponCard instanceof WeaponCard)) {
            try {
                weaponCard = deck.get(Integer.parseInt(in.nextLine()));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
                return null;
            }
        }

        System.out.println("Pick 1 character card: ");
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

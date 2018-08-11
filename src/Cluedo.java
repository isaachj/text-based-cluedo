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

    private Cluedo(){ setUpBoard(); }

    /**
     * game logic
     */
    private void run(){
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

        // generate murder
        Suggestion murder = generateMurder();

        // distribute cards

        // game logic
        int count = 0;
        redraw();
        while (true){
            for (Player p : players) {

                System.out.println("It is " + p.getName() + "'s (Player " + (p.getPrintable()) + ") turn");

                int moves = roll();

                System.out.println("You rolled: " + moves);

                while (moves != 0) {

                    System.out.println("Enter direction to move: (WASD)");
                    String dir = in.nextLine();

                    if (!p.parseMove(dir)){

                        System.out.println("Invalid Direction");

                    }else {

                        redraw();
                        moves--;
                        System.out.println("You have " + moves + " moves left");
                    }
                }
            }

            redraw();

            if (count == 20) { break; }

            count++;
        }

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
        String layout =
                "XXXXXXXXX_XXXX_XXXXXXXXX" +
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

        int i = (int) (Math.random() * deck.size());
        while (!(deck.get(i) instanceof  WeaponCard)) {
            i = (int) (Math.random() * deck.size());
        }
        murderWeapon = (WeaponCard) deck.get(i);
        deck.remove(murderWeapon);

        i = (int) (Math.random() * deck.size());
        while (!(deck.get(i) instanceof  RoomCard)) {
            i = (int) (Math.random() * deck.size());
        }
        murderRoom = (RoomCard) deck.get(i);
        deck.remove(murderRoom);

        i = (int) (Math.random() * deck.size());
        while (!(deck.get(i) instanceof  CharacterCard)) {
            i = (int) (Math.random() * deck.size());
        }
        murderEr = (CharacterCard) deck.get(i);
        deck.remove(murderEr);

        return new Suggestion(murderWeapon, murderRoom, murderEr);
    }

    /**
     * represents rolling two 6-sided dice
     * @return the sum of the two dice rolls
     */
    private int roll(){
        return (int) ((Math.random() * 6) + (Math.random() * 6));
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.run();
    }

}

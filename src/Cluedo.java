import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains the game logic and sets up the default game state
 */
public class Cluedo {

    //public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final int BOARD_WIDTH = 24;

    private ArrayList<Room> rooms = new ArrayList<>();
    private Board board = new Board();
    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();

    private Cluedo(){ setUpBoard(); }

    /**
     * game logic
     */
    private void run(){
        redraw();

        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");

        try{
            numPlayers = Integer.parseInt(in.nextLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        System.out.println("You entered : " + numPlayers);

        // setup players here
        players.add(new Player(board.getBoard()[2][2], board));

        // generate murder

        // distribute cards

        // basic logic goes here
        int count = 0;
        while (true){

            for (Player p : players) {

                int moves = roll();
                System.out.println("You rolled: " + moves);

                while (moves != 0) {
                    System.out.println("Enter direction to move: (WASD)");
                    String dir = in.nextLine();
                    p.parseMove(dir);
                    redraw();
                    moves--;
                    System.out.println("You have " + moves + " moves left");
                }
            }

            redraw();


            if (count == 20) { break; }

            count++;
        }

    }

    public void redraw(){ board.draw(); }

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
     * 'X' represents inaccessible areas of the map and walls
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
                "XXXXXLX__XHHHHX_________" +
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

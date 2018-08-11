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
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");

        try{
            numPlayers = Integer.parseInt(in.nextLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        System.out.println("You entered : " + numPlayers);

        // setup players here
        players.add(new Player(board.getBoard()[1][1], board));

        // generate murder

        // distribute cards

        // basic logic goes here
        int count = 0;
        while (true){

            for (Player p : players) {
                System.out.print("Enter direction to move: (WASD)");
                String dir = in.nextLine();
                p.move(dir);
            }






            redraw();


            if (count == 5) { break; }

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
     * 'X' represents inaccessible areas of the map
     * '_' represents accessible tiles tha are not part of a room
     * 'E' represents the end of the string
     */
    public void parseLayout(){
        String layout =
                "XXXXXXXXX_XXXX_XXXXXXXXX" +
                "KKKKKKX___BBBB___XCCCCCC" +
                "KKKKKK__BBBBBBBB__CCCCCC" +
                "KKKKKK__BBBBBBBB__CCCCCC" +
                "KKKKKK__BBBBBBBB__CCCCCC" +
                "KKKKKK__BBBBBBBB___CCCCX" +
                "XKKKKK__BBBBBBBB________" +
                "________BBBBBBBB_______X" +
                "X_________________IIIIII" +
                "DDDDD_____________IIIIII" +
                "DDDDDDDD__XXXXX___IIIIII" +
                "DDDDDDDD__XXXXX___IIIIII" +
                "DDDDDDDD__XXXXX___IIIIII" +
                "DDDDDDDD__XXXXX________X" +
                "DDDDDDDD__XXXXX___LLLLLX" +
                "DDDDDDDD__XXXXX__LLLLLLL" +
                "X_________XXXXX__LLLLLLL" +
                "_________________LLLLLLL" +
                "X________HHHHHH___LLLLLX" +
                "OOOOOOO__HHHHHH_________" +
                "OOOOOOO__HHHHHH________X" +
                "OOOOOOO__HHHHHH__SSSSSSS" +
                "OOOOOOO__HHHHHH__SSSSSSS" +
                "OOOOOOO__HHHHHH__SSSSSSS" +
                "OOOOOOX_XHHHHHHX_XSSSSSSE";

        for (int i = 0; layout.charAt(i) != 'E'; i++) {
            if (layout.charAt(i) == 'X') {
                board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH].setAccessible();
                board.getBoard()[i / BOARD_WIDTH][i % BOARD_WIDTH].setPrintable("X");
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

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.run();
    }

}

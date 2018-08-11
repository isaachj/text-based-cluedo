import java.util.ArrayList;

/**
 * Contains the game logic
 */
public class Cluedo {

    ArrayList<Room> rooms = new ArrayList<>();
    Board board = new Board();

    private Cluedo(){
        setUpBoard();
        redraw();
    }

    public void redraw(){ board.draw(); }

    public void setUpBoard(){
        for (int i = 0; i < board.getBoard().length; i++){
            for (int j = 0; j < board.getBoard()[1].length; j++){
                board.getBoard()[i][j] = new Tile(i,j);
            }
        }

        setUpRooms();
    }

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

    // theres gotta be a better way
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
                board.getBoard()[i / 24][i % 24].setAccessible();
                board.getBoard()[i / 24][i % 24].setPrintable(" ");
            } else {
                for (Room r : rooms) {
                    if (layout.charAt(i) == r.getPrintable().charAt(0)) {
                        r.addTile(board.getBoard()[i / 24][i % 24]);
                    }
                }
            }
        }

        for (Room r : rooms){
            r.setPrintable();
        }
    }

    public static void main(String[] args) { new Cluedo(); }

}

import java.util.ArrayList;

/**
 * represents a room on the game board
 */
public class Room {

    private String name;
    private ArrayList<Tile> tiles = new ArrayList<>(); // list of the tiles inside the room
    private Board board;

    Room(String name, Board board){
        this.name = name;
        this.board = board;


    }

    public String getName() {
        return name;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setPrintable(){
        for (int i = 5; i < 10; i++){
            for (int j = 5; j < 10; j++){
                board.getBoard()[i][j].setPrintable("R");
            }
        }
    }
}

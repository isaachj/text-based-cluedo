import java.util.ArrayList;

/**
 * represents a room on the game board
 */
public class Room {

    private String name;
    private ArrayList<Tile> tiles = new ArrayList<>(); // list of the tiles inside the room
    private Board board;
    private String printable;

    Room(String name, Board board, String printable){
        this.name = name;
        this.board = board;
        this.printable = printable;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void addTile(Tile tile){ tiles.add(tile); }

    public String getPrintable(){ return printable; }

    /**
     * Sets the character for each tile to be drawn as
     * also associates each tile with the room
     */
    void setPrintable(){
        for (Tile t : tiles){
            t.setRoom(this);
            t.setPrintable(printable);
        }
    }
}

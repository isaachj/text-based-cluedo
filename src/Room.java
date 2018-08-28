import java.util.ArrayList;

/**
 * represents a room on the game board
 */
public class Room {

    private String name;
    private ArrayList<Tile> tiles = new ArrayList<>(); // list of the tiles inside the room
    private Board board;
    private String printable;
    private int[] safeTiles;

    Room(String name, Board board, String printable, int[] safeTiles){
        this.name = name;
        this.board = board;
        this.printable = printable;
        this.safeTiles = safeTiles;
    }

    public boolean equals(Room r) {
        if(r != null && name.equals(r.getName())) {
            return true;
        }
        return false;
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

    void putWeapon(Weapon w) {
        for(int i : safeTiles) {
            if(tiles.get(i).getContains() == null) {
                w.putIn(tiles.get(i));
                break;
            }
        }
    }
}

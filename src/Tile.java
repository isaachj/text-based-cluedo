/**
 * Represents a square on the game board
 */

public class Tile {

    private int row, col;
    private Room room;
    private String printable = "_";
    private boolean accessible = true;

    Tile (int row, int col){
        this.row = row;
        this.col = col;
    }

    Tile (int row, int col, Room room){
        this.row = row;
        this.col = col;
        this.room = room;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isAccessible() { return accessible; }

    public void setAccessible() {
        if (accessible){
            accessible = false;
        } else {
            accessible = true;
        }
    }

    public String getPrintable(){ return printable; }

    public void setPrintable(String printable) {
        this.printable = printable;
    }
}

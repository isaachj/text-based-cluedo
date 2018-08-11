/**
 * Represents a square on the game board
 */

public class Tile {

    private int row, col;
    private Room room;
    private String printable = " ";
    private boolean accessible = true;
    private Object contains = null;

    Tile (int row, int col){
        this.row = row;
        this.col = col;
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

    public void setRoom(Room room) { this.room = room; }

    public boolean isAccessible() { return accessible; }

    public void setAccessible() { accessible = !accessible; }

    public String getPrintable(){ return printable; }

    public void setPrintable(String printable) {
        this.printable = printable;
    }
}

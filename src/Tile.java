import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Represents a square on the game board
 */

public class Tile {

    public static int width = 20;
    private int row, col;
    private Room room;
    private String printable = " ";
    private boolean accessible = true;
    private Player contains = null;

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

    public void setContains(Player contains) {
        this.contains = contains;
    }

    public Player getContains() {
        return contains;
    }

    public void setRoom(Room room) { this.room = room; }

    public boolean isAccessible() { return accessible; }

    public void setAccessible() { accessible = !accessible; }

    /*public String getPrintable(){
        if (contains != null) { return contains.getPrintable(); }
        return printable;
    }*/

    public void setPrintable(String printable) {
        this.printable = printable;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        if (!isAccessible()) {
            g.fillRect(col * width, row * width, width, width);
            return;
        }

        g.drawRect(col * width, row * width, width, width);

        if (contains != null) {
            g.setColor(contains.c);
            g.fillOval(col * width + 2, row * width + 2, width - 4, width - 4);
        }
    }

	/**
	 * @param e - The mouse click event.
	 * @return Whether or not the mouse click happened on top of this tile.
	 */
	public boolean isMouseOn(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	return x >= width*col && y >= width*row && x < width*(col+1) && y < width*(row+1);
    }
}

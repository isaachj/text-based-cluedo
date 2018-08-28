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
    private boolean accessible = true;
    private Movable contains = null;

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

    public void setContains(Movable contains) {
        this.contains = contains;
    }

    public Movable getContains() {
        return contains;
    }

    public void setRoom(Room room) { this.room = room; }

    public boolean isAccessible() { return accessible; }

    public void setAccessible() { accessible = !accessible; }

    public void draw(Graphics g){
        g.setColor(Color.black);

        // draws inaccessible tiles
        if (!isAccessible()) {
            g.fillRect(col * width + 20, row * width + 20, width, width);
            return;
        }

        // colours room tiles
        if (room != null) {
            g.setColor(room.color);
            g.fillRect(col * width + 20, row * width + 20, width, width);
        }

        // draws grid
        g.setColor(Color.black);
        g.drawRect(col * width + 20, row * width + 20, width, width);

        // draws players and weapons
        if (contains != null) {
            g.setColor(contains.c);
            if (contains instanceof Player) {
                g.fillOval(col * width + 22, row * width + 22, width - 4, width - 4);
            } else if ( contains instanceof Weapon) {
                g.drawString(contains.s.substring(0,2), col * width + 23, row * width + 33);
            }
        }
    }

	/**
	 * @param e - The mouse click event.
	 * @return Whether or not the mouse click happened on top of this tile.
	 */
	public boolean isMouseOn(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	return x >= width * col + 20&& y >= width * row + 20 && x < width * (col+1) + 20 && y < width * (row+1) + 20;
    }
}

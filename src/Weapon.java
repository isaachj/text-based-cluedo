import java.awt.*;

public class Weapon extends Movable {

    private Tile location;
    private Board board;

    public Weapon (String name, Board b){
        super(Color.BLACK, name);
        this.board = b;
    }

	/**
	 * Add the weapon to a tile.
	 * @param t - the tile to add the fire to.
	 */
	public void putIn(Tile t) {
    	if(location != null) {
    		location.setContains(null);
    		location = null;
	    }
	    location = t;
    	t.setContains(this);
    }

    public Tile getLocation() {
    	return location;
    }

}

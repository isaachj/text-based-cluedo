import java.awt.*;

public class Weapon extends Movable {

    private Tile location;
    private Board board;

    public Weapon (int startX, int startY, String name, Board b){
        super(Color.BLACK, name);
        this.board = b;
        location = board.get(startY, startX);
        this.location.setContains(this);
    }

}

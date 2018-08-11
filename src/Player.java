/**
 * represents a player
 */
public class Player {

    private Tile location;
    private String printable = "P";
    private Board board;

    public Player(Tile startLocation, Board board){
        this.location = startLocation;
        this.location.setContains(this);
        this.board = board;
    }

    public void move(String input){

        switch (input){
            case "W":
                location.setContains(null);
                location = board.getBoard()[location.getRow() - 1][location.getCol()];
                location.setContains(this);
                break;
            case "S":
                location.setContains(null);
                location = board.getBoard()[location.getRow() + 1][location.getCol()];
                location.setContains(this);
                break;
            case "A":
                location.setContains(null);
                location = board.getBoard()[location.getRow()][location.getCol() - 1];
                location.setContains(this);
                break;
            case "D":
                location.setContains(null);
                location = board.getBoard()[location.getRow()][location.getCol() + 1];
                location.setContains(this);
                break;
            default:
                System.out.println("Not a valid direction");
                break;

        }

    }

    public String getPrintable() {
        return printable;
    }
}
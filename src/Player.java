import java.util.ArrayList;

/**
 * represents a player
 */
public class Player {

    private Tile location;
    private String printable = "P";
    private Board board;
    private ArrayList<Card> hand;

    public Player(Tile startLocation, Board board){
        this.location = startLocation;
        this.location.setContains(this);
        this.board = board;
        hand = new ArrayList<Card>();
    }

    /**
     * Moves the character based on the input.
     * @param input - A string representing the input character.
     */
    public void parseMove(String input){

        switch (input.toUpperCase()){
            case "W":
                move(-1, 0);
                break;
            case "S":
                move(1, 0);
                break;
            case "A":
                move(0, -1);
                break;
            case "D":
                move(0, 1);
                break;
            default:
                System.out.println("Not a valid direction");
                break;

        }

    }

    /**
     * Moves the player by the specified amount.
     * @param drow - The change in row.
     * @param dcol - The change in column.
     */
    private void move(int drow, int dcol) {

        Tile next = board.getBoard()[location.getRow() + drow][location.getCol() + dcol];
        if(next.isAccessible()) {
            location.setContains(null);
            location = next;
            location.setContains(this);
        } else {
            System.out.println("Cannot move through walls.");
        }
    }

    public void addToHand(Card c) {
        hand.add(c);
    }

    public String getPrintable() {
        return printable;
    }
}
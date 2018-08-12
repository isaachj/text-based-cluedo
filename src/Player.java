import java.util.ArrayList;

/**
 * represents a player
 */
public class Player {

    private Tile location;
    private String printable = "P";
    private String name;
    private Board board;
    private ArrayList<Card> hand; // The cards in the player's hand

    public Player(CharacterCard card, Board board){
        this.location = board.get(card.getStartRow(), card.getStartCol());
        this.location.setContains(this);
        this.board = board;
        name = card.getName();
        hand = new ArrayList<Card>();
    }

    /**
     * Decides what to do based on the input.
     * @param input - A string representing the input character.
     */
    public boolean parseInput(String input){

        switch (input.toUpperCase()){
            case "W":
                return move(-1, 0);
            case "S":
                return move(1, 0);
            case "A":
                return move(0, -1);
            case "D":
                return move(0, 1);
            default:
                System.out.println("Not a valid direction");
                return false;

        }

    }

    /**
     * Moves the player by the specified amount.
     * @param drow - The change in row.
     * @param dcol - The change in column.
     */
    private boolean move(int drow, int dcol) {

        boolean moveCost;

        Tile next = board.get(location.getRow() + drow, location.getCol() + dcol);
        if(next != null && next.isAccessible()) {

            // If the player is moving within a room, don't make it cost anything.
            moveCost = true;
            if(next.getRoom() != null && location.getRoom() != null) moveCost = false;

            location.setContains(null);
            location = next;
            location.setContains(this);
        } else {
            System.out.println("Cannot move through walls.");
            moveCost = false;
        }

        return moveCost;
    }

    /**
     * Add a card to the player's hand
     * @param c - The card to add.
     */
    public void addToHand(Card c) {
        hand.add(c);
    }

    /**
     * Returns whether or not the hand contains the provided card.
     * @param c - The card
     * @return Whether or not the hand contains the provided card
     */
    public boolean handContains(Card c) {
        return hand.contains(c);
    }

    /**
     * Outputs the hand to the console
     */
    public void printHand() {
        int i = 0;
        for(Card c : hand) {
            System.out.println(i + ": " + c.getName() + " (" + c.getType() + ")\n");
            i++;
        }
    }

    /**
     * Moves the player to the target tile.
     * @param t - The target tile
     */
    public void moveTo(Tile t) {
        location.setContains(null);
        location = t;
        location.setContains(this);
    }

    /**
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns player's board symbol
     * @return - The name of the player
     */
    public String getPrintable() {
        return printable;
    }

    public void setPrintable(String printable){
        this.printable = printable;
    }

    /**
     * @return the location of the player
     */
    public Tile getLocation() {
        return location;
    }
}
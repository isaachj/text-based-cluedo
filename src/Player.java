import java.awt.*;
import java.util.ArrayList;

/**
 * represents a player
 */
public class Player extends Movable{

    private Tile location;
    //private String printable = "P";
    private String name;
    private Board board;
    private ArrayList<Card> hand; // The cards in the player's hand
    private CharacterCard card;
    private boolean justMoved = false; // Whether or not the player has been moved by a suggestion.
    private boolean hasLost = false;

    // For managing turns
	public Player next;
	public int moves = 0;
	public Room startingRoom = null;

    public Player(CharacterCard card, Board board, Color c){
    	super(c, "");
        this.location = board.get(card.getStartRow(), card.getStartCol());
        this.board = board;
        name = card.getName();
        hand = new ArrayList<Card>();
        this.card = card;
    }

    public void addToBoard(){
        this.location.setContains(this);
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
     * returns hand as a string to be outputted
     */
    public String printHand() {
        int i = 0;
        String output = "";
        for(Card c : hand) {
            output = output + i + ": " + c.getName() + " (" + c.getType() + ")\n";
            i++;
        }

        return output;
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
     * @return whether or not the player has lost.
     */
    public boolean hasLost() {return hasLost;}

    public void lose() { hasLost = true;}

    /**
     * Returns true if the card provided represents the player's character.
     * @param c - The card to see if the player equals
     * @return Whether or not c is the player
     */
    public boolean equals(Card c) {
        return card.equals(c);
    }

    /**
     * Returns true if the player provided is this player
     * @param p - The player which is being compared
     * @return Whether or not p is the player
     */
    public boolean equals(Player p) {
        return p.getName().equals(name);
    }

    /**
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @param b - Whether or not the player has just been moved by another player making a suggestion.
     */
    public void setJustMoved(boolean b) {
        justMoved = b;
    }

    /**
     * @return Whether or not the player has just been moved by another player making a suggestion.
     */
    public boolean getJustMoved() {
        return justMoved;
    }

    /**
     * Returns player's board symbol
     * @return - The name of the player
     */
    /*public String getPrintable() {
        return printable;
    }

    public void setPrintable(String printable){
        this.printable = printable;
    }*/

    /**
     * @return the location of the player
     */
    public Tile getLocation() {
        return location;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

	/**
	 * @return The appropriate text based on the state of the player.
	 */
	public String getText() {
    	if(moves != 0) {
    		String s = "Moves: " + moves;
		    if(location.getRoom() != null && !location.getRoom().equals(startingRoom) ) {
		    	s += "\nYou can also make a suggestion from this room.";
		    }
		    return s;
	    } else if(location.getRoom() != null && !location.getRoom().equals(startingRoom) ) {
    		return "You can make a suggestion from this room!";
	    } else {
    		return "No moves left.";
	    }
    }

    public boolean isValidMove(Tile t) {
		if(!t.isAccessible() || !(t.getContains() == null)) return false;

		if( // Check that the tile is one of the adjacent tiles
				((location.getRow()+1 == t.getRow() || location.getRow()-1 == t.getRow()) && location.getCol() == t.getCol()) || // north/south
				(location.getRow() == t.getRow() && (location.getCol()-1 == t.getCol() || location.getCol()+1 == t.getCol()))    // east/west
		) return true;
		return false;
    }
}
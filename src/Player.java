import java.awt.*;
import java.util.ArrayList;

/**
 * represents a player
 */
public class Player {

    private Tile location;
    //private String printable = "P";
    private String name;
    private Board board;
    private ArrayList<Card> hand; // The cards in the player's hand
    private CharacterCard card;
    private boolean justMoved = false; // Whether or not the player has been moved by a suggestion.
    private boolean hasLost = false;
    Color c;

    public Player(CharacterCard card, Board board, Color c){
        this.location = board.get(card.getStartRow(), card.getStartCol());
        this.location.setContains(this);
        this.board = board;
        name = card.getName();
        hand = new ArrayList<Card>();
        this.card = card;
        this.c = c;
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

    public void lose() {hasLost = true;}

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
}
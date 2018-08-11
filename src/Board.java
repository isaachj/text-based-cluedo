/**
 * represents the game board
 */

public class Board {

    private Tile[][] board;

    public Board() {
        board = new Tile[25][24];
    }

    /**
     * todo: remove this (Obsolete)
     * @return The board array
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Returns the target tile, or null if the tile is out of bounds.
     * @param row - row of target tile
     * @param col - col of target tile
     * @return The target tile.
     */
    public Tile get(int row, int col) {
        if(row < board.length && row >= 0 && col < board[0].length && col >= 0) {
            return board[row][col];
        }
        return null;
    }

    /**
     * Draws the board into System.out
     */
    public void draw(){
        String output = "";

        for (int i = 0; i < board.length; i++){

            for (int j = 0; j < board[1].length; j++){

                output = output.concat(" " + board[i][j].getPrintable());

            }

            output = output.concat("\n");
        }

        System.out.print(output);
    }
}

/**
 * represents the game board
 */
public class Board {

    private Tile[][] board = new Tile[26][25];

    public Tile[][] getBoard() {
        return board;
    }

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

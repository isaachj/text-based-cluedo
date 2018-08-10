public class Board {

    private Tile[][] board = new Tile[24][25];

    public Tile[][] getBoard() {
        return board;
    }

    public void draw(){
        String output = "";

        /*// top row
        output = output.concat("+");

        for (int j = 0; j < board[1].length; j++){

            output = output.concat(" - +");

        }

        output = output.concat("\n");

*/
        for (int i = 0; i < board.length; i++){

            //output = output.concat("| ");

            for (int j = 0; j < board[1].length; j++){

                output = output.concat(" " + board[i][j].getPrintable()/* + " | "*/);

            }

            /*output = output.concat("\n");

           // lower border
            for (int j = 0; j < board[1].length; j++){

                output = output.concat(" - +");

            }*/

            output = output.concat("\n");
        }

        System.out.print(output);
    }
}

public class Cluedo {

    public static void main(String[] args) {
        Board b = new Board();

        for (int i = 0; i < b.getBoard().length; i++){
            for (int j = 0; j < b.getBoard()[1].length; j++){

                b.getBoard()[i][j] = new Tile(i,j);

            }
        }

        new Room("test", b).setPrintable();
        b.draw();
    }
}

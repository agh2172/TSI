import java.util.ArrayList;

public class Game {
    private Tile[][] easyBoard = new Tile[9][9];
    private Tile[][] mediumBoard = new Tile[16][16];
    private Tile[][] hardBoard = new Tile[30][16];

    private int mines;


    private Tile[][] gameBoard;

    //d1 = easy, d2=medium, d3=jard
    public Game(int difficulty){
        if(difficulty==1) {
            gameBoard = easyBoard;
            mines=10;
        } else if (difficulty==2) {
            gameBoard = mediumBoard;
            mines=40;
        } else {
            gameBoard = hardBoard;
            mines=99;
        }
    }

    //Upon first move, initialise board
    public void initialiseBoard(int r, int c){
        int mineCount=0;
        double threshold = (double) mines /(easyBoard.length*easyBoard[0].length)*1.15;
        //System.out.println("Threshold: " + threshold);
        boolean mine = false;
        double random = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                mine=false;
                if(mineCount<mines && (i!=r && j!=c)){
                    //could be a new mine
                    random = Math.random();
                    //System.out.println("Random: " + random);
                    if(random<threshold){
                        //System.out.println("True");
                        mine=true;
                        mines++;
                    }
                }
                gameBoard[i][j] = new Tile(mine);
                gameBoard[i][j].setShow(true);
            }
        }
        iniitialiseCounts();
    }

    public void iniitialiseCounts(){
        for(int i=0; i<gameBoard.length; i++){
            for(int j=0; j<gameBoard[i].length; j++){
                if(gameBoard[i][j].isMine()){
                    //update surrounding tiles with count
                    ArrayList<Tile> surrounding = new ArrayList<Tile>();
                    getNeighbours(i, j, surrounding);
                    for(Tile tile : surrounding){
                        tile.count++;
                    }
                }
            }
        }
    }

    private void getNeighbours(int r, int c, ArrayList<Tile> surrounding){
        //@todo
        int rowL = gameBoard.length;
        int colL = gameBoard[0].length;
        boolean bottomRow = false;
        boolean topRow = false;
        boolean firstColumn = false;
        boolean lastColumn = false;
        if(r==0){
            bottomRow=true;
        } else if (r == rowL-1) {
            topRow=true;
        }
        if(c==0){
            firstColumn=true;
        } else if (c == colL-1) {
            lastColumn=true;
        }
        if(bottomRow&&firstColumn){
            surrounding.add(gameBoard[1][1]);
            surrounding.add(gameBoard[0][1]);
            surrounding.add(gameBoard[1][0]);
        } else if (bottomRow&&lastColumn) {
            surrounding.add(gameBoard[0][colL-2]);
            surrounding.add(gameBoard[1][colL-2]);
            surrounding.add(gameBoard[1][colL-1]);
        } else if (bottomRow) {
            //first those in the same row
            surrounding.add(gameBoard[0][c-1]);
            surrounding.add(gameBoard[0][c+1]);
            //then those in the row above
            surrounding.add(gameBoard[1][c-1]);
            surrounding.add(gameBoard[1][c+1]);
            surrounding.add(gameBoard[1][c]);
        } else if (topRow&&firstColumn){
            surrounding.add(gameBoard[rowL-2][0]);
            surrounding.add(gameBoard[rowL-2][1]);
            surrounding.add(gameBoard[rowL-1][1]);
        } else if (topRow&&lastColumn){
            surrounding.add(gameBoard[rowL-2][colL-2]);
            surrounding.add(gameBoard[rowL-1][colL-2]);
            surrounding.add(gameBoard[rowL-2][colL-1]);
        } else if(topRow){
            //first those in the same row
            surrounding.add(gameBoard[r][c-1]);
            surrounding.add(gameBoard[r][c+1]);
            //then those in the row below
            surrounding.add(gameBoard[r-1][c-1]);
            surrounding.add(gameBoard[r-1][c+1]);
            surrounding.add(gameBoard[r-1][c]);
        } else if (firstColumn){
            //first those in the same column @todo
            surrounding.add(gameBoard[r-1][c]);
            surrounding.add(gameBoard[r+1][c]);
            //then those in the column after
            surrounding.add(gameBoard[r-1][c+1]);
            surrounding.add(gameBoard[r+1][c+1]);
            surrounding.add(gameBoard[r][c+1]);
        } else if (lastColumn){
            //first those in the same column @todo
            surrounding.add(gameBoard[r+1][c]);
            surrounding.add(gameBoard[r-1][c]);
            //then those in the column before
            surrounding.add(gameBoard[r-1][c-1]);
            surrounding.add(gameBoard[r+1][c-1]);
            surrounding.add(gameBoard[r][c-1]);

        }
        //@todo test
    }

    public void printBoard(){
        System.out.print("    |");
        for (int i = 0; i < gameBoard[0].length; i++){
            System.out.print("    ");
            System.out.print(i);
            System.out.print("    |");
        }
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++){
            System.out.print("----+");
            for (int j = 0; j < gameBoard[0].length; j++){
                System.out.print("---------+");
            }
            System.out.println();
            System.out.print("  " + i + " |");
            for (int j = 0; j < gameBoard[0].length; j++){
                int spaces = 8;
                for (int k = 0; k < spaces; k++){
                    System.out.print(" ");
                }
                System.out.print(gameBoard[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }


}

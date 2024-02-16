import java.util.ArrayList;

public class Game {

    private boolean gameOver = false;

    private boolean lost = false;
    private boolean won = false;
    private Tile[][] testBoard = new Tile[5][5];

    private Tile[][] easyBoard = new Tile[9][9];
    private Tile[][] mediumBoard = new Tile[16][16];
    private Tile[][] hardBoard = new Tile[30][16];

    private ArrayList<Tile> revealed = new ArrayList<Tile>();

    private int rowSize, colSize;

    private int mines;


    private Tile[][] gameBoard;

    //d1 = easy, d2=medium, d3=jard
    public Game(int difficulty) {
        if(difficulty==-1){
            gameBoard = testBoard;
            mines=2;
        }else if(difficulty==1) {
            gameBoard = easyBoard;
            mines=10;
        } else if (difficulty==2) {
            gameBoard = mediumBoard;
            mines=40;
        } else {
            gameBoard = hardBoard;
            mines=99;
        }
        colSize=gameBoard[0].length;
        rowSize=gameBoard.length;
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
                gameBoard[i][j] = new Tile(mine, i, j);
                //gameBoard[i][j].setShow(true);
            }
        }
        ArrayList<Tile> neighbours = new ArrayList<Tile>();
        getNeighbours(r, c, neighbours);
        for(Tile neighbour: neighbours){
            neighbour.setMine(false);
        }
        iniitialiseCounts();
        updateBoardClick(r, c);
    }


    //@todo implement, return true if successful, false otherwise
    public boolean placeFlag(int r, int c, boolean place){
        if(gameBoard[r][c].isShow()){
            return false;
        }else{
            gameBoard[r][c].setFlag(place);
            return true;
        }
    }

    //reveal new numbers/empty spaces
    public int updateBoardClick(int r, int c){
        //first check if it's a bomb
        if(gameBoard[r][c].isMine()){
            if(gameBoard[r][c].isFlag()){
                return -1;
            }
            lost = true;
            gameOver();
            return -1;
        }else{
            gameBoard[r][c].setShow(true);
            ArrayList<Tile> neighbours = new ArrayList<Tile>();
            getNeighbours(r, c, neighbours);
            for(Tile neighbour:neighbours){
                int temp = expand(neighbour);
            }
        }
        //check if they won
        won = checkWin();
        if(won) gameOver=true;
        return 0;
    }

    //expand on the tile
    public int expand(Tile tile){
        if(tile.isShow()){
            return 1;
        }else if(tile.isMine()){
            return 1;
        }else if(tile.isFlag()) {
            return 1;
        } else if (tile.count>0){
            tile.setShow(true);
            return 1;
        }else{
            //Expand
            tile.setShow(true);
            ArrayList<Tile> neighbours = new ArrayList<Tile>();
            getNeighbours(tile.getRow(), tile.getCol(), neighbours);
            for(Tile neighbour : neighbours){
                expand(neighbour);
            }
        }
        return -1;
    }

    //Game over! set all squares to x or something - specifically for a loss
    public void gameOver(){
        gameOver = true;
        for (int i=0; i< rowSize; i++){
            for (int j=0; j< colSize; j++){
                gameBoard[i][j].setMine(true);
                System.out.print("\033[1;31m");
                gameBoard[i][j].setShow(true);
            }
        }
    }

    //method to check if you've w
    //win condition: all safe blocks opened
    public boolean checkWin(){
        Tile temp;
        for (int i=0; i< rowSize; i++){
            for (int j=0; j< colSize; j++){
                temp = gameBoard[i][j];
                if(!temp.isShow()&&!temp.isMine()){
                    return false;
                }
            }
        }
        return true;
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
                    surrounding.clear();
                }
            }
        }
    }

    private void getNeighbours(int r, int c, ArrayList<Tile> surrounding){
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
            //first those in the same column
            surrounding.add(gameBoard[r-1][c]);
            surrounding.add(gameBoard[r+1][c]);
            //then those in the column after
            surrounding.add(gameBoard[r-1][c+1]);
            surrounding.add(gameBoard[r+1][c+1]);
            surrounding.add(gameBoard[r][c+1]);
        } else if (lastColumn){
            //first those in the same column
            surrounding.add(gameBoard[r+1][c]);
            surrounding.add(gameBoard[r-1][c]);
            //then those in the column before
            surrounding.add(gameBoard[r-1][c-1]);
            surrounding.add(gameBoard[r+1][c-1]);
            surrounding.add(gameBoard[r][c-1]);
        } else{
            //add all - should be 8
            surrounding.add(gameBoard[r-1][c-1]);
            surrounding.add(gameBoard[r-1][c]);
            surrounding.add(gameBoard[r-1][c+1]);

            surrounding.add(gameBoard[r+1][c-1]);
            surrounding.add(gameBoard[r+1][c]);
            surrounding.add(gameBoard[r+1][c+1]);

            surrounding.add(gameBoard[r][c-1]);
            surrounding.add(gameBoard[r][c+1]);
        }
    }

    public void printBoard(){
        System.out.print("    |");
        for (int i = 0; i < gameBoard[0].length; i++){
            if(i<10){
                System.out.print("    ");
                System.out.print(i);
                System.out.print("    |");
            }else{
                System.out.print("    ");
                System.out.print(i);
                System.out.print("   |");
            }
        }
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++){
            System.out.print("----+");
            for (int j = 0; j < gameBoard[0].length; j++){
                System.out.print("---------+");
            }
            System.out.println();
            if(i<10){
                System.out.print("  " + i + " |");
            }else{
                System.out.print(" " + i + " |");
            }
            //System.out.print("  " + i + " |");
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


    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isLost() {
        return lost;
    }
}

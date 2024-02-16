import java.util.Locale;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        play();
    }

    public static void play(){
        System.out.println("Welcome to MINESWEEPER");
        System.out.println("Please be warned, this game is not practice for real-life " +
                "minesweeping.\nMINESWEEPER is not liable for any real-life " +
                "minesweeping you choose to do.\n");

        Scanner reader = new Scanner(System.in);
        System.out.println("Please select your difficulty\nEnter 1 for easy (9x9), 2 for intermediate (16x16), " +
                "or 3 for Ultimate Minesweeper (30x16)");
        String input = reader.next();
        int diff = Integer.parseInt(input);
        //@todo add input validation

        Game minesweeper =  new Game(diff);
        String difficulty = "";
        switch (diff){
            case 1 -> {
                difficulty = "easy";
            }
            case 2 -> {
                difficulty = "intermediate";
            }
            case 3 -> {
                difficulty = "ULTIMATE MINESWEEPER";
            }
            case -1 ->{
                difficulty = "testing mode - for devs only";
            }
        }
        System.out.println("You have chosen " + difficulty + ", good luck!\n" +
                "The coordinates start at 0,0\n");
        System.out.println("Which coordinates would you like to begin with?");
        int[] coordinates = new int[2];
        coordinates = coordinates(minesweeper);

        minesweeper.initialiseBoard(coordinates[0],coordinates[1]);
        minesweeper.printBoard();


        //bulk of game here
        while(!minesweeper.isGameOver()){ //@todo
            //Ask if they want to place a flag or click on a new tile
            get_move(minesweeper);
            minesweeper.printBoard();
        }
        //Upon exit, game is over

        //They lost
        if(minesweeper.isLost()){
            //minesweeper.printBoard();
            System.out.print("\033[1;31m");
            System.out.println("GAME OVER");
        }else{ //or they won
            System.out.println("CONGRATULATIONS, you have cleared the minefield!");
        }
    }



    public static void get_move(Game minesweeper){
        boolean readMove = false;
        boolean placeFlag = false;
        boolean place = false;
        boolean cleanTile = false;
        //Ask if they want to click or place a flag
        //Then either way use the coordinates method to get the coordinates
        while(!readMove){
            Scanner reader = new Scanner(System.in);
            System.out.println("Would you like to place a flag or clean a tile?\n" +
                    "Enter f or flag to place a flag\n" +
                    "Enter c or clean to clean a tile\n" +
                    "Enter r or remove to remove a flag");
            String input = reader.next();
            input = input.toLowerCase().trim();
            if(input.equals("flag")||input.equals("f")){
                //place flag
                placeFlag =true;
                readMove = true;
                place = true;
            } else if (input.equals("clean")||input.equals("c")) {
                //clean tile
                cleanTile =true;
                readMove = true;
            } else if (input.equals("remove")||input.equals("r")) {
                placeFlag=true;
                place=false;
                readMove = true;
            }else{
                System.out.println("Invalid input, please try again");
            }
        }

        int[] coordinates = new int[2];
        coordinates = coordinates(minesweeper);

        if(placeFlag){
            minesweeper.placeFlag(coordinates[0], coordinates[1], place);
        } else if (cleanTile) {
            minesweeper.updateBoardClick(coordinates[0], coordinates[1]);
        }

    }

    //returns int[] with row at [0], and column at [1]
    public static int[] coordinates(Game board){
        int[] coordinates = new int[2];

        //coordinate Row/Column
        int cR =-1; //row
        int cC = -1; //column

        int colSize = board.getColSize();
        int rowSize = board.getRowSize();

        boolean input1 = false;
        boolean input2 = false;
        boolean valid = true;


        Scanner reader = new Scanner(System.in);


        while(!input1){
            System.out.println("What is the row coordinate?");
            String input = reader.next();

            try {
                cR = Integer.parseInt(input);
                input1 = true;
            } catch (NumberFormatException ignored) {
            }

            if(!input1 || cR<0 || cR>=rowSize){
                System.out.println("Invalid input, please enter a valid coordinate\n");
                input1 = false;
            }
        }
        while(!input2){
            System.out.println("What is the column coordinate?");
            String input = reader.next();

            try {
                cC = Integer.parseInt(input);
                input2 = true;
            } catch (NumberFormatException ignored) {
            }

            if(!input2 || cC<0 || cC>=colSize){
                System.out.println("Invalid input, please enter a valid coordinate\n");
                input2 = false;
            }
        }
        coordinates[0] = cR;
        coordinates[1] = cC;
        return coordinates;
    }
}
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        play();
    }

    public static void play(){
        System.out.println("Welcome to Minesweeper");
        System.out.println("Please be warned, this game is not practice for real-life " +
                "minesweeping.\nMinesweeper is not liable for any real-life " +
                "minesweeping you choose to do.\n");

        Scanner reader = new Scanner(System.in);
        System.out.println("Please select your difficulty\nEnter 1 for easy, 2 for intermediate, " +
                "or 3 for Ultimate Minesweeper");
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
        }
    }

    public static void get_move(Game minesweeper){
        //Ask if they want to click or place a flag
        //Then either way use the coordinates method to get the coordinates

    }
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

            if(!input1 || cR<=0 || cR>=rowSize){
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

            if(!input2 || cC<=0 || cC>=colSize){
                System.out.println("Invalid input, please enter a valid coordinate\n");
                input2 = false;
            }
        }
        coordinates[0] = cR;
        coordinates[1] = cC;
        return coordinates;
    }
}
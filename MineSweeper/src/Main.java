//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Minesweeper");
        System.out.println("Please be warned, this game is not practice for real-life minesweeping. Minesweeper is not liable for any real-life minesweeping you choose to do.");
        Game minesweeper =  new Game(1);
        minesweeper.initialiseBoard(5,5);
        minesweeper.printBoard();

    }
}
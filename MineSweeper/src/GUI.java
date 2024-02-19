import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

public class GUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Minesweeper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.setSize(600,600);
        f.setVisible(true);

    }

}

class MyPanel extends JPanel {

    int numRow = 16;
    int numCol = 16;
    Square[][] tiles = new Square[16][16];

    public Game minesweeper;

    int clickCount = 0;

    //Square tile = new Square();

    public MyPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));

        for(int i=0; i<numRow; i++){
            for(int j=0; j<numCol; j++){
                tiles[i][j] = new Square(600/numCol*j, 600/numRow*i);
            }
        }


        //hard coded for now
        int diff=1;
        minesweeper = new Game(diff);
        minesweeper.initialiseBoard(5, 5);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                int w = tiles[0][0].getWidth();
                int h = tiles[0][0].getHeight();
                //find which tile pressed
                for(int i=0; i<numRow; i++){
                    for(int j=0; j<numCol; j++){
                        int curX = tiles[i][j].getX();
                        int curY = tiles[i][j].getY();
                        if ((curX<600/numCol*j+w && curX>600/numCol*j)&&
                                (curY<600/numRow*i+w && curX>600/numRow*i)){
                            updateTileClick(i, j);
                            break;
                        }
                        //tiles[i][j] = new Square(600/numCol*j, 600/numRow*i);
                    }
                }
            }
        });

//        addMouseMotionListener(new MouseAdapter(){
//            public void mouseDragged(MouseEvent e){
//                moveSquare(e.getX(),e.getY());
//            }
//        });

    }

    private void updateTileClick(int row, int col){
        //click on board
        minesweeper.updateBoardClick(row, col);
        repaint();
    }

//    private void moveSquare(int x, int y){
//
//        // Current square state, stored as final variables
//        // to avoid repeat invocations of the same methods.
//        final int CURR_X = tile.getX();
//        final int CURR_Y = tile.getY();
//        final int CURR_W = tile.getWidth();
//        final int CURR_H = tile.getHeight();
//        final int OFFSET = 1;
//
//        if ((CURR_X!=x) || (CURR_Y!=y)) {
//
//            // The square is moving, repaint background
//            // over the old square location.
//            repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);
//
//            // Update coordinates.
//            tile.setX(x);
//            tile.setY(y);
//
//            // Repaint the square at the new location.
//            repaint(tile.getX(), tile.getY(),
//                    tile.getWidth()+OFFSET,
//                    tile.getHeight()+OFFSET);
//        }
//    }

    public Dimension getPreferredSize() {
        return new Dimension(600,600);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0; i<numRow; i++){
            for(int j=0; j<numCol; j++){
                tiles[i][j].paintSquare(g);
            }
        }

    }
}

class Square{

    private int numRow = 16;
    private int numCol = 16;

    private int xPos = 50;
    private int yPos = 50;
    private int width = 600/numCol;
    private int height = 600/numRow;

    public Square(int x, int y) {
        xPos=x;
        yPos=y;
    }


    public void setX(int xPos){
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void paintSquare(Graphics g){
        g.setColor(Color.GRAY);
        //xPos and yPos are the top left point of the rect
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);
    }
}
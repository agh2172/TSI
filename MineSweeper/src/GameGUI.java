import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {
    private JFrame frame;

    private Game minesweeper;

    private int diff = 0;

    private int frameWidth = 600;
    private int frameHeight = 600;

    public GameGUI(){
        frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth,frameHeight);

        frame.setVisible(true);
        //Need size of gameBoard to format buttons correctly
    }

    //get game difficulty
    public void start(){
        final String[] difficulty = new String[1];
        JLabel l1,l2;
        String s1[] = { "Easy", "Medium", "Hard"};
        //l2.setBounds(100,200, 200,30);
        //frame.add(l1); frame.add(l2);
        JButton b=new JButton("Select");
        b.setBounds(200,100,75,20);
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);

        // create checkbox
        JComboBox c1 = new JComboBox(s1);
        c1.setBounds(50, 100,90,20);
        frame.setLayout(null);

        l1 = new JLabel("select difficulty ");
        //l2 = new JLabel("Easy selected");

        frame.add(c1);
        frame.add(b);
        frame.add(label);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Game Difficulty Selected: "
                        + c1.getItemAt(c1.getSelectedIndex());
                label.setText(data);
                difficulty[0] = " " + c1.getItemAt(c1.getSelectedIndex());

                System.out.println(difficulty[0]);
                switch (difficulty[0].trim()){
                    case "Easy" -> {
                        diff = 1;
                        initGame();
                    }
                    case "Medium" -> {
                        diff = 2;
                        initGame();
                    }
                    case "Hard" -> {
                        diff = 3;
                        initGame();
                    }
                    default -> {
                        diff = 1;
                        initGame();
                    }
                }
            }
        });

    }

    //draw initial board
    public void initGame(){
        System.out.println("In initGame");
        drawBoardFirst();
    }

    public void drawBoardFirst(){
        //easy 9x9
        //medium 16x16
        //hard 30x16

        //frame.dispose();
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setSize(frameWidth,frameHeight);
        frame.add(new MyPanel());
        frame.pack();
        frame.setVisible(true);
//        System.out.println("In drawBoardFirst");
//
//        int rowNum = 0, colNum = 0, squareSize;
//
//        if(diff==1){
//            rowNum = 9;
//            colNum = 9;
//            squareSize = frameHeight/rowNum;
//
//        } else if (diff==2) {
//            rowNum = 16;
//            colNum = 16;
//            squareSize = frameHeight/rowNum;
//
//        } else if (diff==3) {
//            //@todo, set new framesize?
//            rowNum = 30;
//            colNum = 16;
//            squareSize = frameHeight/rowNum;
//        }
//        for(int i=0; i<rowNum; i++){
//            for(int j=0; j<colNum; j++){
//
//            }
//        }

    }


}


import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    //constructer
    SnakeGame(){
        //create objecct of the board class
        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args) {
    //initialize the snakegame create object
        SnakeGame snakeGame = new SnakeGame();
    }
}
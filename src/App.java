// import javax.swing.JFrame;
import javax.swing.*;

// this would control the main window of the game, and would be responsible for creating the game board
public class App {
    public static void main(String[] args) throws Exception {
        // window variables
        int tileSize = 32;
        int rows=16; 
        int columns = 16;
        int boardWidth = tileSize * columns;
        int boardLength = tileSize * rows;

        JFrame frame = new JFrame("Fish Invaders");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(boardWidth, boardLength);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // System.out.println("Hello, World!");
        
        FishInvaders game = new FishInvaders();
        frame.add(game);
        frame.pack();
        game.requestFocus();
        frame.setVisible(true);

    }
}

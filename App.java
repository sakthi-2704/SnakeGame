import javax.swing.JFrame;
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 600;
        int boardheight = boardwidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakGame = new SnakeGame(boardwidth, boardheight);
        frame.add(snakGame);
        frame.pack();
        snakGame.requestFocus();
    }
}

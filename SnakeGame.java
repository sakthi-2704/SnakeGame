import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int boardheight;
    int boardwidth;
    int tileSize=25;


    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardwidth, int boardheight){
        this.boardheight=boardheight;
        this.boardwidth=boardwidth;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX=0;
        velocityY=0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Grid
        /*for(int i=0;i<boardwidth/tileSize;i++){
            g.drawLine(i*tileSize, 0, i*tileSize, boardheight);
            g.drawLine(0, i*tileSize, boardwidth, i*tileSize);
        }*/
        
        //Food
        g.setColor(Color.red);
      //g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);  
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        //Snake HEad
        g.setColor(Color.green);
      //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);

        //Snake Body
        for (int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
          //g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);  
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize,true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN,16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        else{
            g.drawString("Score"+String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }
       }

       public void placeFood(){
        food.x = random.nextInt(boardwidth/tileSize);
        food.y = random.nextInt(boardheight/tileSize); 
       }

       public boolean collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
       }

       public void move(){
        //snake eats food
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y= snakeHead.y;
            }
            else{
                Tile prevsnakePart=snakeBody.get(i-1);
                snakePart.x = prevsnakePart.x;
                snakePart.y = prevsnakePart.y;
            }
        }
        //SnakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over
        for (int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            //collision of snake with self
            if (collision(snakeHead, snakePart)){
                gameOver=true;
            }
        }

        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardwidth || 
           snakeHead.y*tileSize<0 || snakeHead.y*tileSize>boardheight){
            gameOver=true;
        }
       }

       @Override
       public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
       }

       @Override
       public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
       }

       //Not Required
       @Override
       public void keyTyped(KeyEvent e) {
       }
       @Override
       public void keyReleased(KeyEvent e) {
       }
}

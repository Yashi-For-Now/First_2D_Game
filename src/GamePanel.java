import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    //Screen Setting

    final int originalTileSize =  16; //16x16 tile
    final int scale= 3;

    final int tileSize= originalTileSize*scale; //48x48 tile

    final int maxScreenCol= 16;
    final int maxScreenRow= 12;
    final int screenWidth= tileSize*maxScreenCol; //768 pixels
    final int screenHeight= tileSize*maxScreenRow; //576 pixels

    //FPS
    int FPS=60;

    KeyHandler keyH= new KeyHandler();
    Thread gameThread;

    //constructor of the GamePanel
    //Set players default position
    int playerX=100;
    int playerY=100;
    int playerSpeed=4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread= new Thread(this);
        gameThread.start();
    }

    @Override
    // public void run() {
        //sleep method for game loop
    //     double drawInterval = 1000000000/FPS; //0.0166666 second
    //     double nextDrawTime= System.nanoTime() + drawInterval;

    //     while (gameThread!= null) {
    //         // long currentTime= System.nanoTime();
            
    //         // System.out.println("The game loop is running.");
    //         //Essentially this place is for keeping the game running always. 
    //         //Things to do here.
    //         //1. Update: update information such as character position
    //         update();
    //         //2. Draw: draw the screen with the updates information.
    //         repaint(); //this is how we call paintComponent method.
            
    //         try {
    //             double remainingTime= nextDrawTime - System.nanoTime();
    //             remainingTime = remainingTime/1000000;
                
    //             if(remainingTime<0){
    //                 remainingTime= 0;
    //             }

    //             Thread.sleep((long) remainingTime);

    //             nextDrawTime += drawInterval;

    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
    public void run () {
        //delta/accumulate method for game loop
        double drawInterval= 1000000000/FPS;
        double delta=0;
        long lastTime= System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;
        while (gameThread!= null){
            currentTime= System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime-lastTime);
            lastTime= currentTime;

            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount=0;
                timer=0;
            }
        }
    }
    //method 
    public void update() {
        if (keyH.upPressed==true){
            playerY-= playerSpeed;
        } else if(keyH.downPressed==true){
            playerY +=playerSpeed;
        } else if(keyH.leftPressed==true){
            playerX -= playerSpeed;
        } else if(keyH.rightPressed==true){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {
        //paint component is the standard method to draw on JPanel
        super.paintComponent(g);

        Graphics2D g2= (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}

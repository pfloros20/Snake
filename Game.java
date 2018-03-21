import javax.swing.JFrame;
import java.awt.Font;
import java.applet.*;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JOptionPane;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.awt.Toolkit;
public class Game extends JComponent implements KeyListener, Runnable{
	public static final int WIDTH=800;
	public static final int HEIGHT=600;
	private static String name=null;
	private static boolean registered=false;
	public static int score=0;
	private boolean paused=false;
	private boolean gameOver=false;
	public static JFrame window;
	public static Game game;
	private Thread thread=null;
	private Snake snake;
	private Goal goal;
	private long lastTime;
	private BufferedImage image;
	private AudioClip bg=Applet.newAudioClip(getClass().getResource("Assets/bg.wav"));
	public static void main(String args[]){
		game =new Game();
		name=JOptionPane.showInputDialog(window,"Type username for the leaderboard or nothing to play offline.");
		if(!name.equals("")){
			registered=true;
			Client client=new Client("localhost",93604587+" "+name+" "+score);
			boolean establishedConnection=client.startRunning();
			if(!establishedConnection)
				JOptionPane.showMessageDialog(window,"Please visit the site and get latest version."
						+"\nIf this is the latest version the servers are down,please try again later."
						+"\nScores won't be on the leaderboard.");
		}
		window=new JFrame("Snake Game.");
		game.createAssets();
		initComponents();
		
	}
	public void run(){
		while(!gameOver){
			long timeNow = System.currentTimeMillis();
			long dif=timeNow-lastTime;
			
			if(!paused){
		 		window.getContentPane().setForeground(Color.WHITE);
		 		if(dif>100){
		 			snake.move();
			 		gameOver=snake.collision();
			 		if(snake.collide(goal)){
			 			goal.respawn();
			 			score+=50;
			 		}
			 		repaint();
			 		lastTime=System.currentTimeMillis();
			 	}
		 		
			}else
		 		window.getContentPane().setForeground(Color.GRAY);
		}
		if(registered){
			Client client=new Client("localhost",93604587+" "+name+" "+score);
			boolean establishedConnection=client.startRunning();
			if(!establishedConnection)
				JOptionPane.showMessageDialog(window,"Please visit the site and get latest version."
						+"\nIf this is the latest version the servers are down,please try again later."
						+"\nScores won't be on the leaderboard.");
		}
	}
	public void createAssets(){
		bg.loop();
		try{
			image = ImageIO.read(getClass().getResource("Assets/riplol.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
		snake=new Snake();
		goal=new Goal();
		thread=new Thread(game);
		thread.start();
	}
	public void destroyAssets(){
		snake=null;
		goal=null;
	}

	public static void initComponents(){
		window.setUndecorated(true);
		window.setSize(WIDTH,HEIGHT);
		window.setResizable(false);
		Color c=new Color(54,111,171);
		window.getContentPane().setBackground(c);
		window.getContentPane().setForeground(Color.WHITE);
		window.add(game);
		window.addKeyListener(game);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Toolkit.getDefaultToolkit().sync();	
		g.setFont(new Font("TimesRoman",Font.PLAIN,20));
		g.drawString("Score: "+score,5,20);
		Color c,r;
		if(!paused){
			c=new Color(37,255,76);
			r=new Color(255,34,76);
		}
		else{
			c=Color.GRAY;
			r=c;
		}
		snake.draw(g,c);
		goal.draw(g,r);
		if(gameOver)
			g.drawImage(image, WIDTH/2-130, HEIGHT/2-97, null);
		
	}
	public void keyReleased(KeyEvent e){
    }
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		if(!paused&&!gameOver){
			if (key == KeyEvent.VK_UP){
				snake.redirect("UP");
	        }
	        
	        else if (key == KeyEvent.VK_LEFT){
	        	snake.redirect("LEFT");
	        }
	        
	        else if (key == KeyEvent.VK_RIGHT){
	        	snake.redirect("RIGHT");
	        }
	        if (key == KeyEvent.VK_DOWN){
				snake.redirect("DOWN");
	        }
	    }
	    if(!gameOver)
	        if (key ==KeyEvent.VK_P){
	        	if(!paused)
	        		paused=true;
	        	else
	        		paused=false;
	        }
        if (key ==KeyEvent.VK_ENTER){
        	
	        if(gameOver){
	        	gameOver=false;
	        	score=0;
	        	game.destroyAssets();
	        	game.createAssets();
	        }
        }
        if (key ==KeyEvent.VK_ESCAPE)
        	System.exit(0);
	}
	public void keyTyped(KeyEvent e){
	}

}
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;
public class Goal{
	private int x;
	private int y;
	private static final int dimensions=20;
	private static final int padding=2;

	public Goal(){
		Random rn=new Random();
		x=rn.nextInt(Game.WIDTH/dimensions)*dimensions;
		y=rn.nextInt(Game.HEIGHT/dimensions)*dimensions;
	}
	public void respawn(){
		Random rn=new Random();
		x=rn.nextInt(Game.WIDTH/dimensions)*dimensions;
		y=rn.nextInt(Game.HEIGHT/dimensions)*dimensions;
	}
	public void draw(Graphics g,Color c){
		Graphics2D g2=(Graphics2D) g;
		g.setColor(c);
		g2.fillRect(x+padding,y+padding,dimensions-padding,dimensions-padding);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
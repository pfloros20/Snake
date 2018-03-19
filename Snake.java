import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
public class Snake{
	private Segment head;
	private ArrayList<Segment> tail;
	private int vx;
	private int vy;
	private String lastDirection;

	public Snake(){
		head=new Segment(Game.WIDTH/2,Game.HEIGHT/2);
		tail=new ArrayList<Segment>();
		tail.add(head);
		vx=20;
		vy=0;
		lastDirection="RIGHT";
	}
	public void redirect(String direction){
		switch(direction){
			case "UP":
				if(!lastDirection.equals("DOWN")){
					vy=-Segment.dimensions;
					vx=0;
					lastDirection="UP";
				}
				break;
			case "DOWN":
				if(!lastDirection.equals("UP")){
					vy=+Segment.dimensions;
					vx=0;
					lastDirection="DOWN";
				}
				break;
			case "RIGHT":
				if(!lastDirection.equals("LEFT")){
					vx=+Segment.dimensions;
					vy=0;
					lastDirection="RIGHT";
				}
				break;
			case "LEFT":
				if(!lastDirection.equals("RIGHT")){
					vx=-Segment.dimensions;
					vy=0;
					lastDirection="LEFT";
				}
				break;
		}
	}
	public void move(){
		for(int i=tail.size()-1;i>0;i--)
			tail.get(i).move(tail.get(i-1).x,tail.get(i-1).y);
		head.move(head.x+vx,head.y+vy);
	}
	public boolean collision(){
		if(head.x<0||head.x>=Game.WIDTH)
			return true;
		if(head.y<0||head.y>=Game.HEIGHT)
			return true;
		for(int i=1;i<tail.size();i++)
			if(head.x+vx==tail.get(i).x&&head.y+vy==tail.get(i).y)
				return true;
		return false;
	}
	public boolean collide(Goal goal){
		if(head.x==goal.getX()&&head.y==goal.getY()){
			increaseSize();
			return true;
		}
		return false;
	}
	private void increaseSize(){
		tail.add(new Segment(-20,-20));
	}
	public void draw(Graphics g,Color c){
		for(int i=0;i<tail.size();i++)
			tail.get(i).draw(g,c);
		g.setColor(Color.WHITE);
	}
	class Segment{
			int x;
			int y;
			static final int dimensions=20;
			static final int padding=2;

			public Segment(){}
			public Segment(int x,int y){
				this.x=x;
				this.y=y;
			}
			public void move(int x,int y){
				this.x=x;
				this.y=y;
			}
			public void draw(Graphics g,Color c){
				Graphics2D g2=(Graphics2D) g;
				g.setColor(c);
				g2.fillRect(x+padding,y+padding,dimensions-padding,dimensions-padding);
			}
	}
}
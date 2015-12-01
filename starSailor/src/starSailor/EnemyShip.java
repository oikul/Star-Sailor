package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class EnemyShip{

	private ArrayList<Point2D.Double> trajectories;
	private ArrayList<Line2D.Double> shots;
	private int health;
	private Point2D.Double location;
	private double shootTime;
	
	public enum shipClass{
		FIGHTER,CARRIER,COMMAND
	}
	public enum fightingStyle{
		AGGRESSIVE,DEFENSIVE,TACTICAL
	}
	
	public EnemyShip(int x,int y,shipClass ship){
		
		location = new Point2D.Double(x,y);
		if(ship == shipClass.FIGHTER){
			this.health = 100;
		}else if(ship == shipClass.CARRIER){
			this.health = 500;
		}else{
			this.health = 2000;
		}

		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		shootTime = System.currentTimeMillis()+4000;
	}
	
	private boolean isAlive(){
		
		if(health <= 0){
			return true;
		}
		return false;
		
	}

	public void takeDamage(int damage){
		this.health -= damage;
		
	}
	
	public void shoot(){
		
		shots.add(new Line2D.Double(location.x, location.y, location.x, location.y));
		double speed = 20.0;
		
		trajectories.add(Main.getPoint(new Point2D.Double(location.x,location.y),new Point2D.Double(Main.width/2, Main.height/2),speed));
		
		shootTime = System.currentTimeMillis() + ((Main.random.nextDouble() * 1000) * 2);
	}
	
	public void update(){
		
		if(System.currentTimeMillis() >= shootTime){
			shoot();
		}
		
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
		}
		Rectangle rect = new Rectangle(0,0,Main.width,Main.height);
		for (int i = 0; i < shots.size(); i++) {
			if(!rect.contains(new Point2D.Double(shots.get(i).getX1(),shots.get(i).getY1()))){
				
				shots.remove(i);
				trajectories.remove(i);
				
			}
		}
		
	}
	
	public void draw(Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.red);
		int size = 10;//will be replaced with an image
		g2.drawRect((int)location.x-size, (int)location.y-size, 2*size, 2*size);
		for (int i = 0; i < shots.size(); i++) {
			g2.draw(shots.get(i));
		}
	}
	
	
}
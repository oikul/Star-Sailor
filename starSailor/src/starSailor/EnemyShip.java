package starSailor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EnemyShip{

	protected ArrayList<Point2D.Double> trajectories;
	protected ArrayList<Line2D.Double> shots;
	private int health;
	protected int currentFrame;
	protected int death;
	protected Point2D.Double spaceLocation;
	protected Point2D.Double actualLocation;
	private double rotation;
	protected double time;
	protected double shootTime;
	private Image[] shipImages;
	private int xSize,ySize;
	
	public enum shipClass{
		FIGHTER,CARRIER,COMMAND
	}
	
	public EnemyShip(int x,int y,shipClass ship){
		
		time = System.currentTimeMillis();
		spaceLocation = new Point2D.Double(x,y);
		if(ship == shipClass.FIGHTER){
			this.health = 100;
			shipImages = ResourceLoader.getBlockSprites("spaceship/smallFighterSprite.png", 16, 16);
			actualLocation = new Point2D.Double(x,y);
			xSize = 16;
			ySize = 16;
		}else if(ship == shipClass.CARRIER){
			this.health = 500;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			actualLocation = new Point2D.Double(x,y);
			xSize = 32;
			ySize = 32;
		}else if(ship == shipClass.COMMAND){
			this.health = 2000;
			shipImages = ResourceLoader.getBlockSprites("spaceship/CommandShipSprite.png", 128, 32);
			actualLocation = new Point2D.Double(x,y);
			xSize = 128;
			ySize = 32;
		}else{
			health = 100;
		}

		getAngle();
		
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		shootTime = System.currentTimeMillis()+3000 + Main.random.nextInt(500);
	}
	
	public void resetTime(){
		shootTime = System.currentTimeMillis()+3000 + Main.random.nextInt(500);
	}
	
	public void die(){
		if(System.currentTimeMillis() >= time + 300){
			currentFrame++;
			death--;
		}
	}
	
	public boolean deadYet(){
		return death == 0;
	}
	
	public boolean isAlive(){
		if(health > 0){
			return true;
		}
		return false;
		
	}
	
	public void getAngle(){
		rotation = SpaceBattle.getAngle(actualLocation, 
										new Point2D.Double(Main.width/2,Main.height/2));
	}

	public Point2D.Double getLocation(){
		return actualLocation;
	}
	
	public void takeDamage(int damage){
		this.health -= damage;
		
	}
	
	public void shoot(){
		
		shots.add(new Line2D.Double(actualLocation.x, actualLocation.y, actualLocation.x, actualLocation.y));
		double speed = 20.0;
		
		trajectories.add(SpaceBattle.getPoint(new Point2D.Double(actualLocation.x,actualLocation.y),new Point2D.Double(Main.width/2, Main.height/2),speed,0));
		
		shootTime += 300 + Main.random.nextDouble() * 1000;
		Sound.laser.play();
	}
	
	public void update(double xChange,double yChange){
		
		getAngle();
		spaceLocation.setLocation(actualLocation.x-xChange, actualLocation.y-yChange);
		if(System.currentTimeMillis() >= time){
			
			if(currentFrame == 1){
				currentFrame = 0;
			}else{
				currentFrame = 1;
			}
			time += Main.random.nextInt(500);
		}
		
		if(System.currentTimeMillis() >= shootTime){
			shoot();
		}
		
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
			if(shots.get(i).intersects(new Rectangle(Main.width/2 - 16, Main.height/2 - 16, 32, 32))){
				SpaceBattle.playerDamage(5);
				shots.remove(i);
				trajectories.remove(i);
				i--;
			}
		}
		Rectangle rect = new Rectangle(0,0,Main.width,Main.height);
		for (int i = 0; i < shots.size(); i++) {
			if(!rect.contains(new Point2D.Double(shots.get(i).getX1(),shots.get(i).getY1()))){
				
				shots.remove(i);
				trajectories.remove(i);
				
			}
		}
		
	}
	
	public void draw(Graphics2D g2d){
		

		g2d.setColor(Color.red);

		AffineTransform saveAt;
		AffineTransform at;
		at = new AffineTransform();
		saveAt = g2d.getTransform();
		at.rotate(rotation, actualLocation.x, actualLocation.y);
		g2d.setTransform(at);
		g2d.drawRect((int)actualLocation.x-(xSize/2), (int)actualLocation.y-(ySize/2),xSize,ySize);
		g2d.drawImage(shipImages[currentFrame],(int)actualLocation.x-(xSize/2),(int)actualLocation.y-(ySize/2), null);
		g2d.setTransform(saveAt);
		
		
		for (int i = 0; i < shots.size(); i++) {
			g2d.draw(shots.get(i));
		}
	}
	
}
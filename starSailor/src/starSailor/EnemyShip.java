package starSailor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EnemyShip{

	protected ArrayList<Point2D.Double> trajectories;
	protected ArrayList<Line2D.Double> shots;
	private int health,speed;
	protected int currentFrame;
	protected int death;
	protected Rectangle location;
	protected Point2D.Double spaceLocation;
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
		spaceLocation = new Point2D.Double(0,0);
		if(ship == shipClass.FIGHTER){
			this.health = 100;
			shipImages = ResourceLoader.getBlockSprites("spaceship/smallFighterSprite.png", 16, 16);
			location = new Rectangle(x+8,y+8,16,16);
			xSize = 16;
			ySize = 16;
		}else if(ship == shipClass.CARRIER){
			this.health = 500;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			location = new Rectangle(x+16,y+16,32,32);
			xSize = 32;
			ySize = 32;
		}else if(ship == shipClass.COMMAND){
			this.health = 2000;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			location = new Rectangle(x+16,y+64,32,128);
			xSize = 32;
			ySize = 128;
		}else{
			health = 100;
		}

		getAngle();
		
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
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
		shipImages = ResourceLoader.getBlockSprites("shipExplosions/carrierExSprite.png", 32, 32);
		currentFrame = 0;
		return false;
		
	}
	
	public void getAngle(){
		rotation = SpaceBattle.getAngle(new Point2D.Double(location.getX()+(location.getWidth()/2),location.getY()+(location.getHeight()/2)), 
										new Point2D.Double(Main.width/2,Main.height/2));
	}

	public void takeDamage(int damage){
		this.health -= damage;
		
	}
	
	public void shoot(){
		
		shots.add(new Line2D.Double(location.x, location.y, location.x, location.y));
		double speed = 20.0;
		
		trajectories.add(SpaceBattle.getPoint(new Point2D.Double(location.x,location.y),new Point2D.Double(Main.width/2, Main.height/2),speed,0));
		
		shootTime += 300 + Main.random.nextDouble() * 1000;
		Sound.laser.play();
	}
	
	public void update(int xChange,int yChange){
		
		getAngle();
		spaceLocation.setLocation(xChange+location.x, yChange+location.y);
		if(System.currentTimeMillis() >= time){
			
			Point2D.Double temp = new Point2D.Double();
			
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
		at.rotate(rotation, location.x, location.y);
		g2d.setTransform(at);
		g2d.drawRect((int)spaceLocation.x-(xSize/2), (int)spaceLocation.y-(ySize/2),xSize,ySize);
		g2d.drawImage(shipImages[currentFrame],location.x-(xSize/2),location.y-(ySize/2), null);
		g2d.setTransform(saveAt);
		
		
		for (int i = 0; i < shots.size(); i++) {
			g2d.draw(shots.get(i));
		}
	}
	
	
}
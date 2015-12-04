package starSailor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EnemyShip{

	private ArrayList<Point2D.Double> trajectories;
	private ArrayList<Line2D.Double> shots;
	private int health,speed,currentFrame;
	private Rectangle location;
	private fightingStyle fightStyle;
	private double rotation,time,shootTime;
	private Image[] shipImages;
	
	public enum shipClass{
		FIGHTER,CARRIER,COMMAND
	}
	public enum fightingStyle{
		AGGRESSIVE,DEFENSIVE,TACTICAL
	}
	
	
	public EnemyShip(int x,int y,shipClass ship){
		
		time = System.currentTimeMillis();
		if(ship == shipClass.FIGHTER){
			this.health = 100;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			location = new Rectangle(x,y,16,16);
		}else if(ship == shipClass.CARRIER){
			this.health = 500;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			location = new Rectangle(x,y,32,32);
		}else if(ship == shipClass.COMMAND){
			this.health = 2000;
			shipImages = ResourceLoader.getBlockSprites("spaceship/enemyCarrierSprite.png", 32, 32);
			location = new Rectangle(x,y,32,128);
		}

		getAngle();
		int num = Main.random.nextInt(3);
		switch (num){
		case 0:
			fightStyle = fightingStyle.AGGRESSIVE;
			speed = 1;
			break;
		case 1:
			fightStyle = fightingStyle.DEFENSIVE;
			speed = 1;
			break;
		case 2:
			fightStyle = fightingStyle.TACTICAL;
			speed = 1;
			break;
		}
		
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		shootTime = System.currentTimeMillis()+3000;
	}
	
	public Point getLocation(){
		return location.getLocation();
	}
	
	private boolean isAlive(){
		
		if(health <= 0){
			return true;
		}
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
		
		shootTime = System.currentTimeMillis() + ((Main.random.nextDouble() * 1000) * 2);
	}
	
	public void update(){
		
		getAngle();
		if(System.currentTimeMillis() >= time + 300){
			
			Point2D.Double temp = new Point2D.Double();
			switch (fightStyle){
			case DEFENSIVE:
				temp.setLocation(SpaceBattle.getPoint(new Point2D.Double(location.getX()+(location.getWidth()/2),location.getY()+(location.getHeight()/2)),
						new Point2D.Double(Main.width/2-16,Main.height/2-16),speed,0));
				location.setLocation((int)(location.getX() + temp.x),(int)(location.getY() + temp.y));
			case AGGRESSIVE:
				temp.setLocation(SpaceBattle.getPoint(new Point2D.Double(location.getX()+(location.getWidth()/2),location.getY()+(location.getHeight()/2)),
						new Point2D.Double(Main.width/2-16,Main.height/2-16),speed,0));
				location.setLocation((int)(location.getX() + temp.x),(int)(location.getY() + temp.y));
			case TACTICAL:
				temp.setLocation(SpaceBattle.getPoint(new Point2D.Double(location.getX()+(location.getWidth()/2),location.getY()+(location.getHeight()/2)),
						new Point2D.Double(Main.width/2-16,Main.height/2-16),speed,0));
				location.setLocation((int)(location.getX() + temp.x),(int)(location.getY() + temp.y));
			}
			if(currentFrame == 1){
				currentFrame = 0;
			}else{
				currentFrame = 1;
			}
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
	
	public void draw(Graphics g){
		isAlive();
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform saveAt;
		AffineTransform at;
		at = new AffineTransform();
		saveAt = g2d.getTransform();
		at.rotate(rotation, location.x+16, location.y+16);
		g2d.setTransform(at);
		g2d.drawImage(shipImages[currentFrame],(int)(location.getX()+(location.getWidth()/2)),(int)(location.getY()+(location.getHeight()/2)), null);
		g2d.setTransform(saveAt);
		
		g2d.setColor(Color.red);
		for (int i = 0; i < shots.size(); i++) {
			g2d.draw(shots.get(i));
		}
	}
	
	
}
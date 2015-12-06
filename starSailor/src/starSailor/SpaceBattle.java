package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SpaceBattle {
	
	private ArrayList<Point2D.Double> trajectories;
	private ArrayList<Rectangle> fighterLocations;
	private ArrayList<Rectangle> carrierLocations;
	private ArrayList<Line2D.Double> shots;
	private ArrayList<Fighter> fighters;
	private ArrayList<Carrier> carriers;
	private Point2D.Double pointer; // displays the pointer location
	private Image background;
	
	public SpaceBattle(){
		int carriers = 5;
		int fighters = 5;
		this.carriers = new ArrayList<Carrier>(carriers);
		this.fighters = new ArrayList<Fighter>(fighters);
		background = ResourceLoader.getImage("background/planet1.png");
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		fighterLocations = new ArrayList<Rectangle>(fighters);
		carrierLocations = new ArrayList<Rectangle>(carriers);
		
		for (int i = 0; i < carriers; i++) {
			
			int x = Main.random.nextInt(300)+50;
			int y = Main.random.nextInt(Main.height-100)+50;

			carrierLocations.add(new Rectangle(x,y,32,32));
			this.carriers.add(new Carrier(x,y));
			for (int j = 0; j < fighters; j++) {
			
				int x2 = x + Main.random.nextInt(50)-25;
				int y2 = y + Main.random.nextInt(50)-25;
				this.fighters.add(new Fighter(x2,y2,this.carriers.get(i)));
				fighterLocations.add(new Rectangle(x2,y2,16,16));
			}
		}
		
		
		
		pointer = new Point2D.Double(0, 0);
	}
	
	public static Point2D.Double getPoint(Point2D.Double p1,Point2D.Double p2,double speed,double accuracy){
		double xdif = (p2.getX() - p1.getX());
		double ydif = (p2.getY() - p1.getY());
		double angle = 0;		// in radians
		
		angle = -Math.atan(ydif/xdif);
		if(xdif<0){
			if(ydif<0){
				angle += Math.PI;
			} else {
				angle -= +Math.PI;
			}
		}
		
		angle += (Main.random.nextGaussian()*accuracy);
		
		double xgain = 0;
		double ygain = 0;
		xgain = Math.cos(angle) * speed;
		ygain = -Math.sin(angle) * speed;
		

		
		return new Point2D.Double(xgain,ygain);
		
	}

	public static double getAngle(Point2D.Double p1,Point2D.Double p2){
		
		double xdif = (p2.getX() - p1.getX());
		double ydif = (p2.getY() - p1.getY());
		double angle = 0;		// in radians
		
		angle = -Math.atan(ydif/xdif);
		if(xdif<0){
			if(ydif<0){
				angle += Math.PI;
			} else {
				angle -= +Math.PI;
			}
		}
		
		return -angle;
		
	}
	
	public void shoot(Point mouseCoord){
		double accuracy = 1.0 / ((Player.accuracySTAT*3)+30);
		shots.add(new Line2D.Double(Main.width/2, Main.height/2,Main.width/2, Main.height/2));
		pointer.setLocation(mouseCoord.getX(),mouseCoord.getY());
		double speed = 20.0;
		trajectories.add(getPoint(new Point2D.Double(Main.width/2,Main.height/2),new Point2D.Double(mouseCoord.getX(), mouseCoord.getY()),speed,accuracy));
		Sound.laser.play();
			
	}
	
	public void update(){
		
		for(int i = 0; i < shots.size(); i++){
			
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).getX(), shots.get(i).getY1()+trajectories.get(i).getY(),shots.get(i).getX1(), shots.get(i).getY1());
		}
		Rectangle rect = new Rectangle(0,0,Main.width,Main.height);
		for (int i = 0; i < shots.size(); i++) {
				if(!rect.contains(shots.get(i).getX2(), shots.get(i).getY2())){
					shots.remove(i);
					trajectories.remove(i);
				}
			
		}
		
		for (int i = 0; i < carriers.size(); i++) {
			if(carriers.get(i).isAlive()){
				carriers.get(i).update();
			for (int j = 0; j < shots.size();j++) {
				if(carrierLocations.get(i).contains(shots.get(j).getP1())||carrierLocations.get(i).contains(shots.get(j).getP2())){
					carriers.get(i).takeDamage(100);
					shots.remove(j);
					trajectories.remove(j);
				}
			}
			}else{
				if(carriers.get(i).deadYet()){
					carrierLocations.remove(i);
					carriers.remove(i);
					Sound.explosion.play();
					i--;
				}else{
					carriers.get(i).die();
				}
			}
		}
		
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(background, 0, 0, Main.width, Main.height, null);
		g2d.setColor(Color.green);
		for(int i = 0; i< shots.size(); i++){
			g2d.drawLine((int)shots.get(i).getX1(),(int)shots.get(i).getY1(),(int)shots.get(i).getX2(),(int)shots.get(i).getY2());
		}
		g2d.drawRect(Main.width/2 - 16, Main.height/2-16, 32, 32);
		g2d.drawRect((int)pointer.x, (int)pointer.y, 1, 1);
		
		for (Carrier carrier : carriers) {
			carrier.draw(g2d);
		}
		for (Fighter fighter : fighters) {
			fighter.draw(g2d);
		}
			
	}

}

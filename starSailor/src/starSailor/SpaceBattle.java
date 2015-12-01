package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SpaceBattle {
	
	private ArrayList<Point2D.Double> trajectories;
	private ArrayList<Line2D.Double> shots;
	private EnemyShip ship;
	private Point2D.Double enemyLocation;
	
	public SpaceBattle(){
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		enemyLocation = new Point2D.Double(200,200);
		ship = new EnemyShip((int)enemyLocation.x,(int)enemyLocation.y,EnemyShip.shipClass.CARRIER);
	}
	
	public void shoot(Point mouseCoord){
		shots.add(new Line2D.Double(Main.width/2, Main.height/2,Main.width/2, Main.height/2));
		
		double speed = 20.0;

		trajectories.add(Main.getPoint(new Point2D.Double(Main.width/2,Main.height/2),new Point2D.Double(mouseCoord.getX(), mouseCoord.getY()),speed));
		
			
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
		
		
		
		
		
	}
	
	public void draw(Graphics g){
		if(Main.state == Main.State.SPACEBATTLE){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.green);
			for(int i = 0; i< shots.size(); i++){
				g2d.drawLine((int)shots.get(i).getX1(),(int)shots.get(i).getY1(),(int)shots.get(i).getX2(),(int)shots.get(i).getY2());
			}
			
			
			ship.draw(g2d);
			
		}else{
			shots.clear();
			trajectories.clear();
		}
	}

}

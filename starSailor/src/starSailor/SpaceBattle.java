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
	
	private ArrayList<Line2D.Double> trajectories;
	private ArrayList<Line2D.Double> shots;
	
	public SpaceBattle(){
		trajectories = new ArrayList<Line2D.Double>();
		shots = new ArrayList<Line2D.Double>();
	}
	
	public void shoot(Point mouseCoord){
		trajectories.add(new Line2D.Double(Main.width/2, Main.height/2, mouseCoord.getX() + 17, mouseCoord.getY() - 9));
		shots.add(new Line2D.Double(Main.width/2, Main.height/2, Main.width/2, Main.height/2));
	}
	
	public void update(){
		for(int i = 0; i < shots.size(); i++){
			double speed = 20.0;
			double xdif = trajectories.get(i).x2 - trajectories.get(i).x1;
			double ydif = (trajectories.get(i).y2 - trajectories.get(i).y1);
			double angle = 0;
			if(xdif < 0){
				angle = Math.atan(ydif/xdif);
			}else{
				angle = Math.atan(ydif/-xdif);
			}
			
			double xgain = Math.cos(angle) * speed;
			double ygain = Math.sin(angle) * speed;
			shots.get(i).setLine(shots.get(i).getX2(), shots.get(i).getY2(), shots.get(i).getX2() + xgain, shots.get(i).getY2() - ygain);
		}
		for (int i = 0; i < shots.size(); i++) {
			Rectangle rect = new Rectangle(0,0,Main.width,Main.height);
			if(!rect.contains(new Point2D.Double(shots.get(i).getX1(),shots.get(i).getY1()))){
				
				shots.remove(i);
				
			}
		}
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		for(int i = 0; i< shots.size(); i++){
			g2d.draw(shots.get(i));
		}
	}

}

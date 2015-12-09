package starSailor;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Command extends EnemyShip {

	public Command(int x, int y) {
		super(x,y,EnemyShip.shipClass.COMMAND);
		
		
	}
	
	@Override
	public void shoot(){
		shots.add(new Line2D.Double(actualLocation.x, actualLocation.y, actualLocation.x, actualLocation.y));
		double speed = 20.0;
		
		trajectories.add(SpaceBattle.getPoint(new Point2D.Double(actualLocation.x,actualLocation.y),new Point2D.Double(Main.width/2, Main.height/2),speed,1.0/10));
		
		shootTime =System.currentTimeMillis() + Main.random.nextDouble() * 100;
	}
	
	@Override
	public void update(double xChange,double yChange){

		actualLocation.setLocation(actualLocation.x-xChange, actualLocation.y-yChange) ;
		getAngle();
		if(System.currentTimeMillis() >= time){
			
			if(currentFrame == 1){
				currentFrame = 0;
			}else{
				currentFrame = 1;
			}
			time += Main.random.nextInt(500);
		}
		getAngle();
		if(System.currentTimeMillis() >= shootTime){
			shoot();
		}
		
		if(System.currentTimeMillis() >= moveTime){
			Point2D.Double loc;
			loc = new Point2D.Double(Main.width/2, Main.height/2);
		
			double a = loc.x - actualLocation.x;
			double b = loc.y - actualLocation.y;
			if(Math.sqrt((a*a)+(b*b))>500){
				moveAmount = SpaceBattle.getPoint(actualLocation, loc, .5, Math.PI/6);
			}else{
				moveAmount = new Point2D.Double(0, 0);
			}
			moveTime = System.currentTimeMillis()+500;
		}
		
		actualLocation.setLocation(actualLocation.x + moveAmount.x, actualLocation.y + moveAmount.y);
		
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
			if(shots.get(i).getP1() == shots.get(i).getP2()){
				shots.remove(i);
				trajectories.remove(i);
				i--;
			}
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
			if(shots.get(i).intersects(new Rectangle(Main.width/2 - 16, Main.height/2 - 16, 32, 32))){
				SpaceBattle.playerDamage(10);
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

}

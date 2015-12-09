package starSailor;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Fighter extends EnemyShip {

	private Carrier commander;
	private boolean isCommanderAlive;
	
 	public Fighter(int x, int y, Carrier commander){

		super(x, y, EnemyShip.shipClass.FIGHTER);
		
		isCommanderAlive = true;
		this.commander = commander;
		
	}
	
	@Override
	public void update(double xChange,double yChange){
		
		actualLocation.setLocation(actualLocation.x-xChange, actualLocation.y-yChange);
		if(System.currentTimeMillis() >= time){
			
			if(currentFrame == 1){
				currentFrame = 0;
			}else{
				currentFrame = 1;
			}
			time += Main.random.nextInt(500);
		}
		
		if(System.currentTimeMillis() >= moveTime){
			Point2D.Double loc;
			if(isCommanderAlive){
				loc = commander.getLocation();
			}else{
				loc = new Point2D.Double(Main.width/2, Main.height/2);
			}
		
			double a = loc.x - actualLocation.x;
			double b = loc.y - actualLocation.y;
			if(Math.sqrt((a*a)+(b*b))>=50){
				moveAmount = SpaceBattle.getPoint(actualLocation, loc, 3, Math.PI/6);
			}else{
				double angle = SpaceBattle.getAngle(actualLocation, loc);
				angle += Math.PI/2;
				moveAmount = SpaceBattle.moveAngle(actualLocation, angle, 3, Math.PI/6);
			}
			moveTime = System.currentTimeMillis()+500;
		}
		
		
		actualLocation.setLocation(actualLocation.x+moveAmount.x, actualLocation.y+moveAmount.y);
		
		
		getAngle();
		if(System.currentTimeMillis() >= shootTime){
			shoot();
		}
		
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
			if(shots.get(i).getP1() == shots.get(i).getP2()){
				shots.remove(i);
				trajectories.remove(i);
				i--;
			}
			if(shots.get(i).intersects(new Rectangle(Main.width/2 - 16, Main.height/2 - 16, 32, 32))){
				//SpaceBattle.playerDamage(5);
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

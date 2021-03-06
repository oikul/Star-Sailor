package starSailor;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Carrier extends EnemyShip {


	private fightingStyle fightStyle;

	public enum fightingStyle{
		AGGRESSIVE,DEFENSIVE,TACTICAL
	}
	
	public Carrier(int x, int y) {
		super(x, y, EnemyShip.shipClass.CARRIER);
		
		int num = Main.random.nextInt(3);
		switch (num){
		case 0:
			fightStyle = fightingStyle.AGGRESSIVE;
			break;
		case 1:
			fightStyle = fightingStyle.DEFENSIVE;
			break;
		case 2:
			fightStyle = fightingStyle.TACTICAL;
			break;
		}
		
	}
	
	@Override
	public void update(double xChange,double yChange){
		

		actualLocation.setLocation(actualLocation.x-xChange, actualLocation.y-yChange);
		if(System.currentTimeMillis() >= moveTime){
		double a,b;
			switch (fightStyle){
			case DEFENSIVE:

				a = actualLocation.x - Main.width/2;
				b = actualLocation.y - Main.height/2;
				
				if(Math.sqrt((a*a)+(b*b)) < Main.height/2 ){
					
					double angle = SpaceBattle.getAngle(actualLocation, new Point2D.Double(Main.width/2,Main.height/2));
					angle += Math.PI/2;
					moveAmount = SpaceBattle.moveAngle(actualLocation, angle, 2, Math.PI/6);
					
				}else{
					moveAmount = new Point2D.Double(0,0);
				}
				
				
				break;
			case AGGRESSIVE:
			
				moveAmount = SpaceBattle.getPoint(actualLocation, new Point2D.Double(Main.width/2, Main.height/2), 2, Math.PI/6);
				
			
				break;
			case TACTICAL:
				a = actualLocation.x - Main.width/2;
				b = actualLocation.y - Main.height/2;
				
				if(Math.sqrt((a*a)+(b*b)) < Main.height/2 ){
					
					double angle = SpaceBattle.getAngle(actualLocation, new Point2D.Double(Main.width/2,Main.height/2));
					angle += Math.PI/2;
					moveAmount = SpaceBattle.moveAngle(actualLocation, angle, 2, Math.PI/6);
					
				}else{
					moveAmount = SpaceBattle.getPoint(actualLocation, new Point2D.Double(Main.width/2, Main.height/2), 2, Math.PI/6);
				}
				
				break;
			}
			moveTime = System.currentTimeMillis() + 500;
		}
		actualLocation.setLocation(actualLocation.x+moveAmount.x,actualLocation.y+moveAmount.y);
		getAngle();
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
			if(shots.get(i).getP1() == shots.get(i).getP2()){
				shots.remove(i);
				trajectories.remove(i);
				i--;
			}
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).x, shots.get(i).getY1()+trajectories.get(i).y, shots.get(i).getX1(), shots.get(i).getY1());
			if(shots.get(i).intersects(new Rectangle(Main.width/2 - 16, Main.height/2 - 16, 32, 32))){
				SpaceBattle.playerDamage(20);
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

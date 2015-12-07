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
	public void update(int xChange,int yChange){
		

		location.setLocation(location.x+xChange, location.y+yChange);
		switch (fightStyle){
		case DEFENSIVE:
			location.setLocation((int)(location.getX()),(int)(location.getY()));
		case AGGRESSIVE:
			location.setLocation((int)(location.getX()),(int)(location.getY()));
		case TACTICAL:
			location.setLocation((int)(location.getX()),(int)(location.getY()));
		}
		getAngle();
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

}

package starSailor;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Command extends EnemyShip {

	public Command(int x, int y) {
		super(x,y,EnemyShip.shipClass.COMMAND);
		
		
	}
	
	@Override
	public void update(double xChange,double yChange){
	
		location.setLocation((int)xChange+location.x, (int)yChange+location.y);
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

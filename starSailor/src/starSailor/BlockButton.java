package starSailor;

import java.awt.Point;
import java.awt.Rectangle;

public class BlockButton extends Block{
	
	private String stat;

	public BlockButton(String imagePath, boolean isSolid, String stat) {
		super(imagePath, isSolid);
		this.stat = stat;
	}
	
	public void update(Point blockLocation, Point click){
		if(new Rectangle(blockLocation.x, blockLocation.y, 16, 16).contains(click)){
			if(stat.equals("health") && Player.killCount >= 50){
				System.out.println(Player.HPSTAT);
				Player.HPSTAT ++;
				System.out.println(Player.HPSTAT);
				Player.killCount -= 50;
			}
			if(stat.equals("speed") && Player.killCount >= 50){
				System.out.println(Player.speedSTAT);
				Player.speedSTAT ++;
				System.out.println(Player.speedSTAT);
				Player.killCount -= 50;
			}
			if(stat.equals("accuracy") && Player.killCount >= 50){
				System.out.println(Player.accuracySTAT);
				Player.accuracySTAT ++;
				System.out.println(Player.accuracySTAT);
				Player.killCount -= 50;
			}
		}
	}

}

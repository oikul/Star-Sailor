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
				if(Player.HPSTAT < Player.HPMAXSTAT){
					Player.killCount -= 50;
					Player.HPSTAT = Player.HPMAXSTAT;
				}else{
					Player.killCount -= 50;
					Player.HPMAXSTAT += 10;
				}
			}
			if(stat.equals("speed") && Player.killCount >= 10 + 5 * Player.speedSTAT){
				Player.killCount -= 10 + 5 * Player.speedSTAT;
				Player.speedSTAT ++;
			}
			if(stat.equals("accuracy") && Player.killCount >= 10 + 5 * Player.accuracySTAT){
				Player.killCount -= 10 + 5 * Player.accuracySTAT;
				Player.accuracySTAT ++;
			}
		}
	}

}

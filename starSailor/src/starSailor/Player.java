package starSailor;

import java.awt.Image;

public class Player {
	
	private Image[] playerImages;
	private int currentFrame = 0;
	private long time;
	
	public Player(){
		time = System.currentTimeMillis();
	}
	
	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
			if(currentFrame < playerImages.length){
				currentFrame++;
			}else{
				currentFrame = 0;
			}
			time = newTime;
		}
	}

}

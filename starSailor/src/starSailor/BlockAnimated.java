package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class BlockAnimated extends Block {
	
	private long time, updateSpeed;
	private Image[] images;
	private int currentIndex = 0;

	public BlockAnimated(String imagePath, boolean isSolid, int spriteWidth, int spriteHeight, long updateSpeed) {
		super(imagePath, isSolid);
		images = ResourceLoader.getBlockSprites(imagePath, spriteWidth, spriteHeight);
		this.updateSpeed = updateSpeed;
		time = System.currentTimeMillis();
	}

	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + updateSpeed){
			if(currentIndex < images.length - 1){
				currentIndex ++;
			}else{
				currentIndex = 0;
			}

			time = newTime;
		}
	}

	@Override
	public void draw(Graphics g, int x, int y){
		g.drawImage(images[currentIndex], x, y, null);
	}

}

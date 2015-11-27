package starSailor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BlockAnimated extends Block {
	
	private long time;
	private BufferedImage spriteSheet;
	private Image[] images;
	private int currentIndex = 0;

	public BlockAnimated(String imagePath, boolean isSolid) {
		super(imagePath, isSolid);
		spriteSheet = (BufferedImage) this.image;
		images = new Image[spriteSheet.getWidth()/16];
		for(int i = 0; i < images.length; i++){
			images[i] = spriteSheet.getSubimage(i * 16, 0, 16, 16);
		}
		time = System.currentTimeMillis();
	}
	
	@Override
	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
			currentIndex ++;
			time = newTime;
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y){
		g.drawImage(images[currentIndex], x, y, null);
	}

}

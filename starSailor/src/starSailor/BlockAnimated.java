package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BlockAnimated extends Block {
	
	private static long time;
	private BufferedImage spriteSheet;
	private static Image[] images;
	private static int currentIndex = 0;

	public BlockAnimated(String imagePath, boolean isSolid) {
		super(imagePath, isSolid);
		spriteSheet = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = spriteSheet.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();
		images = new Image[spriteSheet.getWidth()/16];
		for(int i = 0; i < images.length; i++){
			images[i] = spriteSheet.getSubimage(i * 16, 0, 16, 16);
		}
		time = System.currentTimeMillis();
	}
	
	public static void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
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

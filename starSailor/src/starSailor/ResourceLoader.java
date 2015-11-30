package starSailor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceLoader {
	
	private static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String path){
		try{
			URL url = rl.getClass().getClassLoader().getResource("resources/" + path);
			return new ImageIcon(url).getImage();
		}catch (Exception e){
			return null;
		}
	}
	
	public static Image[][] getSprites(String path, int spriteSize){
		Image spriteSheet = ResourceLoader.getImage(path);
		BufferedImage BI = new BufferedImage(spriteSheet.getWidth(null), spriteSheet.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Image[][] sprites = new Image[spriteSheet.getWidth(null)/spriteSize][spriteSheet.getHeight(null)/spriteSize];
		Graphics2D bGr = BI.createGraphics();
		bGr.drawImage(spriteSheet, 0, 0, null);
		for(int i = 0; i <= sprites.length - 1; i++){
			for(int j = 0; j <= sprites[0].length - 1; j++){
				sprites[i][j] = BI.getSubimage(i * spriteSize, j * spriteSize, spriteSize, spriteSize);
			}
		}
		return sprites;
	}

}

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
	
	public static Image[][] getPlayerSprites(String path, int spriteWidth, int spriteHeight){
		Image spriteSheet = ResourceLoader.getImage(path);
		BufferedImage BI = new BufferedImage(spriteSheet.getWidth(null), spriteSheet.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Image[][] sprites = new Image[spriteSheet.getWidth(null)/spriteWidth][spriteSheet.getHeight(null)/spriteHeight];
		Graphics2D bGr = BI.createGraphics();
		bGr.drawImage(spriteSheet, 0, 0, null);
		for(int i = 0; i <= sprites.length - 1; i++){
			for(int j = 0; j <= sprites[0].length - 1; j++){
				sprites[i][j] = BI.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
			}
		}
		return sprites;
	}
	
	public static Image[] getBlockSprites(String path, int spriteWidth, int spriteHeight){
		Image spriteSheet = ResourceLoader.getImage(path);
		BufferedImage BI = new BufferedImage(spriteSheet.getWidth(null), spriteSheet.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Image[] sprites = new Image[spriteSheet.getWidth(null)/spriteWidth];
		Graphics2D bGr = BI.createGraphics();
		bGr.drawImage(spriteSheet, 0, 0, null);
		for(int i = 0; i <= sprites.length - 1; i++){
			sprites[i] = BI.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
		}
		return sprites;
	}

}

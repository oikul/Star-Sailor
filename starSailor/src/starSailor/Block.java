package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	private int x, y;
	private Image image;
	private boolean isSolid;
	
	public Block(String imagePath, int x, int y, boolean isSolid){
		image = ResourceLoader.getImage(imagePath);
		this.x = x;
		this.y = y;
		this.isSolid = isSolid;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, x, y, null);
	}

}

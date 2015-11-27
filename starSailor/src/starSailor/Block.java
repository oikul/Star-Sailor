package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	public static final Block grass_forest = new Block("grass/grass_forest", false);
	public static final Block grass_plains = new Block("grass/grass_plains", false);
	public static final Block grass_tundra = new Block("grass/grass_tundra", false);
	public static final Block grass_mountains = new Block("grass/grass_mountains", false);
	public static final Block grass_jungle = new Block("grass/grass_jungle", false);
	
	private Image image;
	private boolean isSolid;
	
	public Block(String imagePath, boolean isSolid){
		image = ResourceLoader.getImage(imagePath);
		this.isSolid = isSolid;
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}

}

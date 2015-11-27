package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	//grass blocks
	public static final Block grass_forest = new Block("grass/grass_forest", false);
	public static final Block grass_plains = new Block("grass/grass_plains", false);
	public static final Block grass_tundra = new Block("grass/grass_tundra", false);
	public static final Block grass_mountains = new Block("grass/grass_mountains", false);
	public static final Block grass_jungle = new Block("grass/grass_jungle", false);
	
	//tree blocks
	public static final Block tree_oak = new Block("tree/tree_oak", true);
	public static final Block tree_jungle = new Block("tree/tree_jungle", true);
	
	//liquids
	public static final Block lava = new Block("lava/lava", true);
	public static final Block water_ocean = new Block("water/water_ocean", true);
	public static final Block water_river = new Block("water/water_river", true);
	public static final Block water_jungle = new Block("water/water_jungle", true);
	public static final Block water_swamp = new Block("water/water_swamp", true);
	
	//stone
	public static final Block stone = new Block("stone/stone", true);
	
	//ice
	public static final Block ice = new Block("ice/ice", false);
	
	
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

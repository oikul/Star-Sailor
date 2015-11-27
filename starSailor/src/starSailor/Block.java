package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	//grass blocks
	public static final Block grass_forest = new Block("grass/grass_forest.png", false);
	public static final Block grass_plains = new Block("grass/grass_plains.png", false);
	public static final Block grass_tundra = new Block("grass/grass_tundra.png", false);
	public static final Block grass_mountains = new Block("grass/grass_mountains.png", false);
	public static final Block grass_jungle = new Block("grass/grass_jungle.png", false);
	
	//tree blocks
	public static final Block tree_oak = new Block("trees/tree_oak.png", true);
	public static final Block tree_jungle = new Block("tree/tree_jungle", true);
	
	//liquids
	/*public static final BlockAnimated lava = new BlockAnimated("lava/lava", true);
	public static final BlockAnimated water_ocean = new BlockAnimated("water/water_ocean", true);
	public static final BlockAnimated water_river = new BlockAnimated("water/water_river", true);
	public static final BlockAnimated water_jungle = new BlockAnimated("water/water_jungle", true);
	public static final BlockAnimated water_swamp = new BlockAnimated("water/water_swamp", true);*/
	
	//stone
	public static final Block stone = new Block("stone/stone", true);
	
	//ice
	public static final Block ice = new Block("ice/ice", false);
	
	
	protected Image image;
	private boolean isSolid;
	
	public Block(String imagePath, boolean isSolid){
		image = ResourceLoader.getImage(imagePath);
		this.isSolid = isSolid;
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g, int x, int y){
		if(image != null){
			g.drawImage(image, x, y, 16, 16, null);
		}
	}

}

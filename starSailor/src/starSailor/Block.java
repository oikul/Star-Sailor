package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	//stone
	public static final Block stone = new Block("stone/stone.png", false);
	public static final Block rock = new Block("stone/rock.png", false);
	public static final Block clay = new Block("stone/clay.png", false);
	public static final Block obsidian = new Block("stone/obsidian.png", false);
	
	//grass blocks
	public static final Block grass_forest = new Block("grass/grass_forest.png", false);
	public static final Block grass_plains = new Block("grass/grass_plains.png", false);
	public static final Block grass_tundra = new Block("grass/grass_tundra.png", false);
	public static final Block grass_mountains = new Block("grass/grass_mountains.png", false);
	public static final Block grass_jungle = new Block("grass/grass_jungle.png", false);
	public static final Block grass_snowy = new Block("grass/grass_snowy.png", false);
	public static final Block grass_steppe = new Block("grass/grass_steppe.png", false);
	public static final Block grass_alien = new Block("grass/grass_alien.png", false);
	
	//sand blocks
	public static final Block sand = new Block("sand/sand.png", false);
	public static final Block sand_snowy = new Block("sand/sand_snowy.png", false);
	
	//snow blocks
	public static final Block snow = new Block("snow/snow.png", false);
	
	//tree blocks
	public static final Block tree_oak = new Block("trees/tree_oak.png", true);
	public static final Block tree_jungle = new Block("tree/tree_jungle.png", true);
	
	//liquids
	//public static final BlockAnimated lava = new BlockAnimated("lava/lava.png", true);
	public static final BlockAnimated water = new BlockAnimated("water/water.png", true);
	//public static final BlockAnimated water_river = new BlockAnimated("water/water_river.png", true);
	//public static final BlockAnimated water_jungle = new BlockAnimated("water/water_jungle.png", true);
	//public static final BlockAnimated water_swamp = new BlockAnimated("water/water_swamp.png", true);
	
	
	
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
	
	public void draw(Graphics g, int x, int y){
		if(image != null){
			g.drawImage(image, x, y, 16, 16, null);
		}
	}

}

package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	//decoration
	public static final Block flower = new Block("decoration/flower.png", false);
	public static final Block flowers = new Block("decoration/flowers.png", false);
	
	//grass blocks
	public static final Block grass_forest = new Block("grass/grass_forest.png", false);
	public static final Block grass_jungle = new Block("grass/grass_jungle.png", false);
	public static final Block grass_mountains = new Block("grass/grass_mountains.png", false);
	public static final Block grass_plains = new Block("grass/grass_plains.png", false);
	public static final Block grass_rainforest = new Block("grass/grass_rainforest.png", false);
	public static final Block grass_savannah = new Block("grass/grass_savannah.png", false);
	public static final Block grass_snowy = new Block("grass/grass_snowy.png", false);
	public static final Block grass_steppe = new Block("grass/grass_steppe.png", false);
	public static final Block grass_tundra = new Block("grass/grass_tundra.png", false);
	public static final Block grass_alien = new Block("grass/grass_alien.png", false);
	
	//ice
	public static final Block ice = new Block("ice/ice.png", false);
	public static final Block snow = new Block("ice/snow.png", false);
	public static final Block ice_spikes = new Block("ice/ice_spikes.png", true);
	
	//sand blocks
	public static final Block sand_arid = new Block("sand/sand_arid.png", false);
	public static final Block sand_beach = new Block("sand/sand_beach.png", false);
	public static final Block sand_cracked = new Block("sand/sand_cracked.png", false);
	public static final Block sand_dunes = new Block("sand/sand_dunes.png", false);
	public static final Block sand = new Block("sand/sand.png", false);
	
	//stone
	public static final Block rock = new Block("stone/rock.png", false);
	public static final Block rocks = new Block("stone/rocks.png", true);
	public static final Block stone_mossy = new Block("stone/stone_mossy.png", false);
	public static final Block stone_snowy = new Block("stone/stone_snowy.png", false);
	public static final Block stone_solid = new Block("stone/stone_solid.png", false);
	public static final Block stone_volcanic = new Block("stone/stone_volcanic.png", false);
	public static final Block stone = new Block("stone/stone.png", false);
	public static final Block clay = new Block("stone/clay.png", false);
	public static final Block obsidian = new Block("stone/obsidian.png", false);
	
	//tree blocks
	public static final Block tree_baobab = new Block("trees/tree_baobab.png", true);
	public static final Block tree_birch = new Block("trees/tree_birch.png", true);
	public static final Block tree_birch_1 = new Block("trees/tree_birch_1.png", true);
	public static final Block tree_cactus = new Block("trees/tree_cactus.png", true);
	public static final Block tree_oak = new Block("trees/tree_oak.png", true);
	public static final Block tree_oak_1 = new Block("trees/tree_oak_1.png", true);
	public static final Block tree_palm = new Block("trees/tree_palm.png", true);
	public static final Block tree_pine = new Block("trees/tree_pine.png", true);
	public static final Block tree_rubber = new Block("trees/tree_rubber.png", true);
	public static final Block tree_sequoia = new Block("trees/tree_sequoia.png", true);
	public static final Block tree_shrub = new Block("trees/tree_shrub.png", true);
	public static final Block tree_shrub_1 = new Block("trees/tree_shrub_1.png", true);
	public static final Block tree_spruce = new Block("trees/tree_spruce.png", true);
	
	//liquids
	public static final BlockAnimated lava = new BlockAnimated("lava/lava.png", true);
	public static final BlockAnimated water_murky = new BlockAnimated("water/water_murky.png", true);
	public static final BlockAnimated water_ocean = new BlockAnimated("water/water_ocean.png", true);
	public static final BlockAnimated water_river = new BlockAnimated("water/water_river.png", true);
	
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

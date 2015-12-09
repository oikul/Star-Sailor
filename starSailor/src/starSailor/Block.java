package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Block {
	
	//technical
	public static final Block solid = new Block("", true);
	
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
	public static final Block rocks = new Block("stone/rocks.png", false);
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
	public static final Block tree_shrub = new Block("trees/tree_shrub.png", false);
	public static final Block tree_shrub_1 = new Block("trees/tree_shrub_1.png", false);
	public static final Block tree_spruce = new Block("trees/tree_spruce.png", true);
	
	//liquids
	public static final BlockAnimated lava = new BlockAnimated("lava/lava.png", true, 16, 16, 500);
	public static final BlockAnimated water_murky = new BlockAnimated("water/water_murky.png", true, 16, 16, 500);
	public static final BlockAnimated water_ocean = new BlockAnimated("water/water_ocean.png", true, 16, 16, 500);
	public static final BlockAnimated water_river = new BlockAnimated("water/water_river.png", true, 16, 16, 500);
	
	//ship_interior / space stations
	public static final Block iron = new Block("ship_interior/iron.png", false);
	public static final BlockAnimated engine_fire = new BlockAnimated("ship_interior/engine_fire_sprites.png", false, 64, 192, 200);
	public static final Block engine = new Block("ship_interior/engine.png", true);
	public static final Block ship_walls = new Block("ship_interior/ship_walls.png", true);
	public static final Block glass = new Block("ship_interior/glass.png", true);
	public static final Block ship_lights = new Block("ship_interior/ship_lights.png", true);
	public static final Block control_desk = new Block("ship_interior/control_desk.png", false);
	public static final Block wood = new Block("wood/wood.png", false);
	public static final Block counter = new Block("wood/counter.png", true);
	public static final Block crates = new Block("decoration/crates.png", true);
	public static final Block smallship = new Block("spaceship/smallShipParked.png", true);
	public static final Block bigship = new Block("spaceship/EnemyCommandShip2.png", true);
	public static final Block smallshipf = new Block("spaceship/smallShipParkedFlip.png", true);
	public static final Block bigshipf = new Block("spaceship/EnemyCommandShip2Flip.png", true);
	public static final Block smallfighter = new Block("spaceship/smallFighterParked.png", true);
	public static final Block smallfighterf = new Block("spaceship/smallFighterParkedFlip.png", true);
	public static final Block entrance = new Block("entrance.png", true);
	public static final BlockButton health = new BlockButton("ship_interior/health_button.png", true, "health");
	public static final BlockButton speed = new BlockButton("ship_interior/speed_button.png", true, "speed");
	public static final BlockButton accuracy = new BlockButton("ship_interior/accuracy_button.png", true, "accuracy");
	
	protected Image image;
	private boolean isSolid;
	public boolean activateable = true;
	
	public Block(String imagePath, boolean isSolid){
		image = ResourceLoader.getImage(imagePath);
		this.isSolid = isSolid;
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public void draw(Graphics g, int x, int y){
		if(image != null){
			g.drawImage(image, x, y, null);
		}
	}

}

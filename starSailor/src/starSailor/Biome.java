package starSailor;

import java.awt.Color;
import java.util.ArrayList;

public class Biome {
	
	//very cold biomes
	public static Biome polar_desert = new Biome("polar desert", Color.white);
	public static Biome ice_spikes = new Biome("ice spikes", Color.white);
	public static Biome frozen_lakes = new Biome("frozen lakes", Color.white);
	public static Biome ice_sheet = new Biome("ice sheet", Color.white);
	public static Biome ice_bergs = new Biome("ice bergs", Color.white);
	
	//cold biomes
	public static Biome tundra = new Biome("tundra", Color.lightGray);
	public static Biome mountain = new Biome("mountain", Color.gray);
	public static Biome taiga = new Biome("taiga", Color.lightGray);
	public static Biome mountain_forest = new Biome("mountain forest", Color.lightGray);
	public static Biome ocean = new Biome("ocean", Color.blue);
	
	//medium biomes
	public static Biome steppe = new Biome("steppe", Color.lightGray);
	public static Biome plains = new Biome("plains", Color.green);
	public static Biome forest = new Biome("forest", Color.green);
	public static Biome lakes = new Biome("lakes", Color.gray);
	public static Biome islands = new Biome("islands", Color.blue);
	
	//hot biomes
	public static Biome desert_plains = new Biome("desert plains", Color.yellow);
	public static Biome canyon = new Biome("canyon", Color.orange);
	public static Biome savannah = new Biome("savannah", Color.green);
	public static Biome jungle = new Biome("jungle", Color.green);
	public static Biome rainforest = new Biome("rainforest", Color.green);
	
	//very hot biomes
	public static Biome lava_ocean = new Biome("lava ocean", Color.red);
	public static Biome lava_islands = new Biome("lava islands", Color.red);
	public static Biome lava_lakes = new Biome("lava lakes", Color.darkGray);
	public static Biome igneous_desert = new Biome("igneous desert", Color.gray);
	public static Biome volcanic_mountains = new Biome("volcanic_mountains", Color.darkGray);
	
	private String name;
	private Color color;
	private ArrayList<BiomePart> terrain, decoration;
	
	public Biome(String name, Color color){
		this.name = name;
		this.color = color;
		terrain = new ArrayList<BiomePart>();
		decoration = new ArrayList<BiomePart>();
	}
	
	public static void createBiomes(){
		polar_desert.addBlock(Block.sand_cracked, 0.0, 1.0);
		polar_desert.addBlock(Block.snow, 0.7, 1.0);
		
		ice_spikes.addBlock(Block.snow, 0.0, 1.0);
		ice_spikes.addBlock(Block.ice, 0.6, 1.0);
		ice_spikes.addDecoration(Block.ice_spikes, 0.7, 1.0, 0.1);
		
		frozen_lakes.addBlock(Block.snow, 0.0, 1.0);
		frozen_lakes.addBlock(Block.ice, 0.7, 1.0);
		
		ice_sheet.addBlock(Block.ice, 0.0, 1.0);
		
		ice_bergs.addBlock(Block.water_ocean, 0.0, 1.0);
		ice_bergs.addBlock(Block.snow, 0.55, 1.0);
		ice_bergs.addBlock(Block.ice, 0.5, 0.55);
		
		tundra.addBlock(Block.grass_tundra, 0.0, 1.0);
		tundra.addBlock(Block.water_murky, 0.7, 1.0);
		
		mountain.addBlock(Block.grass_mountains, 0.0, 1.0);
		mountain.addBlock(Block.stone_volcanic, 0.6, 0.85);
		mountain.addBlock(Block.snow, 0.85, 1.0);
		mountain.addDecoration(Block.tree_spruce, 0.2, 0.4, 0.3);
		mountain.addDecoration(Block.rocks, 0.55, 0.65, 0.1);
		
		taiga.addBlock(Block.grass_snowy, 0.0, 1.0);
		taiga.addBlock(Block.water_river, 0.5, 0.6);
		taiga.addDecoration(Block.tree_pine, 0.0, 0.4, 0.5);
		taiga.addDecoration(Block.tree_pine, 0.7, 1.0, 0.5);
		
		mountain_forest.addBlock(Block.grass_mountains, 0.0, 1.0);
		mountain_forest.addBlock(Block.stone, 0.5, 0.7);
		mountain_forest.addDecoration(Block.tree_pine, 0.75, 1.0, 0.15);
		mountain_forest.addDecoration(Block.tree_spruce, 0.75, 1.0, 0.15);
		mountain_forest.addDecoration(Block.tree_pine, 0.1, 0.4, 0.15);
		mountain_forest.addDecoration(Block.tree_spruce, 0.1, 0.4, 0.15);
		
		ocean.addBlock(Block.water_ocean, 0.0, 1.0);
		ocean.addDecoration(Block.rocks, 0.0, 1.0, 0.01);
		
		steppe.addBlock(Block.grass_steppe, 0.0, 1.0);
		steppe.addDecoration(Block.tree_shrub, 0.0, 1.0, 0.1);
		steppe.addDecoration(Block.tree_shrub_1, 0.0, 1.0, 0.1);
		
		plains.addBlock(Block.grass_plains, 0.0, 1.0);
		plains.addDecoration(Block.tree_oak_1, 0.0, 1.0, 0.001);
		plains.addDecoration(Block.tree_shrub_1, 0.0, 1.0, 0.1);
		plains.addDecoration(Block.tree_shrub, 0.0, 1.0, 0.05);
		plains.addDecoration(Block.flowers, 0.0, 1.0, 0.01);
		
		forest.addBlock(Block.grass_forest, 0.0, 1.0);
		forest.addDecoration(Block.tree_oak_1, 0.0, 0.5, 0.5);
		forest.addDecoration(Block.tree_birch, 0.3, 0.7, 0.4);
		forest.addDecoration(Block.tree_shrub, 0.0, 1.0, 0.1);
		
		lakes.addBlock(Block.grass_plains, 0.0, 1.0);
		lakes.addBlock(Block.water_murky, 0.65, 1.0);
		lakes.addDecoration(Block.tree_shrub_1, 0.0, 0.55, 0.2);
		
		islands.addBlock(Block.water_ocean, 0.0, 1.0);
		islands.addBlock(Block.sand_beach, 0.7, 1.0);
		islands.addBlock(Block.grass_jungle, 0.85, 1.0);
		islands.addDecoration(Block.tree_palm, 0.9, 1.0, 0.3);
		
		desert_plains.addBlock(Block.sand_dunes, 0.0, 1.0);
		desert_plains.addDecoration(Block.tree_cactus, 0.0, 0.6, 0.2);
		desert_plains.addDecoration(Block.tree_shrub_1, 0.0, 1.0, 0.02);
		desert_plains.addDecoration(Block.rocks, 0.4, 0.6, 0.05);
		
		canyon.addBlock(Block.clay, 0.0, 1.0);
		canyon.addDecoration(Block.tree_shrub_1, 0.0, 1.0, 0.01);
		
		savannah.addBlock(Block.grass_savannah, 0.0, 1.0);
		savannah.addDecoration(Block.tree_baobab, 0.2, 1.0, 0.02);
		savannah.addDecoration(Block.tree_shrub_1, 0.0, 0.4, 0.05);
		
		jungle.addBlock(Block.grass_jungle, 0.0, 1.0);
		jungle.addDecoration(Block.tree_palm, 0.0, 1.0, 0.2);
		jungle.addDecoration(Block.tree_rubber, 0.0, 1.0, 0.2);
		jungle.addDecoration(Block.tree_shrub, 0.3, 0.7, 0.03);
		
		rainforest.addBlock(Block.grass_rainforest, 0.0, 1.0);
		rainforest.addBlock(Block.water_river, 0.45, 0.55);
		rainforest.addDecoration(Block.tree_palm, 0.0, 0.45, 0.2);
		rainforest.addDecoration(Block.tree_rubber, 0.0, 0.45, 0.2);
		rainforest.addDecoration(Block.tree_shrub, 0.0, 0.45, 0.03);
		rainforest.addDecoration(Block.tree_palm, 0.55, 1.0, 0.2);
		rainforest.addDecoration(Block.tree_rubber, 0.55, 1.0, 0.2);
		rainforest.addDecoration(Block.tree_shrub, 0.55, 1.0, 0.03);
		
		lava_ocean.addBlock(Block.lava, 0.0, 1.0);
		
		lava_islands.addBlock(Block.lava, 0.0, 1.0);
		lava_islands.addDecoration(Block.stone, 0.6, 0.8, 0.1);
		lava_islands.addBlock(Block.stone_volcanic, 0.7, 1.0);
		
		lava_lakes.addBlock(Block.stone_volcanic, 0.0, 1.0);
		lava_lakes.addBlock(Block.lava, 0.7, 1.0);
		
		igneous_desert.addBlock(Block.stone_volcanic, 0.0, 0.5);
		igneous_desert.addBlock(Block.stone, 0.5, 1.0);
		
		volcanic_mountains.addBlock(Block.stone_volcanic, 0.0, 1.0);
		volcanic_mountains.addBlock(Block.lava, 0.9, 1.0);
		volcanic_mountains.addDecoration(Block.rocks, 0.6, 0.9, 0.1);
		
		
	}
	
	public void addBlock(Block block, double start, double end){
		terrain.add(new BiomePart(block, start, end, 1.0));
	}
	
	public void addDecoration(Block block, double start, double end, double chance){
		decoration.add(new BiomePart(block, start, end, chance));
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Block[][] buildTerrain(double[][] noise){
		Block[][] blocks = new Block[noise.length][noise[0].length];
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				for(int k = 0; k < terrain.size(); k++){
					if(noise[i][j] >= terrain.get(k).getStart() && noise[i][j] <= terrain.get(k).getEnd()){
						if(Main.random.nextDouble() <= terrain.get(k).getChance()){
							blocks[i][j] = terrain.get(k).getBlock();
						}
					}
				}
			}
		}
		return blocks;
	}
	
	public Block[][] buildDecoration(double[][] noise){
		Block[][] blocks = new Block[noise.length][noise[0].length];
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				for(int k = 0; k < decoration.size(); k++){
					if(noise[i][j] >= decoration.get(k).getStart() && noise[i][j] <= decoration.get(k).getEnd()){
						if(Main.random.nextDouble() <= decoration.get(k).getChance()){
							blocks[i][j] = decoration.get(k).getBlock();
						}
					}
				}
			}
		}
		return blocks;
	}

}

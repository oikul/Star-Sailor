package starSailor;

import java.awt.Color;

public class Biome {
	
	//very cold biomes
	public static final Biome barren_waste = new Biome("barrren waste", Color.white, Block.rock, 0.0, 1.0);
	public static final Biome frosty_desert = new Biome("frosty desert", Color.white, Block.sand, 0.0, 1.0);
	public static final Biome frozen_lakes = new Biome("frozen lakes", Color.white, Block.snow, 0.0, 1.0, Block.ice, 0.3, 0.5);
	public static final Biome ice_plains = new Biome("ice plains", Color.white, Block.ice, 0.0, 1.0);
	public static final Biome ice_bergs = new Biome("ice bergs", Color.white, Block.water_ocean, 0.0, 1.0, Block.ice, 0.3, 0.5);
	
	//cold biomes
	public static final Biome tundra = new Biome("tundra", Color.lightGray, Block.grass_tundra, 0.0, 1.0);
	public static final Biome snow_wasteland = new Biome("snowy wasteland", Color.white, Block.snow, 0.0, 1.0);
	public static final Biome snowy_forest = new Biome("snowy forest", Color.white, Block.grass_snowy, 0.0, 1.0);
	public static final Biome steppe = new Biome("steppe", Color.lightGray, Block.grass_steppe, 0.0, 1.0);
	public static final Biome ocean = new Biome("ocean", Color.blue, Block.water_ocean, 0.0, 1.0);
	
	//medium biomes
	public static final Biome canyon = new Biome("canyon", Color.orange, Block.clay, 0.0, 0.1);
	public static final Biome plains = new Biome("plains", Color.green, Block.grass_plains, 0.0, 1.0);
	public static final Biome forest = new Biome("forest", Color.green, Block.grass_forest, 0.0, 1.0, Block.water_river, 0.45, 1.0, Block.tree_oak, 0, 0.35, 0.4);
	public static final Biome mountains = new Biome("mountains", Color.gray, Block.stone, 0.0, 1.0);
	public static final Biome islands = new Biome("islands", Color.blue, Block.water_ocean, 0.0, 1.0, Block.sand, 0.4, 0.6, Block.tree_palm, 0.45, 0.55, 0.3);
	
	//hot biomes
	public static final Biome desert = new Biome("desert", Color.yellow, Block.sand, 0.0, 1.0);
	public static final Biome dry_forest = new Biome("dry forest", Color.green, Block.grass_forest, 0.0, 1.0);
	public static final Biome alien_jungle = new Biome("alien jungle", Color.cyan, Block.grass_alien, 0.0, 1.0);
	public static final Biome jungle = new Biome("jungle", Color.green, Block.grass_jungle, 0.0, 1.0, Block.tree_rubber, 0.4, 0.8, 0.4);
	public static final Biome rainforest = new Biome("rainforest", Color.green, Block.grass_jungle, 0.0, 1.0);
	
	//very hot biomes
	//public static final Biome lava_ocean = new Biome("lava ocean", Block.lava, 0.0, 1.0);
	//public static final Biome lava_islands = new Biome("lava islands", Block.lava, 0.0, 1.0, Block.stone, 0.3, 0.7);
	//public static final Biome lava_lakes = new Biome("lava lakes", Block.stone, 0.0, 1.0, Block.lava, 0.3, 0.7);
	public static final Biome rocky_desert = new Biome("rocky desert", Color.orange, Block.obsidian, 0.0, 1.0);
	//public static final Biome volcanic_islands = new Biome("volcanic islands", Block.water, 0.0, 1.0, Block.stone, 0.8, 1.0, Block.lava, 0.95, 1.0, 1.0);
	
	
	private String name;
	private Color color;
	private Block main, secondary, deco;
	private double mainStart, mainEnd, secondaryStart = -1, secondaryEnd = -1, decoStart = -1, decoEnd = -1, decoChance = 0;
	
	public Biome(String name, Color color, Block main, double mainStart, double mainEnd){
		this.name = name;
		this.color = color;
		setMain(main, mainStart, mainEnd);
	}
	
	public Biome(String name, Color color, Block main, double mainStart, double mainEnd, Block secondary, double secondaryStart, double secondaryEnd){
		this.name = name;
		this.color = color;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
	}
	
	public Biome(String name, Color color, Block main, double mainStart, double mainEnd, Block deco, double decoStart, double decoEnd, double decoChance){
		this.name = name;
		this.color = color;
		setMain(main, mainStart, mainEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public Biome(String name, Color color, Block main, double mainStart, double mainEnd, Block secondary, double secondaryStart, double secondaryEnd, Block deco, double decoStart, double decoEnd, double decoChance){
		this.name = name;
		this.color = color;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setMain(Block main, double start, double end){
		this.main = main;
		this.mainStart = start;
		this.mainEnd = end;
	}
	
	public Block getMainBlock(){
		return main;
	}
	
	public double getMainStart(){
		return mainStart;
	}
	
	public double getMainEnd(){
		return mainEnd;
	}
	
	public void setSecondary(Block secondary, double start, double end){
		this.secondary = secondary;
		this.secondaryStart = start;
		this.secondaryEnd = end;
	}
	
	public Block getSecondaryBlock(){
		return secondary;
	}
	
	public double getSecondaryStart(){
		return secondaryStart;
	}
	
	public double getSecondaryEnd(){
		return secondaryEnd;
	}
	
	public void setDeco(Block deco, double start, double end, double decoChance){
		this.deco = deco;
		this.decoStart = start;
		this.decoEnd = end;
		this.decoChance = decoChance;
	}
	
	public Block getDecoBlock(){
		return deco;
	}
	
	public double getDecoStart(){
		return decoStart;
	}
	
	public double getDecoEnd(){
		return decoEnd;
	}
	
	public double getDecoChance(){
		return decoChance;
	}
	
	public Block[][] buildTerrain(double[][] noise){
		Block[][] blocks = new Block[noise.length][noise[0].length];
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				if(noise[i][j] >= mainStart && noise[i][j] <= mainEnd){
					blocks[i][j] = main;
				}
				if(noise[i][j] >= secondaryStart && noise[i][j] <= secondaryEnd){
					blocks[i][j] = secondary;
				}
			}
		}
		return blocks;
	}
	
	public Block[][] buildDecoration(double[][] noise){
		Block[][] blocks = new Block[noise.length][noise[0].length];
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				if(noise[i][j] >= decoStart && noise[i][j] <= decoEnd){
					if(Main.random.nextDouble() < decoChance){
						blocks[i][j] = deco;
					}
				}
			}
		}
		return blocks;
	}

}

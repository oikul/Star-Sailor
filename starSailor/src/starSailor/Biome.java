package starSailor;

public class Biome {
	
	//very cold biomes
	//public static final Biome ice_plains = new Biome("ice plains", Block.ice, 0.0f, 1.0f);
	
	//cold biomes
	//public static final Biome tundra = new Biome("tundra", Block.grass_tundra, 0.0f, 1.0f);
	
	//medium biomes
	public static final Biome forest = new Biome("forest", Block.grass_forest, 0.0, 1.0, Block.water, 0.45, 1.0, Block.tree_oak, 0, 0.35, 0.4);
	
	//hot biomes
	//public static final Biome jungle = new Biome("jungle", Block.grass_jungle, 0.0f, 1.0f, Block.tree_jungle, 0.4f, 0.8f, 0.4f);
	
	//very hot biomes
	//public static final Biome lava_ocean = new Biome("lava ocean", Block.lava, 0.0f, 1.0f);
	
	
	private String name;
	private Block main, secondary, deco;
	private double mainStart, mainEnd, secondaryStart = -1, secondaryEnd = -1, decoStart = -1, decoEnd = -1, decoChance = 0;
	
	public Biome(String name, Block main, double mainStart, double mainEnd){
		this.name = name;
		setMain(main, mainStart, mainEnd);
	}
	
	public Biome(String name, Block main, double mainStart, double mainEnd, Block secondary, double secondaryStart, double secondaryEnd){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
	}
	
	public Biome(String name, Block main, double mainStart, double mainEnd, Block deco, double decoStart, double decoEnd, double decoChance){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public Biome(String name, Block main, double mainStart, double mainEnd, Block secondary, double secondaryStart, double secondaryEnd, Block deco, double decoStart, double decoEnd, double decoChance){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public String getName(){
		return name;
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

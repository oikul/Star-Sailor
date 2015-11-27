package starSailor;

public class Biome {
	
	//very cold biomes
	public static final Biome ice_plains = new Biome("ice plains", Block.ice, 0.0f, 1.0f);
	
	//cold biomes
	public static final Biome tundra = new Biome("tundra", Block.grass_tundra, 0.0f, 1.0f);
	
	//medium biomes
	public static final Biome forest = new Biome("forest", Block.grass_forest, 0.0f, 1.0f, Block.tree_oak, 0.4f, 0.8f, 0.4f);
	
	//hot biomes
	public static final Biome jungle = new Biome("jungle", Block.grass_jungle, 0.0f, 1.0f, Block.tree_jungle, 0.4f, 0.8f, 0.4f);
	
	//very hot biomes
	public static final Biome lava_ocean = new Biome("lava ocean", Block.lava, 0.0f, 1.0f);
	
	
	private String name;
	private Block main, secondary, deco;
	private float mainStart, mainEnd, secondaryStart = -1, secondaryEnd = -1, decoStart = -1, decoEnd = -1, decoChance = 0;
	
	public Biome(String name, Block main, float mainStart, float mainEnd){
		this.name = name;
		setMain(main, mainStart, mainEnd);
	}
	
	public Biome(String name, Block main, float mainStart, float mainEnd, Block secondary, float secondaryStart, float secondaryEnd){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
	}
	
	public Biome(String name, Block main, float mainStart, float mainEnd, Block deco, float decoStart, float decoEnd, float decoChance){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public Biome(String name, Block main, float mainStart, float mainEnd, Block secondary, float secondaryStart, float secondaryEnd, Block deco, float decoStart, float decoEnd, float decoChance){
		this.name = name;
		setMain(main, mainStart, mainEnd);
		setSecondary(secondary, secondaryStart, secondaryEnd);
		setDeco(deco, decoStart, decoEnd, decoChance);
	}
	
	public String getName(){
		return name;
	}
	
	public void setMain(Block main, float start, float end){
		this.main = main;
		this.mainStart = start;
		this.mainEnd = end;
	}
	
	public Block getMainBlock(){
		return main;
	}
	
	public float getMainStart(){
		return mainStart;
	}
	
	public float getMainEnd(){
		return mainEnd;
	}
	
	public void setSecondary(Block secondary, float start, float end){
		this.secondary = secondary;
		this.secondaryStart = start;
		this.secondaryEnd = end;
	}
	
	public Block getSecondaryBlock(){
		return secondary;
	}
	
	public float getSecondaryStart(){
		return secondaryStart;
	}
	
	public float getSecondaryEnd(){
		return secondaryEnd;
	}
	
	public void setDeco(Block deco, float start, float end, float decoChance){
		this.deco = deco;
		this.decoStart = start;
		this.decoEnd = end;
		this.decoChance = decoChance;
	}
	
	public Block getDecoBlock(){
		return deco;
	}
	
	public float getDecoStart(){
		return decoStart;
	}
	
	public float getDecoEnd(){
		return decoEnd;
	}
	
	public float getDecoChance(){
		return decoChance;
	}
	
	public Block[][] buildTerrain(float[][] noise){
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
	
	public Block[][] buildDecoration(float[][] noise){
		Block[][] blocks = new Block[noise.length][noise[0].length];
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				if(noise[i][j] >= decoStart && noise[i][j] <= decoEnd){
					if(Main.random.nextFloat() < decoChance){
						blocks[i][j] = deco;
					}
				}
			}
		}
		return blocks;
	}

}

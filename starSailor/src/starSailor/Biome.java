package starSailor;

public class Biome {
	
	public static final Biome forest = new Biome("forest", Block.grass_forest, 0.0f, 1.0f);
	
	private String name;
	private Block main;
	private float mainStart, mainEnd;
	
	public Biome(String name, Block main, float mainStart, float mainEnd){
		this.name = name;
		setMain(main, mainStart, mainEnd);
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

}

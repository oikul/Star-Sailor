package starSailor;

public class BlockLiquid extends Block {
	
	private long time;

	public BlockLiquid(String imagePath, boolean isSolid) {
		super(imagePath, isSolid);
		time = System.currentTimeMillis();
	}
	
	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
			
			time = newTime;
		}
		
	}

}

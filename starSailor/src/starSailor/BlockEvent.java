package starSailor;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BlockEvent extends Block{
	
	private int x, y, xDif, yDif;

	public BlockEvent(String imagePath, boolean isSolid, int x, int y) {
		super(imagePath, isSolid);
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		if(new Rectangle(x + xDif, y + yDif, 16, 16).intersects(new Rectangle(Main.width/2 - 8, Main.height/2 - 8, 16, 16))){
			Main.state = Main.State.DUNGEON;
		}
	}
	
	public void panUp(){
		yDif ++;
	}
	
	public void panLeft(){
		xDif ++;
	}
	
	public void panDown(){
		yDif --;
	}
	
	public void panRight(){
		xDif --;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, x + xDif, y + yDif, null);
	}

}

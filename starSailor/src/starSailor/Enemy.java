package starSailor;

import java.awt.Graphics;
import java.awt.Image;

public class Enemy {
	
	private Image[][] sprites;
	private int iIndex, jIndex, x, y;
	private long time;
	
	public Enemy(String path, int size){
		sprites = ResourceLoader.getPlayerSprites(path, size, size);
		time = System.currentTimeMillis();
	}
	
	private void setDirection(int dir){
		iIndex = dir;
	}
	
	private void incrementJ(){
		if(System.currentTimeMillis() >time + 300){
			if(jIndex < 2){
				jIndex ++;
			}else{
				jIndex = 0;
			}
			time = System.currentTimeMillis();
		}
	}
	
	public void update(){
		if(x < Main.width/2){
			x ++;
			setDirection(3);
		}else{
			x --;
			setDirection(2);
		}
		if(y < Main.height/2){
			y ++;
			setDirection(1);
		}else{
			y --;
			setDirection(0);
		}
		incrementJ();
	}
	
	public void draw(Graphics g){
		g.drawImage(sprites[iIndex][jIndex], x, y, null);
	}

}

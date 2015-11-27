package starSailor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player {
	
	private BufferedImage spriteSheet;
	private Image[][] playerImages;
	private int currentIFrame = 0, currentJFrame = 0;
	private long time;
	
	public enum Direction{
		UP, LEFT, DOWN, RIGHT;
	}
	
	public Player(String path){
		spriteSheet = (BufferedImage) ResourceLoader.getImage(path);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				playerImages[i][j] = spriteSheet.getSubimage(j * 16, i * 16, 16, 16);
			}
		}
		time = System.currentTimeMillis();
	}
	
	public void setDirection(Direction direction){
		currentIFrame = 0;
		switch (direction){
		case UP:
			currentJFrame = 0;
			break;
		case LEFT:
			currentJFrame = 1;
			break;
		case DOWN:
			currentJFrame = 2;
			break;
		case RIGHT:
			currentJFrame = 3;
			break;
		}
	}
	
	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
			if(currentIFrame < playerImages.length){
				currentIFrame++;
			}else{
				currentIFrame = 0;
			}
			time = newTime;
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 8, Main.height /2 - 8, null);
	}

}

package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player {
	
	private BufferedImage spriteSheet;
	private Image playerSprites;
	private Image[][] playerImages;
	private int currentIFrame = 0, currentJFrame = 0;
	private long time;
	
	public static enum Direction{
		UP, LEFT, DOWN, RIGHT;
	}
	
	public Player(String path){
		playerImages = new Image[3][4];
		playerSprites = ResourceLoader.getImage(path);
		spriteSheet = new BufferedImage(playerSprites.getWidth(null), playerSprites.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = spriteSheet.createGraphics();
	    bGr.drawImage(playerSprites, 0, 0, null);
	    bGr.dispose();
		for(int i = 0; i <= 2; i++){
			for(int j = 0; j <= 3; j++){
				playerImages[i][j] = spriteSheet.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
		time = System.currentTimeMillis();
	}
	
	public void setDirection(Direction direction){
		switch (direction){
		case UP:
			currentJFrame = 1;
			break;
		case LEFT:
			currentJFrame = 3;
			break;
		case DOWN:
			currentJFrame = 0;
			break;
		case RIGHT:
			currentJFrame = 2;
			break;
		}
	}

	public void stop(){
		currentIFrame = 0;
	}

	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 500){
			if(currentIFrame < playerImages.length - 1){
				currentIFrame++;
			}else{
				currentIFrame = 0;
			}
			time = newTime;
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
	}

}

package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player {

	private BufferedImage spriteSheet;
	private Image playerSprites, shipSprites;
	private Image[][] playerImages, shipImages;
	private int currentIFrame = 0, currentJFrame = 0;
	private boolean isShip = true;
	private long time;

	public static enum Direction{
		UP, LEFT, DOWN, RIGHT;
	}

	public Player(String playerPath, String shipPath){
		playerImages = new Image[3][4];
		shipImages = new Image[3][4];
		playerSprites = ResourceLoader.getImage(playerPath);
		shipSprites = ResourceLoader.getImage(shipPath);
		spriteSheet = new BufferedImage(playerSprites.getWidth(null), playerSprites.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = spriteSheet.createGraphics();
		bGr.drawImage(playerSprites, 0, 0, null);
		for(int i = 0; i <= 2; i++){
			for(int j = 0; j <= 3; j++){
				playerImages[i][j] = spriteSheet.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
		spriteSheet = new BufferedImage(shipSprites.getWidth(null), shipSprites.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		bGr = spriteSheet.createGraphics();
		bGr.drawImage(shipSprites, 0, 0, null);
		for(int i = 0; i <= 2; i++){
			for(int j = 0; j <= 3; j++){
				shipImages[i][j] = spriteSheet.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
		time = System.currentTimeMillis();
	}
	
	public boolean isShip(){
		return isShip;
	}
	
	public void setIsShip(boolean isShip){
		this.isShip = isShip;
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
		switch (Main.state){
		case GALACTIC:
			break;
		case SOLAR:
			break;
		case PLANETRY:
			break;
		case SURFACE:
			long newTime = System.currentTimeMillis();
			if(newTime >= time + 300){
				if(currentIFrame < playerImages.length - 1){
					currentIFrame++;
				}else{
					currentIFrame = 1;
				}
				time = newTime;
			}
			break;
		default:
			break;
		}

	}

	public void draw(Graphics g){
		switch (Main.state){
		case GALACTIC:
			//g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case SOLAR:
			//g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case PLANETRY:
			//g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case SURFACE:
			if(isShip){
				g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			}else{
				g.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			}
			break;
		default:
			break;
		}

	}

}

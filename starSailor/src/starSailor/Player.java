package starSailor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Player {
	
	public static enum Direction{
		UP, LEFT, DOWN, RIGHT;
	}
	
	private Image[][] playerImages, shipImages;
	private int currentIFrame = 0, currentJFrame = 0;
	private static Point lastLocation;
	private int lastDir;
	private static boolean isShip = true;
	private long time;

	public Player(String playerPath, String shipPath){
		playerImages = ResourceLoader.getSprites(playerPath, 32);
		shipImages = ResourceLoader.getSprites(shipPath, 32);
		time = System.currentTimeMillis();
	}
	
	public static boolean isShip(){
		return isShip;
	}
	
	public static void setIsShip(boolean isShip){
		Player.isShip = isShip;
	}

	public void setDirection(Direction direction){
		switch (direction){
		case UP:
			if(isShip){
				lastDir = 1;
			}
			currentJFrame = 1;
			break;
		case LEFT:
			if(isShip){
				lastDir = 3;
			}
			currentJFrame = 3;
			break;
		case DOWN:
			if(isShip){
				lastDir = 0;
			}
			currentJFrame = 0;
			break;
		case RIGHT:
			if(isShip){
				lastDir = 2;
			}
			currentJFrame = 2;
			break;
		}
	}

	public void stop(){
		currentIFrame = 0;
	}
	
	public static void pan(int x, int y){
		lastLocation.translate(x, y);
	}

	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 300){
			if(currentIFrame < playerImages.length - 1){
				currentIFrame++;
			}else{
				currentIFrame = 1;
			}
			time = newTime;
		}
	}

	public void draw(Graphics g){
		switch (Main.state){
		case GALACTIC: 
			g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case SOLAR:
			g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case PLANETRY:
			g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		case SURFACE:
			if(isShip){
				g.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
				lastLocation = new Point(Main.width/2 - 16, Main.height /2 - 16);
			}else{
				g.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
				g.drawImage(shipImages[0][lastDir], lastLocation.x, lastLocation.y, null);
			}
			break;
		case SHIP:
			g.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		default:
			break;
		}

	}

}

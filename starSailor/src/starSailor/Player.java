package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Player {
	
	private Image[][] playerImages, shipImages;
	private int currentIFrame = 0, currentJFrame = 3;
	private static Point2D.Double lastLocation;
	private int lastDir;
	private static boolean isShip = true;
	private static boolean isMoving = false;
	private long time;
	private double rotation;

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
	
	public void setMoving(){
		isMoving = true;
	}

	public void stop(){
		isMoving = false;
		if(isShip && currentIFrame != 4){
			currentIFrame = 3;
		}else if(!isShip && !isMoving){
			currentIFrame = 0;
		}
	}
	
	public static void pan(double x, double y){
		if(lastLocation != null){
			lastLocation.setLocation(lastLocation.getX() + x, lastLocation.getY() + y);
		}
	}
	
	public void calculateRotation(Point mouseCoords){
			double xDif = Main.width/2 - mouseCoords.x;
			double yDif = Main.height/2 - mouseCoords.y;
			rotation = Math.atan(yDif/xDif);
			if(xDif < 0){
				rotation += Math.PI;
			}
	}

	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 300){
			switch (currentIFrame){
			case 0:
				if(isMoving){currentIFrame = 1;}
				else{currentIFrame = 0;}
				break;
			case 1:
				currentIFrame = 2;
				break;
			case 2:
				currentIFrame = 1;
				break;
			case 3:
				if(isMoving){
					currentIFrame = 1;
				}
				else{
					currentIFrame = 4;
				}
				break;
			case 4:
				if(isMoving){
					currentIFrame = 2;
				}
				else{
					currentIFrame = 3;
				}
				break;
			}
			time = newTime;
		}
	}

	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAt;
		AffineTransform at;
		switch (Main.state){
		case SURFACE:
			if(isShip){
				saveAt = new AffineTransform();
				at = new AffineTransform();
				saveAt = g2d.getTransform();
				at.rotate(rotation, Main.width/2, Main.height/2);
				g2d.setTransform(at);
				g2d.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
				g2d.setTransform(saveAt);
				lastLocation = new Point2D.Double(Main.width/2 - 16, Main.height /2 - 16);
			}else{
				if(currentIFrame > 2){
					currentIFrame = 1;
				}
				g2d.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
				g2d.drawImage(shipImages[0][lastDir],(int) lastLocation.x,(int) lastLocation.y, null);
			}
			break;
		case SHIP:
			if(currentIFrame > 2){
				currentIFrame = 0;
			}
			g2d.drawImage(playerImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			break;
		default:
			saveAt = new AffineTransform();
			at = new AffineTransform();
			saveAt = g2d.getTransform();
			at.rotate(rotation, Main.width/2, Main.height/2);
			g2d.setTransform(at);
			g2d.drawImage(shipImages[currentIFrame][currentJFrame], Main.width/2 - 16, Main.height /2 - 16, null);
			g2d.setTransform(saveAt);
			break;
		}

	}

}

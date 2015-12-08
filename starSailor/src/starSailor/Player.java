package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Player {
	
	private Image[][] playerImages;
	private Image[] shipImages;
	private int iIndex = 0, jIndex = 0, shipIndex = 0;
	private static Point2D.Double lastLocation;
	private static boolean isShip = true, isMoving = false;
	public static boolean canLeave = true;
	private long time;
	private double rotation, lastRotation;
	
	public static int accuracySTAT,speedSTAT,HPSTAT;
	public static int killCount;

	public Player(String playerPath, String shipPath){
		accuracySTAT = 1;
		speedSTAT = 1;
		HPSTAT = 100000;
		killCount = 0;
		playerImages = ResourceLoader.getPlayerSprites(playerPath, 16, 16);
		shipImages = ResourceLoader.getBlockSprites(shipPath, 32, 32);
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
	
	public void setDirection(int dir){
		iIndex = dir;
	}

	public void stop(){
		isMoving = false;
		if(isShip && shipIndex != 3 && shipIndex != 4){
			shipIndex = 3;
		}else{
			jIndex = 0;
		}
	}
	
	public static void pan(double x, double y){
		if(lastLocation != null){
			lastLocation.setLocation(lastLocation.getX() + x, lastLocation.getY() + y);
		}
	}
	
	public void calculateRotation(Point mouseCoords){
			
				rotation = SpaceBattle.getAngle(new Point2D.Double(Main.width/2, Main.height/2),new Point2D.Double(mouseCoords.x,mouseCoords.y));
	}

	public void update(Point p){
		long newTime = System.currentTimeMillis();
		if(isShip){
			calculateRotation(p);
		}
		if(newTime >= time + 300){
			if(isMoving){
				if(isShip){
					if(shipIndex < 2){
						shipIndex ++;
					}else{
						shipIndex = 1;
					}
				}else{
					if(jIndex == 0){
						jIndex = 2;
					}else if(jIndex == 1){
						jIndex = 0;
					}else if(jIndex == 2){
						jIndex = 1;
					}
					lastRotation = rotation;
				}
			}else{
				if(isShip){
					if(shipIndex < 4){
						shipIndex ++;
					}else{
						shipIndex = 3;
					}
				}else{
					jIndex = 0;
					lastRotation = rotation;
				}
			}
			time = newTime;
		}
	}

	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAt;
		AffineTransform at;
		switch (Main.state){
		case SHIP:
			g2d.drawImage(playerImages[iIndex][jIndex], Main.width/2 - 8, Main.height /2 - 8, null);
			break;
		default:
			if(isShip){
				at = new AffineTransform();
				saveAt = g2d.getTransform();
				at.rotate(rotation, Main.width/2, Main.height/2);
				g2d.setTransform(at);
				g2d.drawImage(shipImages[shipIndex], Main.width/2 - 16, Main.height /2 - 16, null);
				g2d.setTransform(saveAt);
				lastLocation = new Point2D.Double(Main.width/2 - 16, Main.height /2 - 16);
			}else{
				g2d.drawImage(playerImages[iIndex][jIndex], Main.width/2 - 8, Main.height /2 - 8, null);
				at = new AffineTransform();
				saveAt = g2d.getTransform();
				at.rotate(lastRotation, lastLocation.x+16, lastLocation.y+16);
				g2d.setTransform(at);
				g2d.drawImage(shipImages[0],(int) lastLocation.x,(int) lastLocation.y, null);
				g2d.setTransform(saveAt);
			}
			break;
		}

	}

}

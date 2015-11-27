package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Galaxy {
	
	private Star[] galaxy;
	private int xDif = 0, yDif = 0;
	private int selectedStar = 0;
	
	public Galaxy(int numOfStars){
		galaxy = new Star[numOfStars];
		for(int i = 0; i < numOfStars; i++){
			galaxy[i] = new Star(Main.random.nextDouble() * 8192, Main.random.nextDouble() * 360, Main.random.nextInt(40) + 30);
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
	
	public void update(){
		switch (Main.state){
		case GALACTIC:
			for(int i = 0; i < galaxy.length; i++){
				galaxy[i].update();
			}
			break;
		case PLANETRY:
			break;
		case SOLAR:
			galaxy[selectedStar].update();
			break;
		case SURFACE:
			break;
		default:
			break;
		}
		
	}
	
	public void draw(Graphics g){
		switch (Main.state){
		case GALACTIC:
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			at.translate(xDif, yDif);
			g2d.setTransform(at);
			for(int i = 0; i < galaxy.length; i++){
				galaxy[i].draw(g);
			}
			break;
		case PLANETRY:
			break;
		case SOLAR:
			galaxy[selectedStar].draw(g);
			break;
		case SURFACE:
			break;
		default:
			break;
		}
		
	}

}

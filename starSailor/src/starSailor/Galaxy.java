package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class Galaxy {
	
	private Star[] galaxy;
	private int xDif = 0, yDif = 0;
	private int selectedStar = 0;
	private double zoom = 1.0, xtrans, ytrans;
	private AffineTransform transform;
	
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
	
	public void moveSurface(int dir){
		galaxy[selectedStar].moveSurface(dir);
	}
	
	public void zoomIn(){
		if(zoom < 3){
			xDif = 0;
			yDif = 0;
			zoom += 0.3;
		}else{
			Main.state = Main.State.SOLAR;
		}
	}
	
	public void zoomOut(){
		if(zoom > 0.7){
			xDif = 0;
			yDif = 0;
			zoom -= 0.3;
		}else{
			
		}
	}
	
	public void zoom(boolean in){
		if(Main.state == Main.State.GALACTIC){
			if(in){
				zoomIn();
			}else{
				zoomOut();
			}
		}else{
			galaxy[selectedStar].zoom(in);
		}
	}
	
	private void getXTrans(){
		double x = galaxy[selectedStar].getX() + xDif;
		if(x > Main.width/(zoom*2)){
				xtrans = -(x - Main.width/(zoom*2));
			}else{
				xtrans = Main.width/(zoom*2) - x;
			}
	}
	
	private void getYTrans(){
		double y = galaxy[selectedStar].getY() + yDif;
		if(y > Main.height/(zoom*2)){
				ytrans = -(y - Main.height/(zoom*2));
			}else{
				ytrans = Main.height/(zoom*2) - y;
			}
	}
	
	public void checkForClick(int xCoord, int yCoord){
		int x = (int) (xCoord - 8), y = (int) (yCoord - 8);
		if(Main.state == Main.State.GALACTIC){
			Point2D point = new Point2D.Double(x, y);
			transform.transform(point, point);
			for(int i = 0; i < galaxy.length; i ++){
				if(galaxy[i].getRect().intersects(new Rectangle((int) (point.getX()), (int) (point.getY()), 16, 16))){
					galaxy[i].setSelected(true);
					xDif = 0;
					yDif = 0;
					selectedStar = i;
				}else{
					galaxy[i].setSelected(false);
				}
			}
			galaxy[selectedStar].setSelected(true);
		}else{
			galaxy[selectedStar].checkForClick(x, y);
		}
	}
	
	public void update(){
		switch (Main.state){
		case GALACTIC:
			for(int i = 0; i < galaxy.length; i++){
				galaxy[i].update();
			}
			break;
		case PLANETRY:
			galaxy[selectedStar].update();
			break;
		case SOLAR:
			galaxy[selectedStar].update();
			break;
		case SURFACE:
			galaxy[selectedStar].update();
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
			at.scale(zoom, zoom);
			getXTrans();
			getYTrans();
			at.translate(xtrans, ytrans);
			try {
				transform = at.createInverse();
			} catch (NoninvertibleTransformException e) {
				e.printStackTrace();
			}
			g2d.setTransform(at);
			for(int i = 0; i < galaxy.length; i++){
				galaxy[i].draw(g);
			}
			break;
		case PLANETRY:
			galaxy[selectedStar].draw(g);
			break;
		case SOLAR:
			galaxy[selectedStar].draw(g);
			break;
		case SURFACE:
			galaxy[selectedStar].draw(g);
			break;
		default:
			break;
		}
		
	}

}

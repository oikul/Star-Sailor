package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class SolarSystem {
	
	private Planet[] planets;
	private int size;
	private double zoom = 1.0, xtrans, ytrans;
	private Color color;
	private int selectedPlanet = 0;
	private AffineTransform transform;
	
	public SolarSystem(int size, int numOfPlanets, double min, double max, Color color){
		this.size = size;
		planets = new Planet[numOfPlanets];
		for(int i = 0; i < numOfPlanets; i++){
			planets[i] = new Planet(Main.random.nextInt(16) + 4, (Main.random.nextDouble() * (max-min)) + min, (Main.random.nextDouble() * 360));
		}
		planets[selectedPlanet].setSelected(true);
		this.color = color;
	}
	
	public void checkForClick(int x, int y){
		if(Main.state == Main.State.SOLAR){
			Point2D point = new Point2D.Double(x - 8, y - 8);
			transform.transform(point, point);
			for(int i = 0; i < planets.length; i ++){
				if(planets[i].getRect().intersects(new Rectangle((int) (point.getX()), (int) (point.getY()), 16, 16))){
					planets[i].setSelected(true);
					selectedPlanet = i;
				}else{
					planets[i].setSelected(false);
				}
			}
			planets[selectedPlanet].setSelected(true);
		}
	}
	
	public void zoomIn(){
		if(zoom < 3){
			zoom += 0.3;
		}else{
			Main.state = Main.State.PLANETRY;
		}
	}
	
	public void zoomOut(){
		if(zoom > 0.7){
			zoom -= 0.3;
		}else{
			Main.state = Main.State.GALACTIC;
		}
	}
	
	public void zoom(boolean in){
		if(Main.state == Main.State.SOLAR){
			if(in){
				zoomIn();
			}else{
				zoomOut();
			}
		}else{
			planets[selectedPlanet].zoom(in);
		}
	}
	
	public void moveSurface(int dir){
		if(dir == 0){
			planets[selectedPlanet].panUp();
		}else if(dir == 1){
			planets[selectedPlanet].panLeft();
		}else if(dir == 2){
			planets[selectedPlanet].panDown();
		}else if(dir == 3){
			planets[selectedPlanet].panRight();
		}
	}
	
	private void getXTrans(){
		double x;
		if(selectedPlanet == -1){
			x = Main.width/2;
		}else{
			x = planets[selectedPlanet].getX();
		}
		if(x > Main.width/(zoom*2)){
				xtrans = -(x - Main.width/(zoom*2));
			}else{
				xtrans = Main.width/(zoom*2) - x;
			}
	}
	
	private void getYTrans(){
		double y;
		if(selectedPlanet == -1){
			y = Main.height/2;
		}else{
			y = planets[selectedPlanet].getY();
		}
		if(y > Main.height/(zoom*2)){
				ytrans = -(y - Main.height/(zoom*2));
			}else{
				ytrans = Main.height/(zoom*2) - y;
			}
	}
	
	public void update(){
		switch (Main.state){
		case GALACTIC:
			break;
		case PLANETRY:
			if(selectedPlanet >= 0){
				planets[selectedPlanet].update();
			}
			break;
		case SOLAR:
			for(int i = 0; i < planets.length; i++){
				planets[i].update();
			}
			break;
		case SURFACE:
			planets[selectedPlanet].update();
			break;
		default:
			break;
		}
	}
	
	public void draw(Graphics g){
		switch (Main.state){
		case GALACTIC:
			break;
		case PLANETRY:
			if(selectedPlanet >= 0){
				planets[selectedPlanet].draw(g);
			}
			break;
		case SOLAR:
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
			g2d.setColor(color);
			g2d.fillOval(Main.width/2 - size/2, Main.height/2 - size/2, size, size);
			for(int i = 0; i < planets.length; i++){
				planets[i].draw(g2d);
			}
			break;
		case SURFACE:
			planets[selectedPlanet].draw(g);
			break;
		default:
			break;
		}
		
	}

}

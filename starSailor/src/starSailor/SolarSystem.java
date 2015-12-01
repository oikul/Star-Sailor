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
	private double zoom = 1.0, xtrans, ytrans, xDif, yDif;
	private Color color;
	private int selectedPlanet = -1;
	private AffineTransform transform;
	
	public SolarSystem(int size, int numOfPlanets, double min, double max, Color color){
		this.size = size;
		planets = new Planet[numOfPlanets];
		for(int i = 0; i < numOfPlanets; i++){
			planets[i] = new Planet(Main.random.nextInt(13) + 8, (Main.random.nextDouble() * (max-min)) + min, (Main.random.nextDouble() * 360), false);
		}
		if(selectedPlanet != -1){
			planets[selectedPlanet].setSelected(true);
		}
		this.color = color;
	}
	
	public void checkForClick(int x, int y){
		if(Main.state == Main.State.SOLAR){
			Point2D point = new Point2D.Double(x - 8, y - 8);
			transform.transform(point, point);
			for(int i = 0; i < planets.length; i ++){
				if(planets[i].getRect().intersects(new Rectangle((int) (point.getX()), (int) (point.getY()), 16, 16))){
					planets[i].setSelected(true);
					xDif = 0;
					yDif = 0;
					selectedPlanet = i;
				}else{
					planets[i].setSelected(false);
				}
			}
			if(new Rectangle(Main.width/2 - size/2, Main.height/2 - size/2, size, size).contains(point.getX(), point.getY())){
				selectedPlanet = -1;
			}
		}
	}
	
	public void zoomIn(){
		if(zoom < 3){
			zoom += 0.3;
			xDif = 0;
			yDif = 0;
		}else{
			if(selectedPlanet != -1){
				Main.state = Main.State.PLANETRY;
			}
		}
	}
	
	public void zoomOut(){
		if(zoom > 0.7){
			zoom -= 0.3;
			xDif = 0;
			yDif = 0;
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
			if(selectedPlanet != -1){
				planets[selectedPlanet].zoom(in);
			}
		}
	}
	
	public void panUp(){
		if(Main.state == Main.State.SOLAR){
			yDif --;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panUp();
			}
		}
	}

	public void panLeft(){
		if(Main.state == Main.State.SOLAR){
			xDif --;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panLeft();
			};
		}
	}

	public void panDown(){
		if(Main.state == Main.State.SOLAR){
			yDif ++;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panDown();
			}
		}
	}

	public void panRight(){
		if(Main.state == Main.State.SOLAR){
			xDif ++;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panRight();
			}
		}
	}
	
	public void panUR(){
		if(Main.state == Main.State.SOLAR){
			xDif += 1/Main.root2;
			yDif -= 1/Main.root2;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panUR();
			}
		}
	}
	
	public void panUL(){
		if(Main.state == Main.State.SOLAR){
			xDif -= 1/Main.root2;
			yDif -= 1/Main.root2;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panUL();
			}
		}
	}
	
	public void panDR(){
		if(Main.state == Main.State.SOLAR){
			xDif += 1/Main.root2;
			yDif += 1/Main.root2;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panDR();
			}
		}
	}
	
	public void panDL(){
		if(Main.state == Main.State.SOLAR){
			xDif -= 1/Main.root2;
			yDif += 1/Main.root2;
		}else{
			if(selectedPlanet != -1){
				planets[selectedPlanet].panDL();
			}
		}
	}
	
	private void getXTrans(){
		double x;
		if(selectedPlanet == -1){
			x = Main.width/2 + xDif;
		}else{
			x = planets[selectedPlanet].getX() + xDif;
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
			y = Main.height/2 + yDif;
		}else{
			y = planets[selectedPlanet].getY() + yDif;
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
			AffineTransform saveAt = g2d.getTransform();
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
			if(selectedPlanet == -1){
				g2d.setColor(Color.cyan);
				g2d.drawRect(Main.width/2 - size/2, Main.height/2 - size/2, size, size);
			}
			g2d.setColor(color);
			g2d.fillOval(Main.width/2 - size/2, Main.height/2 - size/2, size, size);
			for(int i = 0; i < planets.length; i++){
				planets[i].draw(g2d);
			}
			g2d.setTransform(saveAt);
			break;
		case SURFACE:
			planets[selectedPlanet].draw(g);
			break;
		default:
			break;
		}
		
	}

}

package starSailor;

import java.awt.Color;
import java.awt.Graphics;

public class SolarSystem {
	
	private Planet[] planets;
	private int size;
	private Color color;
	
	public SolarSystem(int size, int numOfPlanets, double min, double max, Color color){
		this.size = size;
		planets = new Planet[numOfPlanets];
		for(int i = 0; i < numOfPlanets; i++){
			planets[i] = new Planet(Main.random.nextInt(16) + 4, Main.random.nextDouble() * (max-min) + min, Main.random.nextDouble() * 360);
		}
		this.color = color;
	}
	
	public void update(){
		switch (Main.state){
		case GALACTIC:
			break;
		case PLANETRY:
			break;
		case SOLAR:
			for(int i = 0; i < planets.length; i++){
				planets[i].update();
			}
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
			break;
		case PLANETRY:
			break;
		case SOLAR:
			g.setColor(color);
			g.fillOval(Main.width/2 - size/2, Main.height/2 - size/2, size, size);
			for(int i = 0; i < planets.length; i++){
				planets[i].draw(g);
			}
			break;
		case SURFACE:
			break;
		default:
			break;
		}
		
	}

}

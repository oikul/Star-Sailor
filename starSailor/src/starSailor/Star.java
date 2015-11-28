package starSailor;

import java.awt.Color;
import java.awt.Graphics;

public class Star {
	
	private double distance, angle;
	private int x, y, size;
	private Color color;
	private SolarSystem system;
	private boolean made = false;
	
	public Star(double distance, double angle, int size){
		this.distance = distance;
		this.angle = angle;
		this.size = size;
		chooseColor();
		calculateXAndY();
	}
	
	private void createSystem(){
		system = new SolarSystem(size, Main.random.nextInt(8) + 2, size/2, Main.height/2, color);
		made = true;
	}
	
	private void chooseColor(){
		if(size > 30 && size < 40){
			color = Color.red;
		}else if(size >= 40 && size < 50){
			color = new Color(215,102,0);
		}else if(size >= 50 && size < 60){
			color = Color.yellow;
		}else if(size >= 60 && size < 70){
			color = Color.white;
		}
	}
	
	private void calculateXAndY(){
		if(angle >= 0 && angle < 90){
			x = (int) (Main.width/2 + distance*Math.cos(angle));
			y = (int) (Main.height/2 - distance*Math.sin(angle));
		}else if(angle >= 90 && angle < 180){
			x = (int) (Main.width/2 - distance*Math.sin(angle - 90));
			y = (int) (Main.height/2 - distance*Math.cos(angle - 90));
		}else if(angle >= 180 && angle < 270){
			x = (int) (Main.width/2 - distance*Math.cos(angle - 180));
			y = (int) (Main.height/2 + distance*Math.sin(angle - 180));
		}else if(angle >= 270 && angle < 360){
			x = (int) (Main.width/2 + distance*Math.sin(angle - 270));
			y = (int) (Main.height/2 + distance*Math.cos(angle - 270));
		}
	}
	
	private void incrementAngle(){
		if(angle < 360){
			angle += 0.0005;
		}else{
			angle = 0;
		}
	}
	
	public void update(){
		switch (Main.state){
		case GALACTIC:
			incrementAngle();
			calculateXAndY();
			break;
		case PLANETRY:
			system.update();
			break;
		case SOLAR:
			if(!made){
				createSystem();
			}
			system.update();
			break;
		case SURFACE:
			system.update();
			break;
		default:
			break;
		}
		
	}
	
	public void draw(Graphics g){
		switch (Main.state){
		case GALACTIC:
			g.setColor(color);
			g.fillOval(x - (size/30), y - (size/30), size / 15, size / 15);
			break;
		case SOLAR:
			system.draw(g);
			break;
		case PLANETRY:
			system.draw(g);
			break;
		case SURFACE:
			system.draw(g);
			break;
		default:
			break;
		}
		
	}

}

package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Planet {
	
	private int size, x, y, xDif, yDif;
	private double distance, temperature, precipitation, angle, zoom = 1.0;
	private NoiseGenerator generator;
	private double[][] noise;
	private Block[][] terrain, decoration;
	private Biome biome;
	private Color color;
	private boolean selected = false;
	
	public Planet(int size, double distance, double angle){
		this.size = size;
		this.distance = distance;
		this.angle = angle;
		generator = new NoiseGenerator(size * 10, size * 10, 4, 5);
		noise = generator.getNoise();
		calculateTemperature();
		calculatePrecipitation();
		calculateBiome();
		terrain = biome.buildTerrain(noise);
		decoration = biome.buildDecoration(noise);
		color = biome.getColor();
	}
	
	private void calculateTemperature(){
		temperature = Main.random.nextDouble();
	}
	
	private void calculatePrecipitation(){
		precipitation = Main.random.nextDouble();
	}
	
	private void calculateBiome(){
		if(temperature >= 0 && temperature < 0.2){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.polar_desert;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.ice_spikes;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.frozen_lakes;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_sheet;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.ice_bergs;
			}
		}else if(temperature >= 0.2 && temperature < 0.4){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.tundra;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.mountain;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.taiga;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.mountain_forest;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.ocean;
			}
		}else if(temperature >= 0.4 && temperature < 0.6){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.steppe;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.plains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.lakes;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.islands;
			}
		}else if(temperature >= 0.6 && temperature < 0.8){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.desert_plains;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.canyon;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.savannah;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.jungle;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.rainforest;
			}
		}else if(temperature >= 0.8 && temperature <= 1){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.volcanic_mountains;
			}
		}
	}
	
	public int getSize(){
		return size;
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
	
	private void incrementAngle(){
		if(angle < 360){
			angle += 0.001;
		}else{
			angle = 0;
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
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, size, size);
	}
	
	public void zoomIn(){
		if(zoom < 3){
			xDif = 0;
			yDif = 0;
			zoom += 0.3;
		}else{
			Main.state = Main.State.SURFACE;
		}
	}
	
	public void zoomOut(){
		if(zoom > 0.7){
			xDif = 0;
			yDif = 0;
			zoom -= 0.3;
		}else{
			Main.state = Main.State.SOLAR;
		}
	}
	
	public void zoom(boolean in){
		if(Main.state == Main.State.PLANETRY){
			if(in){
				zoomIn();
			}else{
				zoomOut();
			}
		}else{
			
		}
	}
	
	public void update(){
		switch(Main.state){
		case PLANETRY:
			break;
		case SOLAR:
			incrementAngle();
			calculateXAndY();
			break;
		case SURFACE:
			BlockAnimated.update();
			break;
		default:
			break;
		}
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		switch(Main.state){
		case SOLAR:
			g.setColor(color);
			g.fillOval(x - size/2, y - size/2, size, size);
			break;
		case PLANETRY:
			g.setColor(color);
			g.fillOval((Main.width/2) - size * 10, (Main.height/2) - size * 10, size*20, size*20);
			break;
		case SURFACE:
			for(int i = 0; i < noise.length; i++){
				for(int j = 0; j < noise[0].length; j++){
					terrain[i][j].draw(g2d, i * 16 + xDif, j * 16 + yDif);
				}
			}
			for(int i = 0; i < noise.length; i++){
				for(int j = 0; j < noise[0].length; j++){
					if(decoration[i][j] != null){
						decoration[i][j].draw(g2d, i * 16 + xDif, j * 16 + yDif);
					}
				}
			}
			break;
		default:
			break;
		}
		
	}

}

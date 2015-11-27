package starSailor;

import java.awt.Color;
import java.awt.Graphics;

public class Planet {
	
	private int size, x, y, xDif, yDif;
	private double distance, temperature, precipitation, angle;
	private NoiseGenerator generator;
	private double[][] noise;
	private Block[][] terrain;
	private Block[][] decoration;
	private Biome biome;
	
	public Planet(int size, double distance, double angle){
		this.size = size;
		this.distance = distance;
		generator = new NoiseGenerator(size * 10, size * 10, 3, 4);
		noise = generator.getNoise();
		calculateTemperature();
		calculatePrecipitation();
		calculateBiome();
		biome = Biome.forest;
		terrain = biome.buildTerrain(noise);
		decoration = biome.buildDecoration(noise);
	}
	
	private void calculateTemperature(){
		temperature = (size / distance);
	}
	
	private void calculatePrecipitation(){
		float waterAmount = Main.random.nextFloat();
		precipitation = waterAmount / size + temperature;
	}
	
	private void calculateBiome(){
		if(temperature >= 0 && temperature < 0.2){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.forest;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.forest;
			}
		}else if(temperature >= 0.2 && temperature < 0.4){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.forest;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.forest;
			}
		}else if(temperature >= 0.4 && temperature < 0.6){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.forest;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.forest;
			}
		}else if(temperature >= 0.6 && temperature < 0.8){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.forest;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.forest;
			}
		}else if(temperature >= 0.8 && temperature <= 1.0){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.forest;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.forest;
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
	
	public void incrementAngle(){
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
	
	public void draw(Graphics g){
		switch(Main.state){
		case PLANETRY:
			calculateXAndY();
			g.setColor(Color.cyan);
			g.fillOval(x - size/2, y - size/2, size, size);
			break;
		case SURFACE:
			for(int i = 0; i < noise.length; i++){
				for(int j = 0; j < noise[0].length; j++){
					g.setColor(new Color((int) (255 * noise[i][j]), (int) (255 * noise[i][j]), (int) (255 * noise[i][j])));
					g.fillRect(i*16 + xDif, j*16 + yDif, 16, 16);
					terrain[i][j].draw(g, i * 16 + xDif, j * 16 + yDif);
					if(decoration[i][j] != null){
						decoration[i][j].draw(g, i * 16 + xDif, j * 16 + yDif);
					}
				}
			}
			break;
		default:
			break;
		}
		
	}

}

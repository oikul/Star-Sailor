package starSailor;

import java.awt.Graphics;

public class Planet {
	
	private int size, xDif, yDif;
	private float distanceFromStar, temperature, precipitation;
	private NoiseGenerator generator;
	private float[][] noise;
	private Block[][] terrain;
	private Block[][] decoration;
	private Biome biome;
	
	public Planet(int size, float distanceFromStar){
		this.size = size;
		this.distanceFromStar = distanceFromStar;
		generator = new NoiseGenerator(size * 10, size * 10, 3, 4);
		noise = generator.getNoise();
		calculateTemperature();
		calculatePrecipitation();
		calculateBiome();
		terrain = biome.buildTerrain(noise);
		decoration = biome.buildDecoration(noise);
	}
	
	private void calculateTemperature(){
		temperature = (size / distanceFromStar);
	}
	
	private void calculatePrecipitation(){
		float waterAmount = Main.random.nextFloat();
		precipitation = waterAmount / size + temperature;
	}
	
	private void calculateBiome(){
		if(temperature >= 0 && temperature < 0.2){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.ice_plains;
			}
		}else if(temperature >= 0.2 && temperature < 0.4){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.tundra;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.tundra;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.tundra;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.tundra;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.tundra;
			}
		}else if(temperature >= 0.4 && temperature < 0.6){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.forest;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.forest;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.ice_plains;
			}
		}else if(temperature >= 0.6 && temperature < 0.8){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.jungle;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.ice_plains;
			}
		}else if(temperature >= 0.8 && temperature <= 1.0){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.lava_ocean;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_plains;
			}else if(precipitation >= 0.8 && precipitation <= 1.0){
				biome = Biome.ice_plains;
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
	
	public void draw(Graphics g){
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				/*g.setColor(new Color((int) (255 * noise[i][j]), (int) (255 * noise[i][j]), (int) (255 * noise[i][j])));
				g.fillRect(i*16 + xDif, j*16 + yDif, 16, 16);*/
				terrain[i][j].draw(g, i * 16 + xDif, i * 16 + yDif);
				if(decoration[i][j] != null){
					decoration[i][j].draw(g, i * 16 + xDif, j * 16 + yDif);
				}
			}
		}
	}

}

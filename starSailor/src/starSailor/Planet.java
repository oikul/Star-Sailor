package starSailor;

import java.awt.Color;
import java.awt.Graphics;

public class Planet {
	
	private int size;
	private float distanceFromStar, temperature, precipitation;
	private NoiseGenerator generator;
	private float[][] noise;
	private Block[][] blocks;
	private Biome biome;
	
	public Planet(int size, float distanceFromStar){
		this.size = size;
		this.distanceFromStar = distanceFromStar;
		generator = new NoiseGenerator(size * 10, size * 10, 3, 4);
		noise = generator.getNoise();
		calculateTemperature();
		calculatePrecipitation();
		calculateBiome();
	}
	
	private void calculateTemperature(){
		temperature = (size / distanceFromStar);
	}
	
	private void calculatePrecipitation(){
		float waterAmount = Main.random.nextFloat();
		precipitation = waterAmount / size + temperature;
	}
	
	private void calculateBiome(){
		if(temperature >= 0 && temperature < 0.34){
			if(precipitation >= 0 && precipitation < 0.34){
				biome = Biome.forest;
			}else if(precipitation >= 0.34 && precipitation < 0.67){
				
			}else if(precipitation >= 0.67 && precipitation <= 1){
				
			}
		}else if(temperature >= 0.34 && temperature < 0.67){
			if(precipitation >= 0 && precipitation < 0.34){
				
			}else if(precipitation >= 0.34 && precipitation < 0.67){
				
			}else if(precipitation >= 0.67 && precipitation <= 1){
				
			}
		}else if(temperature >= 0.67 && temperature <= 1){
			if(precipitation >= 0 && precipitation < 0.34){
				
			}else if(precipitation >= 0.34 && precipitation < 0.67){
				
			}else if(precipitation >= 0.67 && precipitation <= 1){
				
			}
		}
	}
	
	public int getSize(){
		return size;
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < noise.length; i++){
			for(int j = 0; j < noise[0].length; j++){
				g.setColor(new Color((int)(255*noise[i][j]), (int)(255*noise[i][j]), (int)(255*noise[i][j])));
				g.fillRect(i*16, j*16, 32, 32);
			}
		}
	}

}

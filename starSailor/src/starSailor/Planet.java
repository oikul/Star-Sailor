package starSailor;

import java.awt.Color;
import java.awt.Graphics;

public class Planet {
	
	private int size;
	private NoiseGenerator generator;
	private float[][] noise;
	
	public Planet(int size){
		this.size = size;
		generator = new NoiseGenerator(size, size, 3, 4);
		noise = generator.getNoise();
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

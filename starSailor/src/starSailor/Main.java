package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private boolean running = false;
	public static int width, height;
	public static Random random;

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run(){
		initialise();
		while(running){
			
		}
		dispose();
	}
	
	private void initialise(){
		running = true;
		setTitle("Star Sailor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		random = new Random();
	}
	
	private void update(){
		
	}
	
	private void draw(){
		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(width, height);
		Graphics offGraphics = offImage.getGraphics();
		
		g2d.drawImage(offImage, width, height, null);
		setVisible(running);
	}
	
	public static void exit(){
		
	}

}

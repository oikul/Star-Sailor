package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private boolean running = false;
	public static int width, height;
	public static Random random;
	private Planet planet;

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run(){
		initialise();
		while(running){
			long beforeTime = System.currentTimeMillis();
			update();
			draw();
			long afterTime = System.currentTimeMillis();
			long diff = afterTime - beforeTime;
			long waitTime = diff / 60;
			try{
				if(waitTime > 0){
					Thread.sleep(waitTime);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		dispose();
	}
	
	private void initialise(){
		running = true;
		setTitle("Star Sailor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setSize(width, height);
		setVisible(running);
		random = new Random();
		planet = new Planet(500);
	}
	
	private void update(){
		
	}
	
	private void draw(){
		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(width, height);
		Graphics offGraphics = offImage.getGraphics();
		planet.draw(offGraphics);
		g2d.drawImage(offImage, 0, 0, width, height, null);
	}
	
	public static void exit(){
		
	}

}

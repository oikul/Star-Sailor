package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	public static int width, height;
	public static Random random;
	private InputHandler input;
	private Planet planet;
	private long time;
	
	public static enum State{
		GALACTIC, SOLAR, PLANETRY, SURFACE;
	}
	public static State state;

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
		state = State.SURFACE;
		random = new Random();
		input = new InputHandler(this);
		planet = new Planet(3, 500, 40);
		time = System.currentTimeMillis();
	}
	
	private void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 10){
			switch (state){
			case GALACTIC:

				break;
			case SOLAR:
				planet.incrementAngle();
				break;
			case PLANETRY:
				
				break;
			case SURFACE:
				if(input.isKeyDown(KeyEvent.VK_W)){
					planet.panUp();
				}
				if(input.isKeyDown(KeyEvent.VK_A)){
					planet.panLeft();
				}
				if(input.isKeyDown(KeyEvent.VK_S)){
					planet.panDown();
				}
				if(input.isKeyDown(KeyEvent.VK_D)){
					planet.panRight();
				}
				break;
			}
			time = newTime;
		}
		
	}
	
	private void draw(){
		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(width, height);
		Graphics offGraphics = offImage.getGraphics();
		offGraphics.fillRect(0, 0, width, height);
		switch (state){
		case GALACTIC:
			
			break;
		case SOLAR:
			
			break;
		case PLANETRY:
			planet.draw(offGraphics);
			break;
		case SURFACE:
			planet.draw(offGraphics);
			break;
		}
		g2d.drawImage(offImage, 0, 0, width, height, null);
	}

}

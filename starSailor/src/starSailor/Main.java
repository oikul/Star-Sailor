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
	private Galaxy galaxy;
	private Planet planet;
	private Player player;
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
		state = State.GALACTIC;
		random = new Random();
		input = new InputHandler(this);
		galaxy = new Galaxy(4096);
		planet = new Planet(16, 500, 40);
		player = new Player("character/charSprites.png");
		time = System.currentTimeMillis();
	}
	
	private void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 10){
			switch (state){
			case GALACTIC:
				if(input.isKeyDown(KeyEvent.VK_W)){
					galaxy.panUp();
				}else if(input.isKeyDown(KeyEvent.VK_A)){
					galaxy.panLeft();
				}else if(input.isKeyDown(KeyEvent.VK_S)){
					galaxy.panDown();
				}else if(input.isKeyDown(KeyEvent.VK_D)){
					galaxy.panRight();
				}
				if(input.getMouseWheelUp()){
					state = State.SOLAR;
					input.stopMouseWheel();
				}
				galaxy.update();
				break;
			case SOLAR:
				planet.incrementAngle();
				if(input.getMouseWheelUp()){
					state = State.PLANETRY;
					input.stopMouseWheel();
				}
				if(input.getMouseWheelDown()){
					state = State.GALACTIC;
					input.stopMouseWheel();;
				}
				break;
			case PLANETRY:
				if(input.getMouseWheelUp()){
					state = State.SURFACE;
					input.stopMouseWheel();
				}
				if(input.getMouseWheelDown()){
					state = State.SOLAR;
					input.stopMouseWheel();
				}
				break;
			case SURFACE:
				if(input.isKeyDown(KeyEvent.VK_W)){
					planet.panUp();
					player.setDirection(Player.Direction.UP);
					player.update();
				}else if(input.isKeyDown(KeyEvent.VK_A)){
					planet.panLeft();
					player.setDirection(Player.Direction.LEFT);
					player.update();
				}else if(input.isKeyDown(KeyEvent.VK_S)){
					planet.panDown();
					player.setDirection(Player.Direction.DOWN);
					player.update();
				}else if(input.isKeyDown(KeyEvent.VK_D)){
					planet.panRight();
					player.setDirection(Player.Direction.RIGHT);
					player.update();
				}else{
					player.stop();
				}
				planet.update();
				if(input.getMouseWheelDown()){
					state = State.PLANETRY;
					input.stopMouseWheel();
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
			galaxy.draw(offGraphics);
			break;
		case SOLAR:
			
			break;
		case PLANETRY:
			planet.draw(offGraphics);
			break;
		case SURFACE:
			planet.draw(offGraphics);
			player.draw(offGraphics);
			break;
		}
		g2d.drawImage(offImage, 0, 0, width, height, null);
	}

}

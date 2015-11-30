package starSailor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	public static int width, height;
	public static Random random;
	private InputHandler input;
	private Galaxy galaxy;
	private Player player;
	private long time;
	private State saveState;
	private ShipInterior ship;
	
	public static enum State{
		GALACTIC, SOLAR, PLANETRY, SURFACE, SHIP;
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
		Biome.createBiomes();
		galaxy = new Galaxy(4096);
		player = new Player("character/charSprites.png", "spaceship/shipSprites.png");
		ship = new ShipInterior();
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
				break;
			case SOLAR:
				break;
			case PLANETRY:
				break;
			case SURFACE:
				if(input.isKeyDown(KeyEvent.VK_W)){
					galaxy.moveSurface(0);
				}else if(input.isKeyDown(KeyEvent.VK_A)){
					galaxy.moveSurface(1);
				}else if(input.isKeyDown(KeyEvent.VK_S)){
					galaxy.moveSurface(2);
				}else if(input.isKeyDown(KeyEvent.VK_D)){
					galaxy.moveSurface(3);
				}
				if(input.isKeyDown(KeyEvent.VK_Q)){
					if(Player.isShip()){
						Player.setIsShip(false);
					}else{
						Player.setIsShip(true);
					}
					input.artificialKeyReleased(KeyEvent.VK_Q);
				}
				break;
			case SHIP:
				if(input.isKeyDown(KeyEvent.VK_W)){
					ship.panUp();
				}else if(input.isKeyDown(KeyEvent.VK_A)){
					ship.panLeft();
				}else if(input.isKeyDown(KeyEvent.VK_S)){
					ship.panDown();
				}else if(input.isKeyDown(KeyEvent.VK_D)){
					ship.panRight();
				}
				if(input.isKeyDown(KeyEvent.VK_E)){
					state = saveState;
					Player.setIsShip(true);
					input.artificialKeyReleased(KeyEvent.VK_E);
				}
				break;
			default:
				break;
			}
			if(input.isMouseDown(MouseEvent.BUTTON1)){
				galaxy.checkForClick(input.getMousePositionRelativeToComponent().x, input.getMousePositionRelativeToComponent().y);
				input.artificialMouseReleased(MouseEvent.BUTTON1);
			}
			if(input.getMouseWheelUp()){
				galaxy.zoom(true);
				input.stopMouseWheel();
			}
			if(input.getMouseWheelDown()){
				galaxy.zoom(false);
				input.stopMouseWheel();
			}
			if(input.isKeyDown(KeyEvent.VK_W)){
				player.setDirection(Player.Direction.UP);
				player.update();
			}else if(input.isKeyDown(KeyEvent.VK_A)){
				player.setDirection(Player.Direction.LEFT);
				player.update();
			}else if(input.isKeyDown(KeyEvent.VK_S)){
				player.setDirection(Player.Direction.DOWN);
				player.update();
			}else if(input.isKeyDown(KeyEvent.VK_D)){
				player.setDirection(Player.Direction.RIGHT);
				player.update();
			}else{
				player.stop();
			}
			if(input.isKeyDown(KeyEvent.VK_E) && Player.isShip()){
				saveState = state;
				state = State.SHIP;
				Player.setIsShip(false);
				input.artificialKeyReleased(KeyEvent.VK_E);
			}
			galaxy.update();
			time = newTime;
		}
	}
	
	private void draw(){
		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(width, height);
		Graphics offGraphics = offImage.getGraphics();
		offGraphics.fillRect(0, 0, width, height);
		galaxy.draw(offGraphics);
		if(state == State.SHIP){
			ship.draw(offGraphics);
		}
		player.draw(offGraphics);
		g2d.drawImage(offImage, 0, 0, width, height, null);
	}

}

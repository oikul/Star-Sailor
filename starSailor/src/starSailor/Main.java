package starSailor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	public static boolean alive = false;
	public static int width, height;
	public static Random random;
	public static final double root2 = Math.sqrt(2.0);
	private InputHandler input;
	private Galaxy galaxy;
	private Player player;
	private long time;
	private ShipInterior ship;
	private SpaceBattle sb;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static enum State{
		GALACTIC, SOLAR, PLANETRY, SURFACE, SHIP, SPACEBATTLE, MOON, DUNGEON, VILLAGE, SPACESTATION;
	}
	public static State state;
	private State saveState = State.GALACTIC;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run(){
		initialise();
		while(running){
			alive = true;
			long beforeTime = System.currentTimeMillis();
			update();
			draw();
			long afterTime = System.currentTimeMillis();
			long diff = afterTime - beforeTime;
			long waitTime = diff / 45;
			try{
				if(waitTime > 0){
					Thread.sleep(waitTime);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			if(!alive || Player.playerHealth <= 0){
				dispose();
				initialise();
				Player.playerHealth = 100;
				gameOver();
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
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		device.setFullScreenWindow(this);
		setVisible(running);
		setResizable(false);
		boolean fix = false;
		Cursor cursor = this.getCursor();
		while(!fix){
			try{
				cursor = Toolkit.getDefaultToolkit().createCustomCursor(ResourceLoader.getImage("cursor/cursor.png"), new Point(getX(), getY()), "c");
				fix = true;
			}catch(Exception e){
				System.out.println("fail");
			}
		}
		setCursor(cursor);
		state = State.GALACTIC;
		random = new Random();
		input = new InputHandler(this);
		Biome.createBiomes();
		galaxy = new Galaxy(8192);
		player = new Player("character/character_sprites.png", "spaceship/ship_sprites.png");
		ship = new ShipInterior();
		sb = new SpaceBattle();
		time = System.currentTimeMillis();
	}
	
	private void update(){
		long newTime = System.currentTimeMillis();
		if(newTime >= time + 10){
			switch (state){
			case SURFACE:
				if(input.isKeyDown(KeyEvent.VK_Q)){
					if(Player.isShip() && Player.canLeave){
						Player.setIsShip(false);
					}else{
						Player.setIsShip(true);
					}
					input.artificialKeyReleased(KeyEvent.VK_Q);
				}
				break;
			case MOON:
				if(input.isKeyDown(KeyEvent.VK_Q)){
					if(Player.isShip() && Player.canLeave){
						Player.setIsShip(false);
					}else{
						Player.setIsShip(true);
					}
					input.artificialKeyReleased(KeyEvent.VK_Q);
				}
				break;
			case SHIP:
				if(input.isKeyDown(KeyEvent.VK_E)){
					state = saveState;
					Player.setIsShip(true);
					input.artificialKeyReleased(KeyEvent.VK_E);
				}
				Block.engine_fire.update();
				break;
			case SPACEBATTLE:
				if(input.isMouseDown(MouseEvent.BUTTON1)){
					sb.shoot(input.getMousePositionOnScreen());
					Sound.laser.play();
					input.artificialMouseReleased(MouseEvent.BUTTON1);
				}else if(input.isKeyDown(KeyEvent.VK_SPACE)){
				    sb.shoot(input.getMousePositionOnScreen());
				}
				if(input.isKeyDown(KeyEvent.VK_F)){
					if(sb.finished()){
						state = saveState;
						input.artificialKeyReleased(KeyEvent.VK_F);
					}
				}
				if(input.isKeyDown(KeyEvent.VK_W) && input.isKeyDown(KeyEvent.VK_A)){
					sb.panUL();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_W) && input.isKeyDown(KeyEvent.VK_D)){
					sb.panUR();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_S) && input.isKeyDown(KeyEvent.VK_A)){
					sb.panDL();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_S) && input.isKeyDown(KeyEvent.VK_D)){
					sb.panDR();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_W)){
					sb.panUp();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_S)){
					sb.panDown();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_A)){
					sb.panLeft();
					player.setMoving();
				}else if(input.isKeyDown(KeyEvent.VK_D)){
					sb.panRight();
					player.setMoving();
				}else{
					player.stop();
				}
				sb.update();
				break;
			case SPACESTATION:
				if(input.isMouseDown(MouseEvent.BUTTON1)){
					SpaceStation.clicked = true;
				}else{
					SpaceStation.clicked = false;
				}
				break;
			case DUNGEON:
				if(input.getMouseWheelDown()){
					Dungeon.leave = true;
					input.stopMouseWheel();
				}
				if(input.isKeyDown(KeyEvent.VK_SPACE)){
					player.attack();
					input.artificialKeyReleased(KeyEvent.VK_SPACE);
				}
				break;
			default:
				break;
			}
			if(state != State.SPACEBATTLE){
			if(input.isKeyDown(KeyEvent.VK_W) && input.isKeyDown(KeyEvent.VK_A)){
				galaxy.panUL();
				player.setMoving();
				ship.panUL();
			}else if(input.isKeyDown(KeyEvent.VK_W) && input.isKeyDown(KeyEvent.VK_D)){
				galaxy.panUR();
				player.setMoving();
				ship.panUR();
			}else if(input.isKeyDown(KeyEvent.VK_S) && input.isKeyDown(KeyEvent.VK_A)){
				galaxy.panDL();
				player.setMoving();
				ship.panDL();
			}else if(input.isKeyDown(KeyEvent.VK_S) && input.isKeyDown(KeyEvent.VK_D)){
				galaxy.panDR();
				player.setMoving();
				ship.panDR();
			}else if(input.isKeyDown(KeyEvent.VK_W)){
				galaxy.panUp();
				player.setMoving();
				player.setDirection(1);
				ship.panUp();
			}else if(input.isKeyDown(KeyEvent.VK_A)){
				galaxy.panLeft();
				player.setMoving();
				player.setDirection(3);
				ship.panLeft();
			}else if(input.isKeyDown(KeyEvent.VK_S)){
				galaxy.panDown();
				player.setMoving();
				player.setDirection(0);
				ship.panDown();
			}else if(input.isKeyDown(KeyEvent.VK_D)){
				galaxy.panRight();
				player.setMoving();
				player.setDirection(2);
				ship.panRight();
			}else{
				player.stop();
			}
			if(input.isMouseDown(MouseEvent.BUTTON1)){
				galaxy.checkForClick(input.getMousePositionOnScreen().x, input.getMousePositionOnScreen().y);
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
			if(input.isKeyDown(KeyEvent.VK_E) && Player.isShip()){
				saveState = state;
				state = State.SHIP;
				Player.setIsShip(false);
				input.artificialKeyReleased(KeyEvent.VK_E);
			}
			if(input.isKeyDown(KeyEvent.VK_F)){
				state = State.SPACEBATTLE;
			    input.artificialKeyReleased(KeyEvent.VK_F);
			}
			galaxy.update();
			time = newTime;
			}
			player.update(input.getMousePositionOnScreen());
			if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
				System.exit(0);
			}
		}
	}
	
	private void draw(){
			Graphics g = getGraphics();
			Graphics2D g2d = (Graphics2D) g;
			Image offImage = createImage(width, height);
			Graphics offGraphics = offImage.getGraphics();
			offGraphics.setColor(Color.black);
			offGraphics.fillRect(0, 0, width, height);
			if(state != State.SHIP && state != State.SPACEBATTLE){
			galaxy.draw(offGraphics);
			}
			if(state == State.SHIP){
			ship.draw(offGraphics);
			}
			if(state == State.SPACEBATTLE){
			sb.draw(offGraphics);
			}
			player.draw(offGraphics);
			offGraphics.setColor(Color.red);
			offGraphics.drawString("Kills: " + Player.killCount, 50, 50);
			offGraphics.drawString("Health: " + Player.HPSTAT, 50, 60);
			offGraphics.drawString("Speed: " + Player.speedSTAT, 50, 70);
			offGraphics.drawString("Accuracy: " + Player.accuracySTAT, 50, 80);
			g2d.drawImage(offImage, 0, 0, width, height, null);
	}

	private void gameOver(){
		Player.setIsShip(true);
		Image gameover = ResourceLoader.getImage("background/gameOver.png");
		
		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(gameover,0,0,Main.width,Main.height,null);
		long time = 4000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
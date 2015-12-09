package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Dungeon {
	
	private ArrayList<Rectangle> rooms;
	private ArrayList<Rectangle> corridors;
	private ArrayList<Enemy> enemies;
	private int xDif, yDif;
	public static boolean leave = false;
	public static Main.State saveState = Main.State.SURFACE;
	
	public Dungeon(int numOfRooms){
		rooms = new ArrayList<Rectangle>();
		corridors = new ArrayList<Rectangle>();
		enemies = new ArrayList<Enemy>();
		Rectangle room = new Rectangle(Main.width/2 - 64, Main.height/2 - 64, 128, 128);
		rooms.add(room);
		for(int i = 0; i < numOfRooms; i++){
			boolean collided = false;
			room = new Rectangle(Main.random.nextInt(4096) - 2048, Main.random.nextInt(4096) - 2048, Main.random.nextInt(512) + 128, Main.random.nextInt(512) + 128);
			enemies.add(new Enemy("enemies/ghost_sprites.png", 16, room.x + Main.random.nextInt(room.width), room.y + Main.random.nextInt(room.height)));
			for(int j = 0; j < rooms.size(); j++){
				if(rooms.get(j).intersects(room)){
					collided = true;
				}
			}
			if(!collided){
				rooms.add(room);	
			}
		}
		for(int i = 0; i < rooms.size(); i ++){
			for(int j = 0; j < rooms.size(); j++){
				createHCorridors(rooms.get(i), rooms.get(j));
				createVCorridors(rooms.get(i), rooms.get(j));
			}
		}
	}
	
	public void panUp(){
		if(canMoveUp()){
			yDif ++;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panUp();
			}
		}
	}

	public void panLeft(){
		if(canMoveLeft()){
			xDif ++;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panLeft();
			}
		}
	}

	public void panDown(){
		if(canMoveDown()){
			yDif --;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panDown();
			}
		}
	}

	public void panRight(){
		if(canMoveRight()){
			xDif --;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panRight();
			}
		}
	}

	public void panUL(){
		if(canMoveUp() && canMoveLeft()){
			yDif ++;
			xDif ++;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panUL();
			}
		}
	}

	public void panUR(){
		if(canMoveUp() && canMoveRight()){
			yDif ++;
			xDif --;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panUR();
			}
		}
	}

	public void panDL(){
		if(canMoveDown() && canMoveLeft()){
			yDif --;
			xDif ++;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panDL();
			}
		}
	}

	public void panDR(){
		if(canMoveDown() && canMoveRight()){
			yDif --;
			xDif --;
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).panDR();
			}
		}
	}
	
	private boolean canMoveUp(){
		boolean canMove = false;
		for(int i = 0; i < rooms.size(); i++){
			if(Planet.playerRectUp.intersects(new Rectangle(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height))){
				canMove = true;
			}
		}
		for(int i = 0; i< corridors.size(); i++){
			if(Planet.playerRectUp.intersects(new Rectangle(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height))){
				canMove = true;
			}
		}
		return canMove;
	}

	private boolean canMoveLeft(){
		boolean canMove = false;
		for(int i = 0; i < rooms.size(); i++){
			if(Planet.playerRectLeft.intersects(new Rectangle(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height))){
				canMove = true;
			}
		}
		for(int i = 0; i< corridors.size(); i++){
			if(Planet.playerRectLeft.intersects(new Rectangle(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height))){
				canMove = true;
			}
		}
		return canMove;
	}

	private boolean canMoveDown(){
		boolean canMove = false;
		for(int i = 0; i < rooms.size(); i++){
			if(Planet.playerRectDown.intersects(new Rectangle(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height))){
				canMove = true;
			}
		}
		for(int i = 0; i< corridors.size(); i++){
			if(Planet.playerRectDown.intersects(new Rectangle(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height))){
				canMove = true;
			}
		}
		return canMove;
	}

	private boolean canMoveRight(){
		boolean canMove = false;
		for(int i = 0; i < rooms.size(); i++){
			if(Planet.playerRectRight.intersects(new Rectangle(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height))){
				canMove = true;
			}
		}
		for(int i = 0; i< corridors.size(); i++){
			if(Planet.playerRectRight.intersects(new Rectangle(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height))){
				canMove = true;
			}
		}
		return canMove;
	}

	public void createHCorridors(Rectangle room1, Rectangle room2){
		int xTemp = room1.x + room1.width/2, yTemp = room1.y + room1.height/2, widthTemp = Math.abs(room1.x - room2.x), heightTemp = 32;
		Rectangle room = new Rectangle(xTemp, yTemp, widthTemp, heightTemp);
		boolean collided = false;
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).intersects(room) && !rooms.get(i).equals(room1) && !rooms.get(i).equals(room2)){
				collided = true;
			}
		}
		if(!collided){
			corridors.add(room);
		}
	}
	
	public void createVCorridors(Rectangle room1, Rectangle room2){
		int xTemp = room1.x + room1.width/2, yTemp = room1.y + room1.height/2, widthTemp = 32, heightTemp = Math.abs(room1.y - room2.y);
		Rectangle room = new Rectangle(xTemp, yTemp, widthTemp, heightTemp);
		boolean collided = false;
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).intersects(room) && !rooms.get(i).equals(room1) && !rooms.get(i).equals(room2)){
				collided = true;
			}
		}
		if(!collided){
			corridors.add(room);
		}
	}
	
	public void update(){
		if(Main.state == Main.State.DUNGEON){
			if(leave){
				xDif = 0;
				yDif = 0;
				Main.state = saveState;
				leave = false;
			}
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).update();
				if(enemies.get(i).dead){
					enemies.remove(i);
				}
			}
		}
	}
	
	public void draw(Graphics g){
		if(Main.state == Main.State.DUNGEON){
			g.setColor(Color.gray);
			for(int i = 0; i < rooms.size(); i++){
				g.fillRect(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height);
			}
			for(int i = 0; i < corridors.size(); i++){
				g.fillRect(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height);
			}
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).draw(g);
			}
		}
	}

}

package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Dungeon {
	
	private ArrayList<Rectangle> rooms;
	private ArrayList<Rectangle> corridors;
	private int xDif, yDif;
	
	public Dungeon(int numOfRooms){
		rooms = new ArrayList<Rectangle>();
		corridors = new ArrayList<Rectangle>();
		Rectangle room = new Rectangle(Main.random.nextInt(Main.width), Main.random.nextInt(Main.width), Main.random.nextInt(256) + 64, Main.random.nextInt(256) + 64);
		rooms.add(room);
		for(int i = 0; i < numOfRooms; i++){
			boolean collided = false;
			room = new Rectangle(Main.random.nextInt(Main.width), Main.random.nextInt(Main.width), Main.random.nextInt(256) + 64, Main.random.nextInt(256) + 64);
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

	public void panUL(){
		yDif ++;
		xDif ++;
	}

	public void panUR(){
		yDif ++;
		xDif --;
	}

	public void panDL(){
		yDif --;
		xDif ++;
	}

	public void panDR(){
		yDif --;
		xDif --;
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
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.gray);
		for(int i = 0; i < rooms.size(); i++){
			g.fillRect(rooms.get(i).x + xDif, rooms.get(i).y + yDif, rooms.get(i).width, rooms.get(i).height);
		}
		for(int i = 0; i < corridors.size(); i++){
			g.fillRect(corridors.get(i).x + xDif, corridors.get(i).y + yDif, corridors.get(i).width, corridors.get(i).height);
		}
	}

}

package starSailor;

import java.awt.Graphics;
import java.awt.Point;

public class ShipInterior {
	
	private Block[][] ship;
	private Point[][] shipPoints;
	private int xDif, yDif;
	
	public ShipInterior(){
		ship = new Block[Main.width/16][Main.height/16];
		shipPoints = new Point[Main.width/16][Main.height/16];
		genShip();
	}
	
	private void genShip(){
		ship[ship.length - 1][0] = Block.engine_fire;
		shipPoints[ship.length - 1][0] = new Point(-64, 0);
		ship[ship.length - 1][1] = Block.engine_fire;
		shipPoints[ship.length - 1][1] = new Point(-64, 22 * 16);
		ship[ship.length - 1][2] = Block.engine;
		shipPoints[ship.length - 1][2] = new Point(0, 0);
		ship[ship.length - 1][3] = Block.engine;
		shipPoints[ship.length - 1][3] = new Point(0, 22*16);
		for(int i = 1; i < 8; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, -16);
		}
		for(int i = 8; i < 15; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 0);
		}
		for(int i = 15; i < 21; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 16);
		}
		for(int i = 21; i < 26; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 32);
		}
		for(int i = 26; i < 30; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 48);
		}
		for(int i = 30; i < 33; i++){
			ship[ship.length - 1][4 + i] = Block.ship_walls;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 64);
		}
		for(int i = 33; i < 35; i++){
			ship[ship.length - 1][4 + i] = Block.glass;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 80);
		}
		for(int i = 35; i < 36; i++){
			ship[ship.length - 1][4 + i] = Block.glass;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 96);
		}
		for(int i = 36; i < 37; i++){
			ship[ship.length - 1][4 + i] = Block.glass;
			shipPoints[ship.length - 1][4 + i] = new Point(i * 16, 112);
		}
		for(int i = 8; i < 26; i++){
			ship[ship.length - 2][i] = Block.glass;
			shipPoints[ship.length - 2][i] = new Point(37*16, i*16);
		}
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 12; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 0; i < 8; i++){
			for(int j = 22; j < 34; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 8; i < 15; i++){
			for(int j = 1; j < 33; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 15; i < 21; i++){
			for(int j = 2; j < 32; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 21; i < 26; i++){
			for(int j = 3; j < 31; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 26; i < 30; i++){
			for(int j = 4; j < 30; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 30; i < 33; i++){
			for(int j = 5; j < 29; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 33; i < 35; i++){
			for(int j = 6; j < 28; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 35; i < 36; i++){
			for(int j = 7; j < 27; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
			}
		}
		for(int i = 36; i < 37; i++){
			for(int j = 8; j < 26; j++){
				ship[i][j] = Block.iron;
				shipPoints[i][j] = new Point(i * 16, j * 16);
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
	
	public void draw(Graphics g){
		for(int i = 0; i < ship.length; i++){
			for(int j = 0; j < ship[0].length; j++){
				if(ship[i][j] != null){
					ship[i][j].draw(g, shipPoints[i][j].x + xDif, shipPoints[i][j].y + yDif);
				}
			}
		}
	}

}

package starSailor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy {
	
	private Image[][] sprites;
	private int iIndex, jIndex, x, y;
	private long time, attackTime;
	private int health = 10;
	public boolean dead = false;
	
	public Enemy(String path, int size, int x, int y){
		this.x = x;
		this.y = y;
		sprites = ResourceLoader.getPlayerSprites(path, size, size);
		time = System.currentTimeMillis();
		attackTime = System.currentTimeMillis();
	}
	
	private void setDirection(int dir){
		iIndex = dir;
	}
	
	private void incrementJ(){
		if(System.currentTimeMillis() >time + 300){
			if(jIndex < 2){
				jIndex ++;
			}else{
				jIndex = 0;
			}
			time = System.currentTimeMillis();
		}
	}
	
	private void takeDamage(){
		if(Player.isAttacking && Player.attackRange.intersects(new Rectangle(x, y, 16, 16))){
			health --;
			if(iIndex == 0){
				y -= 10;
			}else if(iIndex == 1){
				y += 10;
			}else if(iIndex == 2){
				x -= 10;
			}else if(iIndex == 3){
				x += 10;
			}
			Sound.ghostHurt.play();
			Player.isAttacking = false;
		}
		if(health <= 0){
			dead = true;
			Player.killCount ++;
		}
	}
	
	private void move(){
		if(new Rectangle(x, y, 16, 16).intersects(0, 0, Main.width, Main.height)){
			if(x < Main.width/2 - 8){
				x++;
				setDirection(2);
			}else if (x > Main.width/2 - 8){
				x--;
				setDirection(3);
			}
			if(y < Main.height/2 - 8){
				y++;
				setDirection(0);
			}else if(y > Main.height/2 - 8){
				y--;
				setDirection(1);
			}
		}
	}
	
	public void panUp(){
		y ++;
	}

	public void panLeft(){
		x ++;
	}

	public void panDown(){
		y --;
	}

	public void panRight(){
		x --;
	}

	public void panUL(){
		y ++;
		x ++;
	}

	public void panUR(){
		y ++;
		x --;
	}

	public void panDL(){
		y --;
		x ++;
	}

	public void panDR(){
		y --;
		x --;
	}
	
	private void attack(){
		long newTime = System.currentTimeMillis();
		if(newTime >= attackTime + 100){
			if(new Rectangle(Main.width/2 - 10, Main.height/ 2 - 10, 20, 20).intersects(new Rectangle(x, y, 16, 16))){
				Player.playerHealth --;
			}
			attackTime = System.currentTimeMillis();
		}
	}
	
	public void update(){
		incrementJ();
		move();
		takeDamage();
		attack();
		double num = Main.random.nextDouble();
		if(num < 0.0001 && num > 0.00001){
			Sound.ghostLaughter.play();
		}else if(num <= 0.00001){
			Sound.ghost.play();
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(sprites[iIndex][jIndex], x, y, null);
	}

}

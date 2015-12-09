package starSailor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class MiniMap {

	private double width,height,ratio;
	private Image background;
	private int x,y;
	private Point2D.Double ship;
	private boolean[][] contains;
	private double xsize,ysize;
	
	public MiniMap(Image background,int x,int y,Point2D.Double start) {
		
		this.background = background;
		this.x = x;
		this.y = y;
		double xsize = background.getWidth(null);
		double ysize = background.getHeight(null);
		ratio = 200.0/background.getHeight(null);
		height = 200;
		width = ratio * background.getWidth(null);
		ship = new Point2D.Double((xsize/2.0),(ysize/2.0));
		contains = new boolean[100][100];
		for (int i = 0; i < contains.length; i++) {
			for (int j = 0; j < contains[0].length; j++) {
				contains[i][j] = false;
			}
		}
		
		this.xsize = width/100.0;
		this.ysize = height/100.0;
		
	}
	
	public void update(double x, double y){
		this.ship = new Point2D.Double(ship.x+(x/3),ship.y+(y/3));
		
		
		for (int i = 0; i < contains.length; i++) {
			for (int j = 0; j < contains[i].length; j++) {
				if(SpaceBattle.doesContain(new Rectangle((int)((j*xsize)/ratio)*3,(int)((i*ysize)/ratio)*3,(int)((xsize)/ratio)*3,(int)((ysize)/ratio)*3))){
					contains[i][j] = true;
				}else{
					contains[i][j] = false;
				}
			}
		}
		
	}
	
	public void draw(Graphics2D g2d){
		
		g2d.drawImage(background,x,y,(int)(width),(int)(height),null);
		g2d.setColor(Color.orange);
		g2d.drawRect(x+(int)(Main.width/6.0*ratio), y+(int)(Main.height/6.0*ratio),
				(int)(width-(Main.width/3.0*ratio)), (int)(height-(Main.height/3*ratio)));
		g2d.setColor(Color.red);
		for (int i = 0; i < contains.length; i++) {
			for (int j = 0; j < contains[0].length; j++) {
				if(contains[i][j]){
					double a = j*xsize;
					double b = i*ysize;
					g2d.drawRect((int)(x+a),(int)(y+b),(int)(xsize),(int)(ysize));
				}
			}
		}
		
		g2d.setColor(Color.BLUE);
		g2d.drawRect((int)(x+(ship.x*ratio))-1, (int)(y+(ship.y*ratio))-1, 2, 2);
	}

}

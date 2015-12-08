package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SpaceBattle {
	
	private Point2D.Double spaceLocation;
	private int xSize;
	private int ySize;
	private double xChange;
	private double yChange;
	
	private int playerHealth;
	
	private ArrayList<Point2D.Double> trajectories;
	private ArrayList<Rectangle> fighterLocations;
	private ArrayList<Rectangle> carrierLocations;
	private Rectangle commanderLocation;
	private ArrayList<Line2D.Double> shots;
	private ArrayList<Fighter> fighters;
	private ArrayList<Carrier> carriers;
	private Command commander;
	private boolean isCommander;
	
	private Point2D.Double pointer; // displays the pointer location
	private Image background;
	public static Main.State saveState = Main.State.GALACTIC;
	private boolean newBattle = false;
	
	public SpaceBattle(){
		int carriers = 5;
		int fighters = 5;
		this.carriers = new ArrayList<Carrier>(carriers);
		this.fighters = new ArrayList<Fighter>(fighters);
		int planet = Main.random.nextInt(8);
		background = ResourceLoader.getImage("background/planet"+planet+".png");
		xSize = background.getWidth(null)*3;
		ySize = background.getHeight(null)*3;
		spaceLocation = new Point2D.Double(0, 0);
		trajectories = new ArrayList<Point2D.Double>();
		shots = new ArrayList<Line2D.Double>();
		fighterLocations = new ArrayList<Rectangle>(fighters);
		carrierLocations = new ArrayList<Rectangle>(carriers);
		
		newGame();
		
		pointer = new Point2D.Double(0, 0);
	}
	
	public static Point2D.Double getPoint(Point2D.Double p1,Point2D.Double p2,double speed,double accuracy){
		if((int)p1.x != (int)p2.x && (int)p1.y != (int)p2.y){
		double xdif = (p2.getX() - p1.getX());
		double ydif = (p2.getY() - p1.getY());
		double angle = 0;		// in radians
		
		angle = -Math.atan(ydif/xdif);
		if(xdif<0){
			if(ydif<0){
				angle += Math.PI;
			} else {
				angle -= +Math.PI;
			}
		}
		
		angle += (Main.random.nextGaussian()*accuracy);
		
		double xgain = 0;
		double ygain = 0;
		xgain = Math.cos(angle) * speed;
		ygain = -Math.sin(angle) * speed;
		

		
		return new Point2D.Double(xgain,ygain);
		}
		return new Point2D.Double(0,0);
	}

	public static double getAngle(Point2D.Double p1,Point2D.Double p2){
		
		if((int)p1.x != (int)p2.x && (int)p1.y != (int)p2.y){
			double xdif = (p2.getX() - p1.getX());
			double ydif = (p2.getY() - p1.getY());
			double angle = 0;		// in radians
		
			angle = -Math.atan(ydif/xdif);
			if(xdif<0){
				if(ydif<0){
					angle += Math.PI;
				} else {
					angle -= +Math.PI;
				}
			}
		
			return -angle;
		}
		return 0.0;
	}
	
	public static void playerDamage(int damage){
		Player.HPSTAT -= damage;
		if(Player.HPSTAT <= 0){
			Main.alive = false;
		}
	}
	
	public boolean finished(){
		return (fighters.size() == 0 && carriers.size() == 0 && !isCommander);
	}
	
	public void panUp(){
		if(spaceLocation.y < 0){
			spaceLocation.setLocation(spaceLocation.x, spaceLocation.y + Player.speedSTAT);
			yChange -= Player.speedSTAT;
		}else{
		}
	}
	public void panDown(){
		if(spaceLocation.y > Main.height - ySize){
			spaceLocation.setLocation(spaceLocation.x, spaceLocation.y - Player.speedSTAT);
			yChange += Player.speedSTAT;
		}
	}
	public void panLeft(){
		if(spaceLocation.x < 0){
			spaceLocation.setLocation(spaceLocation.x + Player.speedSTAT, spaceLocation.y);
			xChange -= Player.speedSTAT;
		}
	}
	public void panRight(){
		if(spaceLocation.x > Main.width - xSize){
			spaceLocation.setLocation(spaceLocation.x - Player.speedSTAT, spaceLocation.y);
			xChange += Player.speedSTAT;
		}
	}
	public void panUR(){
		if(spaceLocation.x <= Main.width - xSize){
			panUp();
		}else if(spaceLocation.y >= 0){
			panRight();
		}else{
			spaceLocation.setLocation(spaceLocation.x - (Player.speedSTAT*(1/Main.root2)), spaceLocation.y + (Player.speedSTAT*(1/Main.root2)));
			xChange += Player.speedSTAT*(1/Main.root2);
			yChange -= Player.speedSTAT*(1/Main.root2);
		}
	}
	public void panUL(){
		if(spaceLocation.x >= 0){
			panUp();
		}else if(spaceLocation.y >= 0){
			panLeft();
		}else{
			spaceLocation.setLocation(spaceLocation.x + (Player.speedSTAT*(1/Main.root2)), spaceLocation.y + (Player.speedSTAT*(1/Main.root2)));
			xChange -= Player.speedSTAT*(1/Main.root2);
			yChange -= Player.speedSTAT*(1/Main.root2);
		}
	}
	public void panDR(){
		if(spaceLocation.x <= Main.width - xSize){
			panDown();
		}else if(spaceLocation.y <= Main.height - ySize){
			panRight();
		}else{
			spaceLocation.setLocation(spaceLocation.x - (Player.speedSTAT*(1/Main.root2)), spaceLocation.y - (Player.speedSTAT*(1/Main.root2)));
			xChange += Player.speedSTAT*(1/Main.root2);
			yChange += Player.speedSTAT*(1/Main.root2);
		}
	}
	public void panDL(){
		if(spaceLocation.x >= 0){
			panDown();
		}else if(spaceLocation.y <= Main.height - ySize){
			panLeft();
		}else{
			spaceLocation.setLocation(spaceLocation.x + (Player.speedSTAT*(1/Main.root2)), spaceLocation.y - (Player.speedSTAT*(1/Main.root2)));
			xChange -= Player.speedSTAT*(1/Main.root2);
			yChange += Player.speedSTAT*(1/Main.root2);
		}
	}
 		
	public void shoot(Point mouseCoord){
		double accuracy = 1.0 / ((Player.accuracySTAT*3)+30);
		shots.add(new Line2D.Double(Main.width/2, Main.height/2,Main.width/2, Main.height/2));
		pointer.setLocation(mouseCoord.getX(),mouseCoord.getY());
		double speed = 20.0;
		trajectories.add(getPoint(new Point2D.Double(Main.width/2,Main.height/2),new Point2D.Double(mouseCoord.getX(), mouseCoord.getY()),speed,accuracy));
		Sound.laser.play();
			
	}
		
	public void newGame(){
		shots.clear();
		trajectories.clear();
		carriers.clear();
		fighters.clear();
		carrierLocations.clear();
		fighterLocations.clear();
		spaceLocation.setLocation(0, 0);
		xChange = 0;
		yChange = 0;

		newBattle = true;

		int planet = Main.random.nextInt(8);
		background = ResourceLoader.getImage("background/planet"+planet+".png");

		xSize = background.getWidth(null)*2;
		ySize = background.getHeight(null)*2;
		
		for (int i = 0; i < Main.random.nextInt(5)+5; i++) {

			int x = Main.random.nextInt(300)+50;
			int y = Main.random.nextInt(Main.height-100)+50;

			carrierLocations.add(new Rectangle(x,y,32,32));
			this.carriers.add(new Carrier(x,y));
			this.carriers.get(i).resetTime();
			for (int j = 0; j < Main.random.nextInt(5)+5; j++) {

				int x2 = x + Main.random.nextInt(50)-25;
				int y2 = y + Main.random.nextInt(50)-25;
				this.fighters.add(new Fighter(x2,y2,this.carriers.get(i)));
				this.fighters.get(i).resetTime();
				fighterLocations.add(new Rectangle(x2,y2,16,16));
			}
		}
		int x = Main.random.nextInt(300)+50;
		int y = Main.random.nextInt(Main.height-100)+50;
		commander = new Command(x,y);
		commanderLocation = new Rectangle(x, y, 128,32);
		commander.resetTime();
		
	}
	
	public void update(){
		
		if(newBattle){
			newGame();
			newBattle = false;
		}else{
		
		
		
		for(int i = 0; i < shots.size(); i++){
			
			shots.get(i).setLine(shots.get(i).getX1()+trajectories.get(i).getX(), shots.get(i).getY1()+trajectories.get(i).getY(),shots.get(i).getX1(), shots.get(i).getY1());
		}
		Rectangle rect = new Rectangle(0,0,Main.width,Main.height);
		for (int i = 0; i < shots.size(); i++) {
				if(!rect.contains(shots.get(i).getX2(), shots.get(i).getY2())){
					shots.remove(i);
					trajectories.remove(i);
				}
			
		}
		
		for (int i = 0; i < carriers.size(); i++) {
			if(carriers.get(i).isAlive()){
				carriers.get(i).update(xChange,yChange);
				carrierLocations.get(i).setLocation((int)carriers.get(i).getLocation().x,(int)carriers.get(i).getLocation().y);
			for (int j = 0; j < shots.size();j++) {
				if(carrierLocations.get(i).contains(shots.get(j).getP1())||carrierLocations.get(i).contains(shots.get(j).getP2())){
					carriers.get(i).takeDamage(100);
					shots.remove(j);
					trajectories.remove(j);
				}
			}
			}else{
				carrierLocations.remove(i);
				carriers.remove(i);
				Sound.explosion.play();
				i--;
			}
		}
		
		for (int i = 0; i < fighters.size(); i++) {
			if(fighters.get(i).isAlive()){
				fighters.get(i).update(xChange,yChange);
				fighterLocations.get(i).setLocation((int)fighters.get(i).getLocation().x,(int)fighters.get(i).getLocation().y);
			for (int j = 0; j < shots.size();j++) {
				if(fighterLocations.get(i).contains(shots.get(j).getP1())||fighterLocations.get(i).contains(shots.get(j).getP2())){
					fighters.get(i).takeDamage(100);
					shots.remove(j);
					trajectories.remove(j);
				}
			}
			}else{
				fighterLocations.remove(i);
				fighters.remove(i);
				Sound.explosion.play();
				i--;
			}
		}
		if(isCommander){
			for (int j = 0; j < shots.size();j++) {
				if(commanderLocation.contains(shots.get(j).getP1())||commanderLocation.contains(shots.get(j).getP2())){
					commander.takeDamage(100);
					shots.remove(j);
					trajectories.remove(j);
					if(!commander.isAlive()){
						isCommander = false;
					}
				}
			}
		}
		xChange = 0;
		yChange = 0;
		
		}
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		switch(Main.state){
		case SPACEBATTLE:

			g2d.drawImage(background, (int)spaceLocation.x, (int)spaceLocation.y, background.getWidth(null)*3, background.getHeight(null)*3, null);
			g2d.setColor(Color.red);
			g2d.fillRect(Main.width/2 - 500, 50, 1000, 20);
			g2d.setColor(Color.green);
			g2d.fillRect(Main.width/2 - (Player.HPSTAT - 500), 50, Player.HPSTAT, 20);
			for(int i = 0; i< shots.size(); i++){
				g2d.drawLine((int)shots.get(i).getX1(),(int)shots.get(i).getY1(),(int)shots.get(i).getX2(),(int)shots.get(i).getY2());
			}
			g2d.drawRect(Main.width/2 - 16, Main.height/2-16, 32, 32);
			g2d.drawRect((int)pointer.x, (int)pointer.y, 1, 1);

			for (Carrier carrier : carriers) {
				carrier.draw(g2d);
			}
			for (Fighter fighter : fighters) {
				fighter.draw(g2d);
			}
			if(isCommander){
			commander.draw(g2d);
			}
			break;
		default:
			break;
		}
			
	}

}

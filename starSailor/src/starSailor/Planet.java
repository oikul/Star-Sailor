package starSailor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Planet {

	private int size, x, y, numOfMoons, selectedMoon = -1, numOfStations, selectedStation = 0;
	private double distance, temperature, precipitation, angle, zoom = 1.0, zoomSurface = 1.0, xtrans, ytrans, xDif, yDif;
	private NoiseGenerator generator;
	private double[][] noise;
	private Block[][] terrain, decoration;
	private Rectangle2D.Double[][] blockRects;
	private Planet[] moons;
	private SpaceStation[] stations;
	private Biome biome;
	private Color color;
	private AffineTransform transform;
	private boolean selected = false, made = false, isMoon;
	private Rectangle playerRectUp = new Rectangle(Main.width/2 - 3, Main.height/2 - 1, 6, 2);
	private Rectangle playerRectLeft = new Rectangle(Main.width/2 - 5, Main.height/2 + 1, 2, 4);
	private Rectangle playerRectDown = new Rectangle(Main.width/2 - 3, Main.height/2 + 5, 6, 2);
	private Rectangle playerRectRight = new Rectangle(Main.width/2 + 3, Main.height/2 + 1, 2, 4);

	public Planet(int size, double distance, double angle, boolean isMoon){
		this.size = size;
		this.distance = distance;
		this.angle = angle;
		this.isMoon = isMoon;
		if(!isMoon){
			numOfMoons = Main.random.nextInt(5);
			numOfStations = 2;
		}
		generator = new NoiseGenerator(size * 10, size * 10, 4, 5);
		noise = generator.getNoise();
		calculateTemperature();
		calculatePrecipitation();
		calculateBiome();
		terrain = biome.buildTerrain(noise);
		decoration = biome.buildDecoration(noise);
		blockRects = new Rectangle2D.Double[terrain.length][terrain[0].length];
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain.length; j++){
				blockRects[i][j] = new Rectangle2D.Double(i*16, j*16, 16, 16);
			}
		}
		color = biome.getColor();
	}

	private void calculateTemperature(){
		temperature = Main.random.nextDouble();
	}

	private void calculatePrecipitation(){
		precipitation = Main.random.nextDouble();
	}

	private void calculateBiome(){
		if(temperature >= 0 && temperature < 0.2){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.polar_desert;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.ice_spikes;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.frozen_lakes;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.ice_sheet;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.ice_bergs;
			}
		}else if(temperature >= 0.2 && temperature < 0.4){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.tundra;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.mountain;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.taiga;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.mountain_forest;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.ocean;
			}
		}else if(temperature >= 0.4 && temperature < 0.6){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.steppe;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.plains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.forest;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.lakes;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.islands;
			}
		}else if(temperature >= 0.6 && temperature < 0.8){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.desert_plains;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.canyon;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.savannah;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.jungle;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.rainforest;
			}
		}else if(temperature >= 0.8 && temperature <= 1){
			if(precipitation >= 0 && precipitation < 0.2){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.2 && precipitation < 0.4){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.4 && precipitation < 0.6){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.6 && precipitation < 0.8){
				biome = Biome.volcanic_mountains;
			}else if(precipitation >= 0.8 && precipitation <= 1){
				biome = Biome.volcanic_mountains;
			}
		}
	}

	public int getSize(){
		return size;
	}

	private void translateBlockRects(double x, double y){
		for(int i = 0; i < blockRects.length; i++){
			for(int j = 0; j < blockRects.length; j++){
				if(blockRects[i][j] != null){
					blockRects[i][j].setRect(blockRects[i][j].x + x, blockRects[i][j].y + y, blockRects[i][j].width, blockRects[i][j].height);;
				}
			}
		}
	}

	public void checkForClick(int x, int y){
		if(Main.state == Main.State.PLANETRY){
			Point2D point = new Point2D.Double(x - 4, y - 4);
			transform.transform(point, point);
			for(int i = 0; i < moons.length; i ++){
				if(moons[i].getRect().intersects(new Rectangle((int) (point.getX()), (int) (point.getY()), 8, 8))){
					moons[i].setSelected(true);
					xDif = 0;
					yDif = 0;
					selectedMoon = i;
				}else{
					moons[i].setSelected(false);
				}
			}
			if(new Rectangle(Main.width/2 - size/5, Main.height/2 - size/5, size * 10, size * 10).contains(point.getX(), point.getY())){
				selectedMoon = -1;
				xDif = 0;
				yDif = 0;
			}
		}
	}

	public void panUp(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionUp()){
					yDif --;
					Player.pan(0, 1 * zoomSurface);
					translateBlockRects(0, 1);
				}
			}else{
				yDif --;
				Player.pan(0, 1 * zoomSurface);
				translateBlockRects(0, 1);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panUp();
			}else{
				if(!Player.isShip()){
					if(!collisionUp()){
						yDif --;
						Player.pan(0, 1 * zoomSurface);
						translateBlockRects(0, 1);
					}
				}else{
					yDif --;
					Player.pan(0, 1 * zoomSurface);
					translateBlockRects(0, 1);
				}
			}
		}
	}

	public void panLeft(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionLeft()){
					xDif --;
					Player.pan(1 * zoomSurface, 0);
					translateBlockRects(1, 0);
				}
			}else{
				xDif --;
				Player.pan(1 * zoomSurface, 0);
				translateBlockRects(1, 0);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panLeft();
			}else{
				if(!Player.isShip()){
					if(!collisionLeft()){
						xDif --;
						Player.pan(1 * zoomSurface, 0);
						translateBlockRects(1, 0);
					}
				}else{
					xDif --;
					Player.pan(1 * zoomSurface, 0);
					translateBlockRects(1, 0);
				}
			}
		}
	}

	public void panDown(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionDown()){
					yDif ++;
					Player.pan(0, -1 * zoomSurface);
					translateBlockRects(0, -1);
				}
			}else{
				yDif ++;
				Player.pan(0, -1 * zoomSurface);
				translateBlockRects(0, -1);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panDown();
			}else{
				if(!Player.isShip()){
					if(!collisionDown()){
						yDif ++;
						Player.pan(0, -1 * zoomSurface);
						translateBlockRects(0, -1);
					}
				}else{
					yDif ++;
					Player.pan(0, -1 * zoomSurface);
					translateBlockRects(0, -1);
				}
			}
		}
	}

	public void panRight(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionRight()){
					xDif ++;
					Player.pan(-1 * zoomSurface, 0);
					translateBlockRects(-1, 0);
				}
			}else{
				xDif ++;;
				Player.pan(-1 * zoomSurface, 0);
				translateBlockRects(-1, 0);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panRight();
			}else{
				if(!Player.isShip()){
					if(!collisionRight()){
						xDif ++;
						Player.pan(-1 * zoomSurface, 0);
						translateBlockRects(-1, 0);
					}
				}else{
					xDif ++;;
					Player.pan(-1 * zoomSurface, 0);
					translateBlockRects(-1, 0);
				}
			}
		}
	}

	public void panUR(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionUp() && !collisionRight()){
					xDif += 1/Main.root2;
					yDif -= 1/Main.root2;
					Player.pan((1/-Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
					translateBlockRects(-1/Main.root2, 1/Main.root2);
				}else if(!collisionUp()){
					panUp();
				}else if(!collisionRight()){
					panRight();
				}
			}else{
				xDif += 1/Main.root2;
				yDif -= 1/Main.root2;
				Player.pan((1/-Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
				translateBlockRects(-1/Main.root2, 1/Main.root2);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panUR();
			}else{
				if(!Player.isShip()){
					if(!collisionUp() && !collisionRight()){
						xDif += 1/Main.root2;
						yDif -= 1/Main.root2;
						Player.pan((1/-Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
						translateBlockRects(-1/Main.root2, 1/Main.root2);
					}else if(!collisionUp()){
						panUp();
					}else if(!collisionRight()){
						panRight();
					}
				}else{
					xDif += 1/Main.root2;
					yDif -= 1/Main.root2;
					Player.pan((1/-Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
					translateBlockRects(-1/Main.root2, 1/Main.root2);
				}
			}
		}

	}

	public void panUL(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionUp() && !collisionLeft()){
					xDif -= 1/Main.root2;
					yDif -= 1/Main.root2;
					Player.pan((1/Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
					translateBlockRects(1/Main.root2, 1/Main.root2);
				}else if(!collisionUp()){
					panUp();
				}else if(!collisionLeft()){
					panLeft();
				}
			}else{
				xDif -= 1/Main.root2;
				yDif -= 1/Main.root2;
				Player.pan((1/Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
				translateBlockRects(1/Main.root2, 1/Main.root2);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panUL();
			}else{
				if(!Player.isShip()){
					if(!collisionUp() && !collisionLeft()){
						xDif -= 1/Main.root2;
						yDif -= 1/Main.root2;
						Player.pan((1/Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
						translateBlockRects(1/Main.root2, 1/Main.root2);
					}else if(!collisionUp()){
						panUp();
					}else if(!collisionLeft()){
						panLeft();
					}
				}else{
					xDif -= 1/Main.root2;
					yDif -= 1/Main.root2;
					Player.pan((1/Main.root2) * zoomSurface, (1/Main.root2) * zoomSurface);
					translateBlockRects(1/Main.root2, 1/Main.root2);
				}
			}
		}
	}

	public void panDR(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionDown() && !collisionRight()){
					xDif += 1/Main.root2;
					yDif += 1/Main.root2;
					Player.pan((1/-Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
					translateBlockRects(-1/Main.root2, -1/Main.root2);
				}else if(!collisionDown()){
					panDown();
				}else if(!collisionRight()){
					panRight();
				}
			}else{
				xDif += 1/Main.root2;
				yDif += 1/Main.root2;
				Player.pan((1/-Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
				translateBlockRects(-1/Main.root2, -1/Main.root2);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panDR();
			}else{
				if(!Player.isShip()){
					if(!collisionDown() && !collisionRight()){
						xDif += 1/Main.root2;
						yDif += 1/Main.root2;
						Player.pan((1/-Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
						translateBlockRects(-1/Main.root2, -1/Main.root2);
					}else if(!collisionDown()){
						panDown();
					}else if(!collisionRight()){
						panRight();
					}
				}else{
					xDif += 1/Main.root2;
					yDif += 1/Main.root2;
					Player.pan((1/-Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
					translateBlockRects(-1/Main.root2, -1/Main.root2);
				}
			}
		}
	}

	public void panDL(){
		if(Main.state == Main.State.PLANETRY || Main.state == Main.State.SURFACE){
			if(!Player.isShip()){
				if(!collisionDown() && !collisionLeft()){
					xDif -= 1/Main.root2;
					yDif += 1/Main.root2;
					Player.pan((1/Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
					translateBlockRects(1/Main.root2, -1/Main.root2);
				}else if(!collisionDown()){
					panDown();
				}else if(!collisionLeft()){
					panLeft();
				}
			}else{
				xDif -= 1/Main.root2;
				yDif += 1/Main.root2;
				Player.pan((1/Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
				translateBlockRects(1/Main.root2, 1/-Main.root2);
			}
		}else if(Main.state == Main.State.MOON){
			if(!isMoon){
				moons[selectedMoon].panDL();
			}else{
				if(!Player.isShip()){
					if(!collisionDown() && !collisionLeft()){
						xDif -= 1/Main.root2;
						yDif += 1/Main.root2;
						Player.pan((1/Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
						translateBlockRects(1/Main.root2, -1/Main.root2);
					}else if(!collisionDown()){
						panDown();
					}else if(!collisionLeft()){
						panLeft();
					}
				}else{
					xDif -= 1/Main.root2;
					yDif += 1/Main.root2;
					Player.pan((1/Main.root2) * zoomSurface, (1/-Main.root2) * zoomSurface);
					translateBlockRects(1/Main.root2, 1/-Main.root2);
				}
			}
		}
	}

	private void incrementAngle(){
		if(angle < 360){
			angle += 0.001;
		}else{
			angle = 0;
		}
	}

	private void calculateXAndY(){
		if(angle >= 0 && angle < 90){
			x = (int) (Main.width/2 + distance*Math.cos(angle));
			y = (int) (Main.height/2 - distance*Math.sin(angle));
		}else if(angle >= 90 && angle < 180){
			x = (int) (Main.width/2 - distance*Math.sin(angle - 90));
			y = (int) (Main.height/2 - distance*Math.cos(angle - 90));
		}else if(angle >= 180 && angle < 270){
			x = (int) (Main.width/2 - distance*Math.cos(angle - 180));
			y = (int) (Main.height/2 + distance*Math.sin(angle - 180));
		}else if(angle >= 270 && angle < 360){
			x = (int) (Main.width/2 + distance*Math.sin(angle - 270));
			y = (int) (Main.height/2 + distance*Math.cos(angle - 270));
		}
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Rectangle getRect(){
		return new Rectangle(x, y, size, size);
	}

	public void zoomIn(){
		if(zoom < 3){
			zoom += 0.3;
			xDif = 0;
			yDif = 0;
		}else{
			if(selectedMoon >= 0){
				Main.state = Main.State.MOON;
			}else{
				Main.state = Main.State.SURFACE;
			}
		}
	}

	public void zoomOut(){
		if(zoom > 0.4){
			zoom -= 0.3;
			xDif = 0;
			yDif = 0;
		}else{
			if(isMoon){
				Main.state = Main.State.PLANETRY;
			}else{
				Main.state = Main.State.SOLAR;
			}
		}
	}

	public void zoom(boolean in){
		if(Main.state == Main.State.PLANETRY){
			if(in){
				zoomIn();
			}else{
				zoomOut();
			}
		}else if(Main.state == Main.State.SURFACE || Main.state == Main.State.MOON){
			if(!in){
				if(Player.isShip()){
					Main.state = Main.State.PLANETRY;
				}
			}
		}
	}

	private void getXTrans(){
		double x;
		if(selectedMoon >= 0){
			x = moons[selectedMoon].getX() + xDif;
		}else{
			x = Main.width/2 + xDif;
		}
		if(x > Main.width/(zoom*2)){
			xtrans = -(x - Main.width/(zoom*2));
		}else{
			xtrans = Main.width/(zoom*2) - x;
		}
	}

	private void getYTrans(){
		double y;
		if(selectedMoon >= 0){
			y = moons[selectedMoon].getY() + yDif;
		}else{
			y = Main.height/2 + yDif;
		}
		if(y > Main.height/(zoom*2)){
			ytrans = -(y - Main.height/(zoom*2));
		}else{
			ytrans = Main.height/(zoom*2) - y;
		}
	}

	private void createMoons(){
		moons = new Planet[numOfMoons];
		stations = new SpaceStation[numOfStations];
		for(int i = 0; i < numOfMoons; i++){
			moons[i] = new Planet(Main.random.nextInt(9) + 3, (Main.random.nextDouble() * (Main.height/2 - size * 5)) + (size * 5), Main.random.nextDouble() * 360, true);
		}
		for(int i = 1; i < numOfStations; i++){
			stations[i] = new SpaceStation((Main.random.nextDouble() * (Main.height/2 - size * 5)) + (size * 5), Main.random.nextDouble() * 360);
		}
		made = true;
	}

	private boolean collisionUp(){
		boolean collided = false;
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[0].length; j++){
				if(decoration[i][j] != null){
					if(playerRectUp.intersects(blockRects[i][j]) && (terrain[i][j].isSolid() || decoration[i][j].isSolid())){
						collided = true;
					}
				}else{
					if(playerRectUp.intersects(blockRects[i][j]) && terrain[i][j].isSolid()){
						collided = true;
					}
				}
			}
		}
		return collided;
	}

	private boolean collisionLeft(){
		boolean collided = false;
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[0].length; j++){
				if(decoration[i][j] != null){
					if(playerRectLeft.intersects(blockRects[i][j]) && (terrain[i][j].isSolid() || decoration[i][j].isSolid())){
						collided = true;
					}
				}else{
					if(playerRectLeft.intersects(blockRects[i][j]) && terrain[i][j].isSolid()){
						collided = true;
					}
				}
			}
		}
		return collided;
	}

	private boolean collisionDown(){
		boolean collided = false;
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[0].length; j++){
				if(decoration[i][j] != null){
					if(playerRectDown.intersects(blockRects[i][j]) && (terrain[i][j].isSolid() || decoration[i][j].isSolid())){
						collided = true;
					}
				}else{
					if(playerRectDown.intersects(blockRects[i][j]) && terrain[i][j].isSolid()){
						collided = true;
					}
				}
			}
		}
		return collided;
	}

	private boolean collisionRight(){
		boolean collided = false;
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[0].length; j++){
				if(decoration[i][j] != null){
					if(playerRectRight.intersects(blockRects[i][j]) && (terrain[i][j].isSolid() || decoration[i][j].isSolid())){
						collided = true;
					}
				}else{
					if(playerRectRight.intersects(blockRects[i][j]) && (terrain[i][j].isSolid())){
						collided = true;
					}
				}
			}
		}
		return collided;
	}

	public void update(){
		switch(Main.state){
		case PLANETRY:
			if(isMoon){
				incrementAngle();
				calculateXAndY();
			}else{
				if(!made){
					createMoons();
				}
				for(int i = 0; i < moons.length; i++){
					moons[i].update();
				}
			}
			break;
		case SOLAR:
			incrementAngle();
			calculateXAndY();
			break;
		case SURFACE:
			Block.water_murky.update();
			Block.water_ocean.update();
			Block.water_river.update();
			Block.lava.update();
			break;
		case MOON:
			if(isMoon){
				Block.water_murky.update();
				Block.water_ocean.update();
				Block.water_river.update();
				Block.lava.update();
			}else{
				moons[selectedMoon].update();
			}
		default:
			break;
		}
	}

	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAt;
		AffineTransform at;
		switch(Main.state){
		case SOLAR:
			if(selected){
				g2d.setColor(Color.cyan);
				g2d.drawRect(x - size/2, y - size/2, size, size);
			}
			g2d.setColor(color);
			g2d.fillOval(x - size/2, y - size/2, size, size);
			break;
		case PLANETRY:
			saveAt = g2d.getTransform();
			if(isMoon){
				if(selected){
					g2d.setColor(Color.cyan);
					g2d.drawRect(x - size / 2, y - size / 2, size, size);
				}
				g2d.setColor(color);
				g2d.fillOval(x - size/2, y - size/2, size, size);
			}else{
				at = new AffineTransform();
				at.scale(zoom, zoom);
				getXTrans();
				getYTrans();
				at.translate(xtrans, ytrans);
				try {
					transform = at.createInverse();
				} catch (NoninvertibleTransformException e) {
					e.printStackTrace();
				}
				g2d.setTransform(at);
				if(selectedMoon == -1){
					g2d.setColor(Color.cyan);
					g2d.drawRect((Main.width/2) - size * 5, (Main.height/2) - size * 5, size*10, size*10);
				}
				g2d.setColor(color);
				g2d.fillOval((Main.width/2) - size * 5, (Main.height/2) - size * 5, size*10, size*10);
				if(!made){
					createMoons();
				}
				for(int i = 0; i < moons.length; i++){
					moons[i].draw(g2d);
				}
				for(int i = 1; i < stations.length; i++){
					stations[i].draw(g2d);
				}
			}
			g2d.setTransform(saveAt);
			break;
		case SURFACE:
			saveAt = g2d.getTransform();
			at = new AffineTransform();
			at.scale(zoomSurface, zoomSurface);
			at.translate(-xDif, -yDif);
			g2d.setTransform(at);
			for(int i = 0; i < noise.length; i++){
				for(int j = 0; j < noise[0].length; j++){
					if(blockRects[i][j].intersects(new Rectangle(0, 0, Main.width, Main.height))){
						terrain[i][j].draw(g2d, i * 16, j * 16);
					}
				}
			}
			for(int i = 0; i < noise.length; i++){
				for(int j = 0; j < noise[0].length; j++){
					if(decoration[i][j] != null && blockRects[i][j].intersects(new Rectangle(0, 0, Main.width, Main.height))){
						decoration[i][j].draw(g2d, i * 16, j * 16);
					}
				}
			}
			g2d.setTransform(saveAt);
			break;
		case MOON:
			if(isMoon){
				saveAt = g2d.getTransform();
				at = new AffineTransform();
				at.translate(-xDif, -yDif);
				g2d.setTransform(at);
				for(int i = 0; i < noise.length; i++){
					for(int j = 0; j < noise[0].length; j++){
						if(blockRects[i][j].intersects(new Rectangle(0, 0, Main.width, Main.height))){
							terrain[i][j].draw(g2d, i * 16, j * 16);
						}
					}
				}
				for(int i = 0; i < noise.length; i++){
					for(int j = 0; j < noise[0].length; j++){
						if(decoration[i][j] != null){
							if(blockRects[i][j].intersects(new Rectangle(0, 0, Main.width, Main.height))){
								decoration[i][j].draw(g2d, i * 16, j * 16);
							}
						}
					}
				}
				g2d.setTransform(saveAt);
			}else{
				moons[selectedMoon].draw(g2d);
			}
		default:
			break;
		}

	}

}

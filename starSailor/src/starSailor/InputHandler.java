package starSailor;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class InputHandler implements KeyListener, MouseListener, MouseWheelListener{
	
	private boolean[] keyArray = new boolean[256];
	private boolean[] mouseArray = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean overComp, mouseWheelUp = false, mouseWheelDown = false;
	private String typedAcum = "";
	private Component c;
	
	public InputHandler(Component c){
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseWheelListener(this);
		this.c = c;
	}
	
	public Point getMousePositionOnScreen(){
		
		if(MouseInfo.getPointerInfo().getLocation().x+16 > Main.width-16){
			try {
				new Robot().mouseMove(Main.width-32, MouseInfo.getPointerInfo().getLocation().y);
			} catch (HeadlessException | AWTException e) {
			}
			if(MouseInfo.getPointerInfo().getLocation().y+16 > Main.height-16){
				try {
					new Robot().mouseMove(Main.width-32,Main.height-32);
				} catch (HeadlessException | AWTException e) {
				}
				Point point = new Point(Main.width-16, MouseInfo.getPointerInfo().getLocation().y+16);
				return point;
			}
			Point point = new Point(Main.width-16, MouseInfo.getPointerInfo().getLocation().y+16);
			return point;
		}else if(MouseInfo.getPointerInfo().getLocation().y+16 > Main.height-16){
			
			try {
				new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x,Main.height-32);
			} catch (HeadlessException | AWTException e) {
			}
			Point point = new Point(MouseInfo.getPointerInfo().getLocation().x+16, Main.height-16);
			return point;
		}
		
		Point point = new Point(MouseInfo.getPointerInfo().getLocation().x+16, MouseInfo.getPointerInfo().getLocation().y+16);
		return point;
	}
	
	public Point getMousePositionRelativeToComponent(){
		Point point = new Point(c.getMousePosition().x+16, c.getMousePosition().y+16);
		return point;
	}
	
	public boolean isKeyDown(int keyCode){
		return keyArray[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyArray[e.getKeyCode()] = true;
	}
	
	public void artificialKeyPressed(int keyCode) {
		keyArray[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyArray[e.getKeyCode()] = false;
	}
	
	public void artificialKeyReleased(int keyCode) {
		keyArray[keyCode] = false;
	}
	
	public String getTyperAcum(){
		return typedAcum;
	}
	
	public void clearTypedAcum(){
		typedAcum = "";
	}

	@Override
	public void keyTyped(KeyEvent e) {
		typedAcum += e.getKeyChar();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public boolean isMouseOverComp(){
		return overComp;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		overComp = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		overComp = false;
	}
	
	public boolean isMouseDown(int mouseButton){
		return mouseArray[mouseButton];
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseArray[e.getButton()] = true;
	}
	
	public void artificialMousePressed(int mouseButton) {
		mouseArray[mouseButton] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseArray[e.getButton()] = false;
	}
	
	public void artificialMouseReleased(int mouseButton) {
		mouseArray[mouseButton] = false;
	}
	
	public boolean getMouseWheelUp(){
		return mouseWheelUp;
	}
	
	public boolean getMouseWheelDown(){
		return mouseWheelDown;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0){
			mouseWheelUp = true;
			mouseWheelDown = false;
		}else if (e.getWheelRotation() > 0){
			mouseWheelUp = false;
			mouseWheelDown = true;
		}else{
			mouseWheelUp = false;
			mouseWheelDown = false;
		}
	}
	
	public void stopMouseWheel(){
		mouseWheelUp = false;
		mouseWheelDown = false;
	}

}

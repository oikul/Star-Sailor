package starSailor;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener{
	
	private boolean[] keyArray = new boolean[256];
	private boolean[] mouseArray = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean overComp;
	private String typedAcum = "";
	private Component c;
	
	public InputHandler(Component c){
		c.addKeyListener(this);
		c.addMouseListener(this);
		this.c = c;
	}
	
	public Point getMousePositionOnScreen(){
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	public Point getMousePositionRelativeToComponent(){
		return c.getMousePosition();
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
	
	public void artificalMousePressed(int mouseButton) {
		mouseArray[mouseButton] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseArray[e.getButton()] = false;
	}
	
	public void artificalMouseReleased(int mouseButton) {
		mouseArray[mouseButton] = false;
	}

}

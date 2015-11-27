package starSailor;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceLoader {
	
	private static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String path){
		try{
			URL url = rl.getClass().getClassLoader().getResource("resources/" + path);
			return new ImageIcon(url).getImage();
		}catch (Exception e){
			return null;
		}
		
	}

}

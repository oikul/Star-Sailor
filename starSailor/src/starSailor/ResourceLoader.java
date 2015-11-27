package starSailor;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader {
	
	private static ResourceLoader rl;
	
	public static Image getImage(String path){
		return Toolkit.getDefaultToolkit().createImage(rl.getClass().getResource("/resources/" + path));
	}

}

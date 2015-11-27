package starSailor;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ResourceLoader {
	
	private static ResourceLoader rl;
	
	public static Image getImage(String path){
		URL url = rl.getClass().getResource("resources/" + path);
		return Toolkit.getDefaultToolkit().getImage(url);
	}

}

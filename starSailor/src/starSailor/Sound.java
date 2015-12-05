package starSailor;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	
	public static final Sound laser = new Sound("sound/laser/laser.wav");
	public static final Sound explosion = new Sound("sound/explosion/explosion.wav");
	
	private Clip clip;
	
	public Sound(String path){
		try {
			AudioInputStream sound = ResourceLoader.getSound(path);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop(){
		clip.stop();
	}
	
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}

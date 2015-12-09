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
	public static final Sound ghostHurt = new Sound("sound/ghost_hurt.wav");
	public static final Sound ghost = new Sound("sound/ghost.wav");
	public static final Sound ghostLaughter = new Sound("sound/ghost_laughter.wav");
	public static final Sound music = new Sound("sound/some_space_music.wav");
	
	private Clip clip, clip1, clip2;
	
	public Sound(String path){
		try {
			AudioInputStream sound = ResourceLoader.getSound(path);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
			clip1 = (Clip) AudioSystem.getLine(info);
			clip1.open(sound);
			clip2 = (Clip) AudioSystem.getLine(info);
			clip2.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		if(clip.isActive()){
			if(clip1.isActive()){
				if(clip2.isActive()){
					clip.setFramePosition(0);
					clip.start();
				}else{
					clip2.setFramePosition(0);
					clip2.start();
				}
			}else{
				clip1.setFramePosition(0);
				clip1.start();
			}
		}else{
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stop(){
		clip.stop();
	}
	
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}

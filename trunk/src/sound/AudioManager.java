package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioManager {
	static public Clip[] milk = new Clip[2];
	static public Clip sweat;
	static public Clip hurt;
	static public Clip scream;
	static public Clip fatality;
	
	static public void loadSounds() {
		milk[0] = fromFile("sounds/milk_1.wav");
		milk[1] = fromFile("sounds/milk_2.wav");
		sweat = fromFile("sounds/sweat.wav");
		hurt = fromFile("sounds/hurt.wav");
		scream = fromFile("sounds/schrei.wav");
		fatality = fromFile("sounds/fatality.wav");
	}

	static private Clip fromFile(String filename) {
		AudioInputStream stream;
		try {
			stream = AudioSystem.getAudioInputStream(new File(filename));
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(stream);
			return clip;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public void playSound(Clip clip) {
		if (clip == null) {
			return;
		}
		if (clip.isRunning()) {
            clip.stop();		
		}
		clip.setFramePosition(0);
		clip.start();
	}
	
	static public void playSoundWait(Clip clip) {
		if (clip == null) {
			return;
		}
		if (!clip.isRunning()) {
			clip.setFramePosition(0);		
		}
		clip.start();		
	}
	
}

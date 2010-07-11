package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioManager {
	static public Clip[] milk = new Clip[2];
	
	static public void loadSounds() {
		milk[0] = fromFile("sounds/milk_1.wav");
		milk[1] = fromFile("sounds/milk_2.wav");
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
	
}

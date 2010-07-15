package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MP3Player {
	static public AdvancedPlayer music;
	
	static public void loadMusic() {
		try {
			music = new AdvancedPlayer(new FileInputStream("music/BurningSun.mp3"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} 
	}
	
	static public void play(AdvancedPlayer p) {
		try {
			p.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
}

package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MP3Player {
	static public void playMusic() {
		try {
			FileInputStream music = music = new FileInputStream("music/BurningSun.mp3");
			AdvancedPlayer p = new AdvancedPlayer(music);
			p.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

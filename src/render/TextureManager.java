package render;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class TextureManager {

	static public Texture horst_stand_tex;
	static public Texture horst_walk_tex;	
	static public Texture horst_stand_sweat_tex;
	static public Texture horst_walk_sweat_tex;		
	static public Texture horst_burns_tex;
	static public Texture background;
	static public Texture sunmilk_tex;
	static public Texture[] fatwoman_tex = new Texture[2];
	static public Texture crab_tex;
	static public Texture radio_tex;
	static public Texture plane_tex;
	static public Texture life_full_tex;
	static public Texture life_empty_tex;
	static public Texture shower_tex;
	static public Texture[] go_tex = new Texture[4];

	static public void loadTextures() {
		try {
			horst_stand_tex = loadTexture("images/animations/horst_stand.png");
			horst_walk_tex = loadTexture("images/animations/horst_walk.png");

			horst_stand_sweat_tex = loadTexture("images/animations/horst_stand_schwitz.png");		
			horst_walk_sweat_tex = loadTexture("images/animations/horst_walk_schwitz.png");
			
			horst_burns_tex = loadTexture("images/animations/horst_brennt.png");
			
			background = loadTexture("images/background/strand.png");
			background.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			
			sunmilk_tex = loadTexture("images/static/sunmilk.png");
			
			fatwoman_tex[0] = loadTexture("images/animations/frau.png");			
			fatwoman_tex[1] = loadTexture("images/animations/frau2.png");			
			crab_tex = loadTexture("images/animations/crab.png");	
			shower_tex = loadTexture("images/animations/dusche.png");			
			radio_tex = loadTexture("images/animations/radio.png");
			
			plane_tex = loadTexture("images/animations/flugzeug.png");
			
			life_full_tex = loadTexture("images/ui/leiste_voll.png");
			life_empty_tex = loadTexture("images/ui/leiste_leer.png");	
			
			go_tex[0] = loadTexture("images/ui/GO1.png");
			go_tex[1] = loadTexture("images/ui/GO2.png");
			go_tex[2] = loadTexture("images/ui/GO3.png");
			go_tex[3] = loadTexture("images/ui/GO4.png");
		} catch (GLException e) {
			e.printStackTrace();
		}
	}
	
	static private Texture loadTexture(String filename) {
		Texture tex;
		try {
			tex = TextureIO.newTexture(new File(filename), false);
			tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			return tex;
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

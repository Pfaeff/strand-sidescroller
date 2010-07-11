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
	static public Texture background;
	static public Texture sunmilk_tex;
	static public Texture[] fatwoman_tex = new Texture[2];
	static public Texture life_full_tex;
	static public Texture life_empty_tex;

	static public void loadTextures() {
		try {
			horst_stand_tex = TextureIO.newTexture(new File("images/animations/horst_stand.png"), false);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		
			horst_walk_tex = TextureIO.newTexture(new File("images/animations/horst_walk.png"), false);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
		
			background = TextureIO.newTexture(new File("images/background/strand.png"), false);
			background.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			background.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);		
			background.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			
			
			sunmilk_tex = TextureIO.newTexture(new File("images/static/sunmilk.png"), false);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
			
			fatwoman_tex[0] = TextureIO.newTexture(new File("images/animations/frau.png"), false);
			fatwoman_tex[0].setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			fatwoman_tex[0].setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

			fatwoman_tex[1] = TextureIO.newTexture(new File("images/animations/frau2.png"), false);
			fatwoman_tex[1].setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			fatwoman_tex[1].setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			life_full_tex = TextureIO.newTexture(new File("images/ui/leiste_voll.png"), false);
			life_full_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			life_full_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			life_empty_tex = TextureIO.newTexture(new File("images/ui/leiste_leer.png"), false);
			life_empty_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			life_empty_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);			
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

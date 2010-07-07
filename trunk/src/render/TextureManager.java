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
	static public Texture fatwoman1_tex;
	static public Texture fatwoman2_tex;
	
	static public void loadTextures() {
		try {
			horst_stand_tex = TextureIO.newTexture(new File("images/animations/horst_stand.jpg"), false);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		
			horst_walk_tex = TextureIO.newTexture(new File("images/animations/horst_walk.jpg"), false);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
		
			background = TextureIO.newTexture(new File("images/background/strand.png"), false);
			background.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			background.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);		
		
			sunmilk_tex = TextureIO.newTexture(new File("images/static/sunmilk.jpg"), false);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
			
			fatwoman1_tex = TextureIO.newTexture(new File("images/animations/frau.png"), false);
			fatwoman1_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			fatwoman1_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

			fatwoman2_tex = TextureIO.newTexture(new File("images/animations/frau2.png"), false);
			fatwoman2_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			fatwoman2_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);			
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
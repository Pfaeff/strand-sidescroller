package render;

import java.io.File;
import java.io.IOException;

import game.Game;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

/**
 * Kümmert sich um das Rendern der Szene
 * 
 * @author Kai
 */
public class Renderer implements GLEventListener {
	
	final private Game game;
	final private int width;
	final private int height;
	
	private Texture testTexture;
	
	/**
	 * Konstruktor
	 * 
	 * @param width Fensterbreite
	 * @param height Fensterhöhe
	 */
	public Renderer(Game game, int width, int height) {
		this.game = game;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Initialisiert OpenGL
	 */
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.setSwapInterval(0);
		
		// Tiefentest aktivieren
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		// Backface Culling aktivieren
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
		// 2D Texturen aktivieren
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		// TEEEST
		try {
			testTexture = TextureIO.newTexture(new File("images/background/strand.png"), false);
			testTexture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			testTexture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Startet den 2D-Modus
	 */
	private void Enter2DMode(GL gl) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
		// statt width und height kann hier auch ein statischer Wert gewählt werden,
		// dann wird das bild immer automatisch gestreckt
		gl.glOrtho(0, width, 0, height, 0, 128);
	}

	/**
	 * Sorgt dafür, dass alles neugezeichnet wird
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		Enter2DMode(gl);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();		
		
		// TEST
		testTexture.enable();
		testTexture.bind();		
		game.draw(gl, width, height);
	}

	/**
	 * Wird bei Änderungen am Fenster aufgerufen
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
	}

	/**
	 * Wird bei Änderungen des Displays aufgerufen
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}

}

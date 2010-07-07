package render;

import game.Game;

import java.io.File;
import java.io.IOException;

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
	
	public Texture sunmilk_tex;
	
	Background bg;
	
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
		
		/*
		 * Texturen laden
		 */
		Texture horst_stand_tex;
		Texture horst_walk_tex;
		Texture background;
		try {
			horst_stand_tex = TextureIO.newTexture(new File("images/animations/horst_stand.jpg"), false);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_stand_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			Animation horst_stand = new Animation(horst_stand_tex, 5000, 4, 1, true);	
			game.getPlayer().setStandAnimation(horst_stand);

			
			horst_walk_tex = TextureIO.newTexture(new File("images/animations/horst_walk.jpg"), false);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			horst_walk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			Animation horst_walk = new Animation(horst_walk_tex, 1000, 4, 1, true);	
			game.getPlayer().setWalkAnimation(horst_walk);			
			
			background = TextureIO.newTexture(new File("images/background/strand.png"), false);
			background.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			background.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
			
			bg = new Background(background, width, height);		
			
			sunmilk_tex = TextureIO.newTexture(new File("images/static/sunmilk.jpg"), false);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			sunmilk_tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		
		bg.render(gl);
		game.render(this, gl, width, height);
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

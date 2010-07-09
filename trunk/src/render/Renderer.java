package render;

import engine.GameTimer;
import game.Game;

import java.awt.Frame;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * Kümmert sich um das Rendern der Szene
 * 
 * @author Kai
 */
public class Renderer implements GLEventListener {
	
	private Game game;
	final private Frame frame;
	final private int width;
	final private int height;
	
	Background bg;
	
	/**
	 * Konstruktor
	 * 
	 * @param width Fensterbreite
	 * @param height Fensterhöhe
	 */
	public Renderer(Frame frame, int width, int height) {
		this.frame = frame;
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
		TextureManager.loadTextures();
		// Hintergrund erzeugen (das muss noch irgendwo anders rein)
		bg = new Background(TextureManager.background, width, height);		
		
		// Game erzeugen (muss leider erfolgen, nachdem ein OpenGL-Thread steht)
		GameTimer gameTimer = new GameTimer();
		game = new Game(gameTimer);
		
		// Tastensteuerung
		frame.addKeyListener(game);				
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
		game.updateGame();
		
		GL gl = drawable.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		Enter2DMode(gl);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();		
		
		bg.render(gl);
		game.render(this, gl, width, height);
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

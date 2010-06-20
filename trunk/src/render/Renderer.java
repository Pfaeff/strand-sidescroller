package render;

import game.Game;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * Kümmert sich um das Rendern der Szene
 * 
 * @author Kai
 */
public class Renderer implements GLEventListener {
	
	final private Game game;
	final private int width;
	final private int height;
	
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
		
		/*
		 * Test (ein Dreieck)
		 */
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_TRIANGLES);
			gl.glVertex3f(width/2-100, height/2-100, 0);  
			gl.glVertex3f(width/2+100, height/2-100, 0);
			gl.glVertex3f(width/2    , height/2+100, 0);
		gl.glEnd();
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

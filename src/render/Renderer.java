package render;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class Renderer implements GLEventListener {
	
	/**
	 * Initialisiert OpenGL
	 */
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.setSwapInterval(0);

	}

	/**
	 * Sorgt dafür, dass alles neugezeichnet wird
	 */
	public void display(GLAutoDrawable drawable) {

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

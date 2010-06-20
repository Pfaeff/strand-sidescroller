package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.*;

import render.Renderer;

import com.sun.opengl.util.Animator;

public class Horst {

	/**
	 * Main Methode
	 * @param args Wird nicht ben�tigt
	 */
	public static void main(String[] args) {
		Frame frame = new Frame("Horst brennt");
		
		// OpenGL Capabilites
		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);
		
		// OpenGL Zeichenfl�che
		GLCanvas canvas = new GLCanvas(caps);

		// Objekt zum Initialisieren/Neuzeichnen von OpenGL
		canvas.addGLEventListener(new Renderer());
		frame.add(canvas);
		
		// Fenstergr��e
		frame.setSize(512, 512);
		
		// Thread in dem gezeichnet wird
		final Animator animator = new Animator(canvas);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		// Fenster anzeigen
		frame.setVisible(true);
		
		// OpenGL-Thread starten
		animator.start();
	}
}
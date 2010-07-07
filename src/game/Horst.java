package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.Animator;

import render.Renderer;

/**
 * Initialisiert notwendige Teilsysteme und stellt das ausführbare Programm dar
 * 
 * @author Kai
 */
public class Horst {
	private Frame frame;
	private GLCanvas canvas;
	
	final private static int width = 1000;
	final private static int height = 500; 
	
	public Horst() {
		// Fenster initialisieren
		initFrame();
		// OpenGL initialisieren
		initGL();		
		// Objekt zum Initialisieren/Neuzeichnen von OpenGL
		canvas.addGLEventListener(new Renderer(frame, width, height));	
		frame.add(canvas);			
		// Spiel laufen lassen
		runGame();
		// Fenster anzeigen
		frame.setVisible(true);				
	}
	
	private void initFrame() {
		frame = new Frame("Horst brennt");
		// Fenstergröße
		frame.setSize(width, height);
		frame.setResizable(false);		
		// Fenster zentrieren
		frame.setLocationRelativeTo(null);		
	}
	
	private void initGL() {
		// OpenGL Capabilites
		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);		
		// OpenGL Zeichenfläche
		canvas = new GLCanvas(caps);	
	}
	
	private void runGame() {
		// Spielschleife
		final Animator gameThread = new Animator(canvas);		
		// Thread in dem gezeichnet wird
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						gameThread.stop();
						System.exit(0);
					}
				}).start();
			}
		});		
		gameThread.start();				
	}

	/**
	 * Main Methode
	 * @param args Wird nicht benötigt
	 */
	public static void main(String[] args) {
		new Horst();
	}
}

package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.*;

import render.Renderer;

import com.sun.opengl.util.Animator;

import engine.GameTimer;

/**
 * Initialisiert notwendige Teilsysteme und stellt das ausführbare Programm dar
 * 
 * @author Kai
 */
public class Horst {

	/**
	 * Main Methode
	 * @param args Wird nicht benötigt
	 */
	public static void main(String[] args) {
		Frame frame = new Frame("Horst brennt");
		
		// OpenGL Capabilites
		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);
		
		// OpenGL Zeichenfläche
		GLCanvas canvas = new GLCanvas(caps);
		
		int width = 800;
		int height = 600;
		
		// Spielobjekt
		Game game = new Game(new GameTimer());

		// Tastensteuerung
		frame.addKeyListener(game);	
		
		// Objekt zum Initialisieren/Neuzeichnen von OpenGL
		canvas.addGLEventListener(new Renderer(game, width, height));	
		frame.add(canvas);
		
		// Fenstergröße
		frame.setSize(width, height);
		frame.setResizable(false);
		
		// Fenster zentrieren
		frame.setLocationRelativeTo(null);
		
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

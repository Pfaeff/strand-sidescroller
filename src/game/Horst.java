package game;

import engine.GameThread;
import engine.GameTimer;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import render.Renderer;

/**
 * Initialisiert notwendige Teilsysteme und stellt das ausführbare Programm dar
 * 
 * @author Kai
 */
public class Horst {
	private Frame frame;
	private GLCanvas canvas;
	private GameTimer gameTimer;
	private Game game;
	
	final private int width = 800;
	final private int height = 600; 
	
	public Horst() {
		// Fenster initialisieren
		initFrame();
		// Spiel initialisieren
		initGame();	
		// OpenGL initialisieren
		initGL();		
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
	
	private void initGame() {
		gameTimer = new GameTimer();
		game = new Game(gameTimer);
		// Tastensteuerung
		frame.addKeyListener(game);				
	}
	
	private void initGL() {
		// OpenGL Capabilites
		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);		
		// OpenGL Zeichenfläche
		canvas = new GLCanvas(caps);
		// Objekt zum Initialisieren/Neuzeichnen von OpenGL
		canvas.addGLEventListener(new Renderer(game, width, height));	
		frame.add(canvas);		
	}
	
	private void runGame() {
		// Spielschleife
		final GameThread gameThread = new GameThread(game, canvas);		
		// Thread in dem gezeichnet wird
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						gameThread.endGameLoop();
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
		@SuppressWarnings("unused")
		Horst horst = new Horst();
	}
}

package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.Animator;

public class Horst {

	public static void main(String[] args) {
		Frame frame = new Frame("Horst brennt");
		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);
		GLCanvas canvas = new GLCanvas(caps);

		frame.add(canvas);
		frame.setSize(512, 512);
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

		frame.setVisible(true);
		animator.start();
	}

	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.setSwapInterval(0);

	}

	public void display(GLAutoDrawable drawable) {

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}

}

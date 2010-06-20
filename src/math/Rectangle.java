package math;

/**
 * Repräsentiert ein Rechteck und stellt Methoden für die Kollision bereit
 * 
 * @author Kai
 */
final public class Rectangle {
	final private Vector2f e0;
	final private Vector2f e1;
	
	/*
	 * 				e1
	 * 	+----------(+)
	 * 	|           |
	 * 	|           |
	 * (+)----------+
	 * e0
	 */
	public Rectangle(Vector2f e0, Vector2f e1) {
		this.e0 = e0;
		this.e1 = e1;
	}
	
	public static boolean intersect(Rectangle r1, Rectangle r2) {
		return 	(r1.e0.getX() <= r2.e1.getX()) && 
				(r1.e1.getX() >= r2.e0.getX()) &&
				(r1.e0.getY() <= r2.e1.getY()) &&
				(r1.e1.getY() >= r2.e0.getY()); 
	}
}

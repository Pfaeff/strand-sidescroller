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
	
	public static Vector2f getMTD(Rectangle r1, Rectangle r2) {
		if (!intersect(r1, r2)) {
			return new Vector2f();
		}
		
		Vector2f[] dir = new Vector2f[4];
		dir[0] = new Vector2f(r2.e0.getX() - r1.e1.getX(), 0);
		dir[1] = new Vector2f(r2.e1.getX() - r1.e0.getX(), 0);
		dir[2] = new Vector2f(0, r2.e0.getY() - r1.e1.getY());
		dir[3] = new Vector2f(0, r2.e1.getY() - r1.e0.getY());
		
		int minIndex = -1;
		float minLength = Float.POSITIVE_INFINITY;
		for (int i=0; i<4; i++) {
			float currLength = dir[i].lengthSquare();
			if (currLength < minLength) {
				minIndex = i;
				minLength = currLength;
			}
		}
		if (minIndex > -1) {
			return dir[minIndex];
		}
		return new Vector2f();
	}
}

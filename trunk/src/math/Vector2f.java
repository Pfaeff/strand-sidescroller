package math;

/**
 * Stellt einen zweidimensionalen Vektor dar
 * 
 * @author Kai
 */
final public class Vector2f {
	final private float x;
	final private float y;

	/**
	 * Parametrisierter Konstruktor
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Kopierender Konstruktor
	 * 
	 * @param v zu kopierender Vektor
	 */
	public Vector2f(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
	}	

	/**
	 * Null Konstruktor, erzeugt einen Nullvektor
	 */
	public Vector2f() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Vektor skalieren
	 * 
	 * @param factor Faktor
	 * @return Vektor mit factor multipliziert
	 */
	public Vector2f scale(float factor) {
		return new Vector2f(x * factor, y * factor);
	}
	
	/**
	 * Liefert die Länge des Vektors zum Quadrat
	 * 
	 * @return |v|² (bzw. v.v)
	 */
	public float lengthSquare() {
		return dotProduct(this, this);
	}
	
	/**
	 * Liefert die Länge des Vektors
	 * 
	 * @return |v| (bzw. sqrt(v.v))
	 */
	public float length() {
		return (float)Math.sqrt(lengthSquare());
	}
	
	/**
	 * Liefert einen Vektor der Länge 1, 
	 * der in die selbe Richtung zeigt wie dieser Vektor
	 * 
	 * @return Normalisierter Vektor
	 */
	public Vector2f normalize() {
		float len = length();
		if (len == 0) {
			return new Vector2f(this);
		}
		return scale(1/len);
	}

	/**
	 * Addiert zwei Vektoren
	 * 
	 * @param v1 erster Vektor
	 * @param v2 zweiter Vektor
	 * @return 
	 */
	public static Vector2f add(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Subtrahiert zwei Vektoren
	 * 
	 * @param v1 erster Vektor
	 * @param v2 zweiter Vektor
	 * @return 
	 */
	public static Vector2f sub(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x - v2.x, v1.y - v2.y);
	}	
	
	/**
	 * Gibt das Skalarprodukt (inneres Produkt) zweier Vektoren zurück
	 * 
	 * @param v1 erster Vektor
	 * @param v2 zweiter Vektor
	 * @return
	 */
	public static float dotProduct(Vector2f v1, Vector2f v2) {
		return v1.x*v2.x+v1.y*v2.y;
	}
	
	/**
	 * Getter für x
	 * 
	 * @return x
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Getter für y	
	 * 
	 * @return y
	 */
	public float getY() {
		return y;
	}
}

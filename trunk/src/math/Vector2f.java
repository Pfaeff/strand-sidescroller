package math;

public class Vector2f {
	final private float x;
	final private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
	}	

	public Vector2f() {
		x = 0;
		y = 0;
	}
	
	public Vector2f scale(float factor) {
		return new Vector2f(x * factor, y * factor);
	}
	
	public float lengthSquare() {
		return dotProduct(this, this);
	}
	
	public float length() {
		return (float)Math.sqrt(lengthSquare());
	}
	
	public Vector2f normalize() {
		float len = length();
		if (len == 0) {
			return new Vector2f(this);
		}
		return scale(1/len);
	}

	public static Vector2f add(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x + v2.x, v1.y + v2.y);
	}
	
	public static Vector2f sub(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x - v2.x, v1.y - v2.y);
	}	
	
	public static float dotProduct(Vector2f v1, Vector2f v2) {
		return v1.x*v2.x+v1.y*v2.y;
	}
}

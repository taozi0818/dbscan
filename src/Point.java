public class Point {

	private int id;
	private double x;
	private double y;
	private boolean core = false; // whether it is a core
	
	public Point(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getX () {
		return this.x;
	}

	public double getY () {
		return this.y;
	}
	
	public void setX (double x) {
		this.x = x;
	}
	
	public void setY (double y) {
		this.y = y;
	}
	
	public Boolean getCore () {
		return this.core;
	}
	
}

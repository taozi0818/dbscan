public class Point {

	private int id;
	private double x;
	private double y;
	private boolean core = false; // 标记是否为核心点

	public Point(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getX () {
		return this.x;
	}

	public double getY () {
		return this.y;
	}
	
	public void setCore (Boolean core) {
		this.core = core;
	}

	public Boolean getCore () {
		return this.core;
	}
	
}

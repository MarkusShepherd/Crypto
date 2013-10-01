package info.riemannhypothesis.gol;

public class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean correct(int width, int height) {
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}

	public void torus(int width, int height) {
		while (x >= width) {
			x -= width;
		}
		while (x < 0) {
			x += width;
		}
		while (y >= height) {
			y -= height;
		}
		while (y < 0) {
			y += height;
		}
	}
}
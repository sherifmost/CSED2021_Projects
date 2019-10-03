package eg.edu.alexu.csd.oop.draw;

public class Shape_maker {

	public Shape_imp makeShape(int type) {

		Shape_imp shape = null;
		if (type == 1) {
			shape = new Rectangle();
		}
		if (type == 2) {
			shape = new Square();
		}
		if (type == 3) {
			shape = new Ellipse();
		}
		if (type == 4) {
			shape = new Circle();
		}
		if (type == 5) {
			shape = new Triangle();
		}
		if (type == 6) {
			shape = new Line();
		}
		return shape;

	}
}

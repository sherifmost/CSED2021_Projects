package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends Shape_imp {

	Point point2;
	Point point3;
	Map<String, Double> properties = new HashMap<>();

	public Triangle(Point point1, Point point2, Point point3) {// instantiate the triangle and fill its properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x1", point1.getX());
		properties.put("y1", point1.getY());
		properties.put("x2", point2.getX());
		properties.put("y2", point2.getY());
		properties.put("x3", point3.getX());
		properties.put("y3", point3.getY());
		properties.put("type", 5.0);// type 5 to be implemented.
		this.setProperties(properties);
		this.type = "Triangle";
		this.setPosition(point1);
		this.point2 = point2;
		this.point3 = point3;
	}

	public Triangle() {
		this(new Point(0, 0), new Point(0, 0), new Point(0, 0));
	}

	public void setPoint2(Point point2) {
		getProperties().put("x2", point2.getX());
		getProperties().put("y2", point2.getY());

		this.point2 = point2;
	}

	public void setPoint3(Point point3) {
		getProperties().put("x3", point3.getX());
		getProperties().put("y3", point3.getY());

		this.point3 = point3;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Triangle();
		r.setColor(getColor());
		r.setFillColor(getFillColor());
		r.setPosition(getPosition());
		Map newprop = new HashMap<>();
		for (Map.Entry s : getProperties().entrySet()) {
			newprop.put(s.getKey(), s.getValue());
		}
		r.setProperties(newprop);
		return r;

	}

	@Override
	public void draw(Graphics canvas) {
		int[] x = { (int) Math.round(getProperties().get("x1")), (int) Math.round(getProperties().get("x2")),
				(int) Math.round(getProperties().get("x3")) };
		int[] y = { (int) Math.round(getProperties().get("y1")), (int) Math.round(getProperties().get("y2")),
				(int) Math.round(getProperties().get("y3")) };
		canvas.setColor(getFillColor());
		canvas.fillPolygon(x, y, 3);
		canvas.setColor(getColor());
		canvas.drawPolygon(x, y, 3);
	}

}

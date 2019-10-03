package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Line extends Shape_imp {

	Point point2;
	Map<String, Double> properties = new HashMap<>();

	public Line(Point point1, Point point2) {// instantiate the line and fill its properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x1", point1.getX());
		properties.put("y1", point1.getY());
		properties.put("x2", point2.getX());
		properties.put("y2", point2.getY());
		properties.put("type", 6.0);// type 6 to be implemented.
		this.setProperties(properties);
		this.setPosition(point1);
		this.point2 = point2;
	}

	public Line() {
		this(new Point(0, 0), new Point(0, 0));
	}

	public void setPoint2(Point point2) {
		getProperties().put("x2", point2.getX());
		getProperties().put("y2", point2.getY());

		this.point2 = point2;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Line();
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
		canvas.setColor(getColor());
		canvas.drawLine(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("x2")),
				(int) Math.round(getProperties().get("y2")));
	}

}

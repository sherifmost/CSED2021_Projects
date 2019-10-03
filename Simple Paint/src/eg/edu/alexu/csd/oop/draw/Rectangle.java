package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends Shape_imp {

	int length, width;
	Map<String, Double> properties = new HashMap<>();

	public Rectangle(Point top_left, int length, int width) {// instantiate the rectangle and fill its properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x", top_left.getX());
		properties.put("y", top_left.getY());
		properties.put("length", (double) length);
		properties.put("width", (double) width);
		properties.put("type", 1.0);// type 1 to be implemented.
		this.type = "Rectangle";
		this.setProperties(properties);
		this.setPosition(top_left);
		this.length = length;
		this.width = width;

	}

	public void setlength(int length) {
		getProperties().put("length", (double) length);
		this.length = length;
	}

	public void setwidth(int width) {
		getProperties().put("length", (double) width);
		this.width = width;
	}

	public Rectangle() {
		this(new Point(0, 0), 0, 0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Rectangle();
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
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("length")),
				(int) Math.round(getProperties().get("width")));
		canvas.setColor(getColor());
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("length")),
				(int) Math.round(getProperties().get("width")));

	}

}

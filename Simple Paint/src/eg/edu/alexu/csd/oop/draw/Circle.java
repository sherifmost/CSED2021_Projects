package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Circle extends Shape_imp {

	int diameter;
	Map<String, Double> properties = new HashMap<>();

	public Circle(Point top_left, int diameter) {//instantiate the circle and fill its properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x", top_left.getX());
		properties.put("y", top_left.getY());
		properties.put("diameter", (double) diameter);
		properties.put("type", 4.0);// type 4 to be implemented.
		this.setProperties(properties);
		this.type = "Circle";
		this.setPosition(top_left);
		this.diameter = diameter;
	}

	public Circle() {
		this(new Point(0, 0), 0);
	}

	public void setdiameter(int diameter) {
		getProperties().put("diameter", (double) diameter);
		this.diameter = diameter;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Circle();
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
		canvas.fillOval(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("diameter")), (int) Math.round(getProperties().get("diameter")));
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("diameter")), (int) Math.round(getProperties().get("diameter")));

	}

}

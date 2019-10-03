package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends Shape_imp {

	int d_horizontal, d_vertical;
	Map<String, Double> properties = new HashMap<>();

	public Ellipse(Point top_left, int d_horizontal, int d_vertical) {// instantiate the ellipse and fill its
																		// properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x", top_left.getX());
		properties.put("y", top_left.getY());
		properties.put("d_horizontal", (double) d_horizontal);
		properties.put("d_vertical", (double) d_vertical);
		properties.put("type", 3.0);// type 3 to be implemented.
		this.type = "Ellipse";
		this.setProperties(properties);
		this.setPosition(top_left);
		this.d_horizontal = d_horizontal;
		this.d_vertical = d_vertical;
	}

	public Ellipse() {
		this(new Point(0, 0), 0, 0);
	}

	public void setd_horizontal(int d_horizontal) {
		getProperties().put("d_horizontal", (double) d_horizontal);
		this.d_horizontal = d_horizontal;
	}

	public void setd_vertical(int d_vertical) {
		getProperties().put("d_vertical", (double) d_vertical);
		this.d_vertical = d_vertical;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Ellipse();
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
		canvas.fillOval(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("d_horizontal")),
				(int) Math.round(getProperties().get("d_vertical")));
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("d_horizontal")),
				(int) Math.round(getProperties().get("d_vertical")));

	}

}

package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Square extends Shape_imp {

    int side;
	Map<String, Double> properties = new HashMap<>();

	public Square(Point top_left, int side) {//instantiate the square and fill its properties.
		properties.put("color", (double) this.getColor().getRGB());
		properties.put("fillcolor", (double) this.getFillColor().getRGB());
		properties.put("x", top_left.getX());
		properties.put("y", top_left.getY());
		properties.put("side", (double) side);
		properties.put("type", 2.0);// type 2 to be implemented.
		this.type = "Square";
		this.setProperties(properties);
		this.setPosition(top_left);
		this.side = side;
	}

	public void setside(int side) {
		getProperties().put("side", (double)side);
		this.side = side;
	}
	public Square() {
		this(new Point(0, 0), 0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shape_imp r = new Square();
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
		canvas.fillRect(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("side")),(int) Math.round(getProperties().get("side")));
		canvas.setColor(getColor());
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(getProperties().get("side")), (int) Math.round(getProperties().get("side")));

	}

}

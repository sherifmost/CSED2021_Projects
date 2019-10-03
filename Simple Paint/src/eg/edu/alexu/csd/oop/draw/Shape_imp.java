package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class Shape_imp implements Shape {

	String type;// string containing the shape type.
	private Point top_left = new Point(0, 0);// top left point of the shape.
	private Color color = Color.black;// border color.
	private Color fillColor = Color.white;// fill color.
	private Map<String, Double> properties = new HashMap<>();// map containing all the shape properties(helps in save
																// and load and cloning).

	@Override
	public void setPosition(Point position) {// set top left point.
		properties.put("x", (double) position.getX());
		properties.put("y", (double) position.getY());
		top_left = position;

	}

	@Override
	public Point getPosition() {// get top left point.
		return top_left;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {// set the properties map.
		color = new Color((int)Math.round(properties.get("color")));
		fillColor = new Color((int)Math.round(properties.get("fillcolor")));
		this.properties = properties;
	}

	@Override
	public Map<String, Double> getProperties() {// get the properties map.
		return properties;
	}

	@Override
	public void setColor(Color color) {// set the border color.
		
		this.color = color;
		properties.put("color", (double) color.getRGB());
	    
	}

	@Override
	public Color getColor() {// get the border color.
		
		return color;
	}

	@Override
	public void setFillColor(Color color) {// set the fill color.
	
		fillColor = color;
		properties.put("fillcolor", (double) color.getRGB());
	}

	
	
	
	@Override
	public Color getFillColor() {// get the fill color.
		
		return fillColor;
	}

	public abstract void draw(Graphics canvas);// draw the shape to be implemented according to the shape's type.

	@Override
	public Object clone() throws CloneNotSupportedException {// obtain a deep clone of the shape (a disjoint copy).
		return super.clone();
	}

	
}

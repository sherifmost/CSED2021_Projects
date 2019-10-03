package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Engine implements DrawingEngine {

	int update = 0;
	ArrayList<Integer> checkundo = new ArrayList<>();// an array list to check the operation to be undone.
	ArrayList<Integer> checkredo = new ArrayList<>();// an array list to check the operation to be redone.
	ArrayList<Shape> oldshapes = new ArrayList<>();// an array list containing all the shapes which were swapped by
													// other shapes (for undo and redo).
	ArrayList<Shape> newshapes = new ArrayList<>();// an array list containing all the new shapes put instead of the old
													// shapes (for undo and redo).
	ArrayList<Shape> shapes = new ArrayList<>();// an array list containing all the shapes currently present.
	ArrayList<Shape> undo = new ArrayList<>();// an array list containing all the shapes which had an operation on them
												// to be undone.
	ArrayList<Shape> redo = new ArrayList<>();// an array list containing all the shapes which had undergone undo
												// operation for redo.
	java.util.List<Class<? extends Shape>> returned = new LinkedList<>();// list containing all supporting classes.

	@Override
	public void refresh(Graphics canvas) {// redraw all the currently present shapes.
		int i;
		for (i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(canvas);
		}

	}

	@Override
	public void addShape(Shape shape) {// add a new shape to the list.

		shapes.add(shape);
		undo.add(shape);
		checkundo.add(1);
		if (undo.size() > 20) {
			undo.remove(0);
			checkundo.remove(0);
		}
		redo.clear();
		checkredo.clear();
	}

	@Override
	public void removeShape(Shape shape) {// remove a shape from the list (for deleting).
		undo.add(shape);
		checkundo.add(2);
		if (undo.size() > 20) {
			undo.remove(0);
			checkundo.remove(0);
		}

		shapes.remove(shape);
		redo.clear();
		checkredo.clear();

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) throws CloneNotSupportedException {// swap two shapes(change
																								// the attributes of a
																								// shape in the list).
		undo.add(oldShape);// updated shape
		checkundo.add(3);
		oldshapes.add(oldShape);
		newshapes.add(newShape);
		update = newshapes.size();
		if (undo.size() > 20) {
			undo.remove(0);
			checkundo.remove(0);
		}
		redo.clear();
		checkredo.clear();
		// update operation;
		Shape temp = (Shape) oldShape.clone();
		temp =  newShape;
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).equals(oldShape)) {
				shapes.set(i, temp);
				break;
			}
		}
       
	}

	@Override
	public Shape[] getShapes() {// return an array containing all the current shapes.

		Shape[] temp = new Shape[shapes.size()];
		int i;
		for (i = 0; i < temp.length; i++) {
			temp[i] = shapes.get(i);
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {// return a list having all the classes from which a shape
																// instance can be made.
		URL file = Rectangle.class.getResource("Rectangle.class");
		File file2 = new File(file.getPath());
		File pack = new File(file2.getParent());// the package file.
		String path = Rectangle.class.getName();
		// installPluginShape("C:\\Users\\Lenovo\\Desktop\\OOP_TAs-oop-course-projects-f402b1ae8a13\\RoundRectangle.java");//
		// install
		// the
		// plugin.
		String[] arr = pack.list();// all the files in the package file.

		for (int i = 0; i < arr.length; i++) {
			Class<? extends Shape> n;
			try {
				if(!arr[i].contains("Shapes_model")) {
				n = (Class<? extends Shape>) Rectangle.class.getClassLoader()
						.loadClass(path.replace("Rectangle", arr[i].replace(".class", "")));// load each class in the
																							// package.
				if (Shape.class.isAssignableFrom(n) && !n.isInterface() && !Modifier.isAbstract(n.getModifiers()))// check
																													// if
																													// it
																													// implements
																													// shape
																													// interface
																													// and
																													// can
																													// be
																													// instantiated.
					returned.add((Class<? extends Shape>) n);
				}
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Class<? extends Shape> n;
		try {
			n = (Class<? extends Shape>) Rectangle.class.getClassLoader()
					.loadClass(path.replace("Rectangle", "RoundRectangle"));
			returned.add(n);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returned;
	}

	@Override
	public void undo() {// undo last operation(last 20 max).
		redo.add(undo.get(undo.size() - 1));
		checkredo.add(checkundo.get(checkundo.size() - 1));
		switch (checkundo.get(checkundo.size() - 1)) {
		case (1): {// shape was added
			shapes.remove(undo.get(undo.size() - 1));
			break;
		}
		case (2): {// shape was removed
			shapes.add(undo.get(undo.size() - 1));
			break;
		}
		case (3): {// shape was updated

			for (int i = 0; i < shapes.size(); i++) {
				if (shapes.get(i).equals(newshapes.get(update - 1))) {
					shapes.set(i, oldshapes.get(update - 1));
					
					update --;
					break;
				}

			}
			break;
		}
		}
		undo.remove(undo.size() - 1);
		checkundo.remove(checkundo.size() - 1);
	}

	@Override
	public void redo() {// redo last undone operation (last 20 max).
		undo.add(redo.get(redo.size() - 1));
		checkundo.add(checkredo.get(checkredo.size() - 1));
		switch (checkredo.get(checkredo.size() - 1)) {
		case (1): {// shape was added
			shapes.add(redo.get(redo.size() - 1));
			break;
		}
		case (2): {// shape was removed
			shapes.remove(redo.get(redo.size() - 1));
			break;
		}
		case (3): {// shape was updated
			update++;
			for (int i = 0; i < shapes.size(); i++) {
				if (shapes.get(i).equals(oldshapes.get(update - 1))) {
					shapes.set(i, newshapes.get(update - 1));
					break;
				}

			}
			break;
		}
		}
		redo.remove(redo.size() - 1);
		checkredo.remove(checkredo.size() - 1);

	}

	@Override
	public void save(String path) {
		File file = new File(path);
		BufferedWriter writer;
		String type = "";
		if (path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0)
			type = path.substring(path.lastIndexOf(".") + 1);

		if (type.equalsIgnoreCase("XML")) {
			try {
				writer = new BufferedWriter(new FileWriter(path));
				writer.write(getXmLString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (type.equalsIgnoreCase("JSON")) {
			try {
				writer = new BufferedWriter(new FileWriter(path));
				writer.write(getJsOnstring());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void load(String path) {
		File file = new File(path);
		String result = "";
		@SuppressWarnings("resource")
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;

			while ((st = br.readLine()) != null) {
				result += st;
				result += System.lineSeparator();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = "";
		if (path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0)
			type = path.substring(path.lastIndexOf(".") + 1);
		if (type.equalsIgnoreCase("XML")) {
			try {
				loadXmL(result);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (type.equalsIgnoreCase("JSON")) {
			try {
				loadJsOn(result);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	// still working on it.
	public void installPluginShape(String jarPath) {// need to add jar file to the build path.
		File file = new File(jarPath);

		URL url;
		try {
			url = file.toURI().toURL();
			URL[] urls = new URL[] { url };

			ClassLoader child = new URLClassLoader(urls, getClass().getClassLoader());
			Class classToLoad = Class.forName("eg.edu.alexu.csd.oop.draw.RoundRectangle", true, child);

			returned.add(classToLoad);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getXmLString() {
		// generating XmL string:
		String string = "";

		string += "<paint>";
		string += System.lineSeparator();

		for (int j = 0; j < shapes.size(); j++) {

			string += "<shape id =\"" + shapes.get(j).getClass().getName() + "\">";
			string += System.lineSeparator();

			string += "<map>";
			string += System.lineSeparator();
			if (shapes.get(j).getProperties() != null)
				for (Map.Entry s : shapes.get(j).getProperties().entrySet()) {

					string += "<" + s.getKey() + ">" + s.getValue() + "</" + s.getKey() + ">";
					string += System.lineSeparator();
				}

			System.out.println(string);
			string += "</map>";
			string += System.lineSeparator();
			string += "</shape>";
			string += System.lineSeparator();

		}
		string += "</paint>";
		return string;
	}

	private void loadXmL(String string) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		shapes.clear();
		undo.clear();
		redo.clear();
		checkredo.clear();
		checkundo.clear();
		oldshapes.clear();
		newshapes.clear();
		String[] arr = string.split(System.lineSeparator());

		String path;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i].contains("shape id")) {

				path = arr[i].split("\"")[1];

				Class sClass = Rectangle.class.getClassLoader().loadClass(path);
				Shape shape = (Shape) sClass.newInstance();
				i += 2;
				Map<String, Double> teMap = new HashMap<>();
				while (!arr[i].contains("/map") && i < arr.length) {
					String key;
					Double value;
					String first = arr[i].split(">")[0];
					key = first.substring(1, first.length());
					String second = arr[i].split(">")[1].split("<")[0];

					value = Double.parseDouble(second);

					teMap.put(key, value);

					i++;

				}

				if (teMap != null && shape != null) {
					if(teMap.get("color")!=null)
					shape.setColor(new Color((int) Math.round(teMap.get("color"))));
					if(teMap.get("fillcolor")!=null)
					shape.setFillColor(new Color((int) Math.round(teMap.get("fillcolor"))));
					if(teMap.get("x")!=null && teMap.get("y")!=null)
					shape.setPosition(new Point(teMap.get("x").intValue(), teMap.get("y").intValue()));
					shape.setProperties(teMap);
					shapes.add(shape);
				}
			}

		}

	}

	public String getJsOnstring() {
		// generating JsOn string:

		String string = "";

		string += "{\"ShapeArray\" :";
		string += System.lineSeparator();
		string += "[";
		string += System.lineSeparator();
		for (int j = 0; j < shapes.size(); j++) {
			string += "{ \"ClassName\" : \"" + shapes.get(j).getClass().getName() + "\",";
			string += System.lineSeparator();
			if (shapes.get(j).getProperties() != null)
				for (Map.Entry s : shapes.get(j).getProperties().entrySet()) {
					string += "\"" + s.getKey() + "\" : \"" + s.getValue() + "\",";
					string += System.lineSeparator();
				}
			string += "}";
			if (j != shapes.size() - 1)
				string += ",";
			string += System.lineSeparator();

		}
		string += "]";
		string += System.lineSeparator();
		string += "}";
		return string;
	}

	private void loadJsOn(String string) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// reading the JsOn string if it is given and generating shapes accordingly:
		shapes.clear();
		undo.clear();
		redo.clear();
		checkredo.clear();
		checkundo.clear();
		oldshapes.clear();
		newshapes.clear();

		String[] arr = string.split(System.lineSeparator());

		String path;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i].contains("ClassName")) {

				path = arr[i].split("\"")[3];

				Class sClass = Rectangle.class.getClassLoader().loadClass(path);
				Shape shape = (Shape) sClass.newInstance();
				i++;
				Map<String, Double> teMap = new HashMap<>();
				while (!arr[i].contains("}") && i < arr.length) {
					String key;
					Double value;
					key = arr[i].split("\"")[1];
					String second = arr[i].split("\"")[3];

					value = Double.parseDouble(second);

					teMap.put(key, value);

					i++;

				}
				if (teMap != null && shape != null) {
					if(teMap.get("color")!=null)
					shape.setColor(new Color((int) Math.round(teMap.get("color"))));
					if(teMap.get("fillcolor")!=null)
					shape.setFillColor(new Color((int) Math.round(teMap.get("fillcolor"))));
					if(teMap.get("x")!=null && teMap.get("y")!=null)
					shape.setPosition(new Point(teMap.get("x").intValue(), teMap.get("y").intValue()));
					shape.setProperties(teMap);
					shapes.add(shape);

				}
			}

		}

	}
}
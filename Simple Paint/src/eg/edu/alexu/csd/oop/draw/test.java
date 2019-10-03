package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.List;
import java.awt.Point;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Templates;

import org.junit.internal.Classes;

import eg.edu.alexu.csd.oop.test.DummyShape;

public class test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, CloneNotSupportedException {
          
		Engine engine = new Engine();
		Shape x= new Rectangle();
		x.setPosition(new Point(0, 0));
		
		engine.addShape(x);
		
		Shape y=(Shape) engine.getShapes()[0].clone();
		System.out.println(x.equals(y));
		System.out.println(engine.getShapes()[0].getPosition().x);
	    y.setPosition(new Point(10, 20));
	    engine.updateShape(engine.getShapes()[0], y);
	    System.out.println(engine.getShapes()[0].equals(y));
	    System.out.println(engine.getShapes()[0].getPosition().x);
	   engine.undo();
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].getPosition().x);
		 Shape z = (Shape) engine.getShapes()[0].clone();
		    z.setPosition(new Point(20, 20));
		    engine.updateShape(engine.getShapes()[0], z);
		    System.out.println(engine.getShapes()[0].equals(z));
			System.out.println(engine.getShapes()[0].getPosition().x);
			 engine.undo();
				System.out.println(engine.getShapes()[0].equals(x));
				System.out.println(engine.getShapes()[0].getPosition().x);
				
		/*engine.undo();
		System.out.println(engine.getShapes()[0].equals(x));
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].getPosition().x);
		engine.redo();
		System.out.println(engine.getShapes()[0].equals(x));
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].equals(z));
		System.out.println(engine.getShapes()[0].getPosition().x);
		engine.redo();
		System.out.println(engine.getShapes()[0].equals(x));
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].equals(z));
		System.out.println(engine.getShapes()[0].getPosition().x);
		
 y=(Shape) engine.getShapes()[0].clone();
		y.setPosition(new Point(10, 20));
	    engine.updateShape(engine.getShapes()[0], y);
	    System.out.println(engine.getShapes()[0].equals(x));
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].equals(z));
		System.out.println(engine.getShapes()[0].getPosition().x);
		engine.undo();
		System.out.println(engine.getShapes()[0].equals(z));
		System.out.println(engine.getShapes()[0].equals(x));
		System.out.println(engine.getShapes()[0].equals(y));
		System.out.println(engine.getShapes()[0].getPosition().x);
		
		*/
		
		
		
	    
	
	}
}

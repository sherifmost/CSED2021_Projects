package eg.edu.alexu.csd.oop.draw;

import java.util.Map.Entry;

import javax.swing.JFrame;

import org.w3c.dom.css.Rect;

import java.awt.*;
import javax.swing.*;

public class CanvasDemo extends JFrame{

private MyCanvas canvas = new MyCanvas();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CanvasDemo fr = new CanvasDemo();
	}
	public CanvasDemo() {
		setLayout(new BorderLayout());
		setSize(1000, 1000);
		setTitle("Canvas Demo");
		add("Center", canvas);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//another way to center the screen
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class MyCanvas extends Canvas {
		
		@Override
		public void paint(Graphics g) {
			Rectangle rec = new Rectangle(new Point(10,10),50,100);
			rec.draw(g);
			Circle cir = new Circle(new Point(100,100),150);
			cir.draw(g);
			Square square= new Square(new Point(800,700),50);
			square.draw(g);
			try {
				Square copy = (Square)square.clone();
				copy.setPosition(new Point(400, 400));
				copy.setFillColor(Color.cyan);
				copy.draw(g);
				Square copy2 = (Square)square.clone();
				copy2.setPosition(new Point(450, 400));
				copy2.setFillColor(Color.cyan);
				copy2.draw(g);
				square.setPosition(new Point(350, 350));
				square.draw(g);
				Square copy3 = (Square) square.clone();
				copy3.setPosition(new Point(500, 250));
				copy3.setFillColor(Color.cyan);
				copy3.draw(g);		
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			square.setPosition(new Point(700, 650));
			square.draw(g);
			
			try {
				Rectangle recClone = (Rectangle)rec.clone();
				recClone.setPosition(new Point(300,300));
				recClone.setColor(Color.gray);
				recClone.setFillColor(Color.yellow);
				recClone.draw(g);
				Circle cirClone = (Circle) cir.clone();
				cirClone.setColor(Color.YELLOW);
				cirClone.setPosition(new Point(500,500));
				cirClone.draw(g);
				
				
				
				
				
				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Rectangle rec5 = new Rectangle(new Point(120, 18),150,100);
		rec5.setColor(Color.cyan);
		rec5.setPosition(new Point(120, 18));
		rec5.setFillColor(Color.yellow);
		rec5.draw(g);
		try {
			Rectangle copy2 = (Rectangle) rec5.clone();
			copy2.setFillColor(Color.cyan);
			copy2.setPosition(new Point(300, 16));
			copy2.setlength(500);
			copy2.draw(g);
			rec5.draw(g);
			rec5.setFillColor(Color.black);
			rec5.setPosition(new Point(200, 25));
			rec5.draw(g);
			for (Entry<String, Double> entry : copy2.getProperties().entrySet())
			{
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Entry<String, Double> entry : rec5.getProperties().entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		Triangle triangle = new Triangle(new Point(100, 100),new Point(120, 120),new Point(200, 150));
		triangle.draw(g);
		try {
			Triangle copy = (Triangle) triangle.clone();
			copy.setFillColor(Color.black);
			copy.setPoint2(new Point(300, 300));
			copy.setPosition(new Point(200,200));
			copy.draw(g);
			triangle.setFillColor(Color.yellow);
			triangle.draw(g);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
	
	}
        
 
}

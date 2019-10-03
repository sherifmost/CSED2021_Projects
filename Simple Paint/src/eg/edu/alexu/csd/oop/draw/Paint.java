package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.xml.sax.XMLFilter;

import javax.net.ssl.ExtendedSSLSession;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Button;
import java.awt.Canvas;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.JMenu;

public class Paint extends JFrame {

	static private JPanel contentPane;
	private JTextField length_txt;
	private JTextField width_txt;
	private JTextField radius_txt;
	protected JColorChooser tcc;
	protected JLabel banner;
	JFileChooser fileChooser;
	boolean recBtnActive = false;
	boolean cirBtnActive = false;
	boolean ellBtnActive = false;
	boolean sqBtnActive = false;
	boolean lineBtnActive = false;
	boolean triBtnActive = false;
	boolean roundRecBtnActive = false;
	boolean borderClicked = false;
	boolean fillClicked = false;
	boolean move = false;
	boolean resize = false;
	boolean supportedShape = false;
	Shape cir, rec, ell, sq, line, tri, supported;
	ArrayList<Point> triPts = new ArrayList<>();
	int x_rightbottom, y_rightbottom, x_topleft, y_topleft, x, y;
	Canvas canvas = new Canvas();
	Engine eng = new Engine();
	Color bordercolor, fillcolor;
	int selected;
	Shape[] shapes;
	boolean ShapeIsSelected = false;
	JButton btnSupported;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Paint frame = new Paint();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Paint() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1116, 721);
		// DEFINING VARIABLES
		// menu bar including shapes buttons and selecting buttons
		// -------------------------------------------------------------
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton rectangle_btn = new JButton("Rectangle");
		menuBar.add(rectangle_btn);

		JButton square_btn = new JButton("Square");
		menuBar.add(square_btn);

		JButton circle_btn = new JButton("Circle");
		menuBar.add(circle_btn);

		JButton ellipse_btn = new JButton("Ellipse");
		menuBar.add(ellipse_btn);

		JButton triangle_btn = new JButton("Triangle");
		menuBar.add(triangle_btn);

		JButton line_btn = new JButton("Line");
		menuBar.add(line_btn);

		JButton backwardSelectBtn = new JButton("<<");
		backwardSelectBtn.setEnabled(false);
		menuBar.add(backwardSelectBtn);

		JButton select_btn = new JButton("Select");
		menuBar.add(select_btn);

		JButton forwardSelectBtn = new JButton(">>");
		forwardSelectBtn.setEnabled(false);
		menuBar.add(forwardSelectBtn);
		// -------------------------------------------------------------

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// DEFINING VARIABLES
		// buttons to apply actions on shapes like move , resize , copy and remove
		// -----------------------------------------------------------------------
		JButton btnMove = new JButton("Move");
		btnMove.setBounds(959, 245, 130, 23);
		contentPane.add(btnMove);
		btnMove.setEnabled(false);

		JButton btnResize = new JButton("Resize");
		btnResize.setBounds(959, 279, 130, 23);
		contentPane.add(btnResize);
		btnResize.setEnabled(false);

		JButton btnCopy = new JButton("Copy");
		btnCopy.setBounds(959, 315, 130, 23);
		contentPane.add(btnCopy);
		btnCopy.setEnabled(false);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(959, 211, 130, 23);
		contentPane.add(btnRemove);
		btnRemove.setEnabled(false);
		// -----------------------------------------------------------------------
		// DEFINING VARIABLES
		// label boxes to show choosen fill and border colors of a shape
		// setting default fill color by white and border color by black
		// -------------------------------------------------------------
		Panel lbl_borderColor = new Panel();
		lbl_borderColor.setBounds(969, 68, 30, 30);
		contentPane.add(lbl_borderColor);
		lbl_borderColor.setBackground(Color.black);

		Panel lbl_fillColor = new Panel();
		lbl_fillColor.setBounds(1047, 68, 30, 30);
		contentPane.add(lbl_fillColor);
		lbl_fillColor.setBackground(Color.white);

		bordercolor = Color.black;
		fillcolor = Color.white;
		// -------------------------------------------------------------
		// select button actions
		// the action is executed when the button is preesed
		// action >> drawing a border around the shape to show the selcted shape to the
		// user
		// --------------------------------------------------------------------------------------------------------
		select_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				recBtnActive = false;
				cirBtnActive = false;
				ellBtnActive = false;
				sqBtnActive = false;
				lineBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = true;
				move = false;
				resize = false;
				btnRemove.setEnabled(true);
				btnCopy.setEnabled(true);
				btnResize.setEnabled(true);
				btnMove.setEnabled(true);
				shapes = eng.getShapes();

				if (!backwardSelectBtn.isEnabled() && !forwardSelectBtn.isEnabled()) {
					selected = shapes.length - 1;
				}
				int type = shapes[selected].getProperties().get("type").intValue();
				if (type == 1) {// rectangle
					try {
						rec = (Rectangle) shapes[selected].clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					rec = new Rectangle(new Point(rec.getPosition().x - 2, rec.getPosition().y - 2),
							rec.getProperties().get("length").intValue() + 4,
							rec.getProperties().get("width").intValue() + 4);
					rec.setColor(Color.blue);
					rec.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					rec.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 2) {// square
					sq = shapes[selected];
					sq = new Square(new Point(sq.getPosition().x - 2, sq.getPosition().y - 4),
							sq.getProperties().get("side").intValue() + 4);
					sq.setColor(Color.blue);
					sq.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					sq.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 3) {// ellipse
					ell = shapes[selected];
					ell = new Ellipse(new Point(ell.getPosition().x - 2, ell.getPosition().y - 2),
							ell.getProperties().get("d_horizontal").intValue() + 4,
							ell.getProperties().get("d_vertical").intValue() + 4);
					ell.setColor(Color.blue);
					ell.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					ell.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 4) {// circle
					cir = shapes[selected];
					cir = new Circle(new Point(cir.getPosition().x - 2, cir.getPosition().y - 2),
							cir.getProperties().get("diameter").intValue() + 4);
					cir.setColor(Color.blue);
					cir.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					cir.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 5) {// triangle
					tri = shapes[selected];
					tri = new Triangle(
							new Point(tri.getProperties().get("x1").intValue() + 2,
									tri.getProperties().get("y1").intValue() + 2),
							new Point(tri.getProperties().get("x2").intValue() + 2,
									tri.getProperties().get("y2").intValue() + 2),
							new Point(tri.getProperties().get("x3").intValue() + 2,
									tri.getProperties().get("y3").intValue() + 2));
					tri.setColor(Color.blue);
					tri.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					tri.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 6) {// line
					line = shapes[selected];
					line = new Line(
							new Point(line.getProperties().get("x1").intValue() + 2,
									line.getProperties().get("y1").intValue() + 2),
							new Point(line.getProperties().get("x2").intValue() + 2,
									line.getProperties().get("y2").intValue() + 2));
					line.setColor(Color.blue);
					line.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					line.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				} else if (type == 7) {// the supported shape (round rectangle)
					try {
						supported = (RoundRectangle) shapes[selected].clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					supported.getProperties().put("Length",
							(double) (shapes[selected].getProperties().get("Length").intValue() + 4));
					supported.getProperties().put("Width",
							(double) (shapes[selected].getProperties().get("Width").intValue() + 4));
					supported.getProperties().put("ArcLength",
							(double) (shapes[selected].getProperties().get("ArcLength").intValue() + 4));
					supported.getProperties().put("ArcWidth",
							(double) (shapes[selected].getProperties().get("ArcWidth").intValue() + 4));
					supported.setPosition(
							new Point(shapes[selected].getPosition().x - 2, shapes[selected].getPosition().y - 2));
					supported.setColor(Color.blue);
					supported.setFillColor(Color.white);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					supported.draw(canvas.getGraphics());
					shapes[selected].draw(canvas.getGraphics());
				}
				lbl_fillColor.setBackground(shapes[selected].getFillColor());
				lbl_borderColor.setBackground(shapes[selected].getColor());
				if (selected > 0) {
					backwardSelectBtn.setEnabled(true);
					if (selected != shapes.length - 1) {
						forwardSelectBtn.setEnabled(true);
					}
				}
				eng.refresh(canvas.getGraphics());
			}
		});
		// --------------------------------------------------------------------------------------------------------
		// backward select button actions
		// similar to the select button but applies the action to the shape drawn before
		// the selected shape
		// --------------------------------------------------------------------------------------------------------
		backwardSelectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selected != 0) {
					selected--;
					if (selected == 0) {
						backwardSelectBtn.setEnabled(false);
					}
					int type = shapes[selected].getProperties().get("type").intValue();
					if (type == 1) {// rectangle
						rec = shapes[selected];
						rec = new Rectangle(new Point(rec.getPosition().x - 2, rec.getPosition().y - 2),
								rec.getProperties().get("length").intValue() + 4,
								rec.getProperties().get("width").intValue() + 4);
						rec.setColor(Color.blue);
						rec.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						rec.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 2) {// square
						sq = shapes[selected];
						sq = new Square(new Point(sq.getPosition().x - 2, sq.getPosition().y - 2),
								sq.getProperties().get("side").intValue() + 4);
						sq.setColor(Color.blue);
						sq.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						sq.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 3) {// ellipse
						ell = shapes[selected];
						ell = new Ellipse(new Point(ell.getPosition().x - 2, ell.getPosition().y - 2),
								ell.getProperties().get("d_horizontal").intValue() + 4,
								ell.getProperties().get("d_vertical").intValue() + 4);
						ell.setColor(Color.blue);
						ell.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						ell.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 4) {// circle
						cir = shapes[selected];
						cir = new Circle(new Point(cir.getPosition().x - 2, cir.getPosition().y - 2),
								cir.getProperties().get("diameter").intValue() + 4);
						cir.setColor(Color.blue);
						cir.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						cir.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 5) {// triangle
						tri = shapes[selected];
						tri = new Triangle(
								new Point(tri.getProperties().get("x1").intValue() + 2,
										tri.getProperties().get("y1").intValue() + 2),
								new Point(tri.getProperties().get("x2").intValue() + 2,
										tri.getProperties().get("y2").intValue() + 2),
								new Point(tri.getProperties().get("x3").intValue() + 2,
										tri.getProperties().get("y3").intValue() + 2));
						tri.setColor(Color.blue);
						tri.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						tri.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 6) {// line
						line = shapes[selected];
						line = new Line(
								new Point(line.getProperties().get("x1").intValue() + 2,
										line.getProperties().get("y1").intValue() + 2),
								new Point(line.getProperties().get("x2").intValue() + 2,
										line.getProperties().get("y2").intValue() + 2));
						line.setColor(Color.blue);
						line.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						line.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 7) {// suported shape (round rectangle)
						try {
							supported = (RoundRectangle) shapes[selected].clone();
						} catch (CloneNotSupportedException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						supported.getProperties().put("Length",
								(double) (shapes[selected].getProperties().get("Length").intValue() + 4));
						supported.getProperties().put("Width",
								(double) (shapes[selected].getProperties().get("Width").intValue() + 4));
						supported.getProperties().put("ArcLength",
								(double) (shapes[selected].getProperties().get("ArcLength").intValue() + 4));
						supported.getProperties().put("ArcWidth",
								(double) (shapes[selected].getProperties().get("ArcWidth").intValue() + 4));
						supported.setPosition(
								new Point(shapes[selected].getPosition().x - 2, shapes[selected].getPosition().y - 2));
						supported.setColor(Color.blue);
						supported.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						supported.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					}
				}
				lbl_fillColor.setBackground(shapes[selected].getFillColor());
				lbl_borderColor.setBackground(shapes[selected].getColor());
				if (selected != shapes.length - 1) {
					forwardSelectBtn.setEnabled(true);
				}
				eng.refresh(canvas.getGraphics());
			}
		});
		// --------------------------------------------------------------------------------------------------------
		// forward select button actions
		// similar to the select button but applies the action to the shape drawn after
		// the selected shape
		// --------------------------------------------------------------------------------------------------------
		forwardSelectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selected != shapes.length - 1) {
					selected++;
					if (selected == shapes.length - 1) {
						forwardSelectBtn.setEnabled(false);
					}
					int type = shapes[selected].getProperties().get("type").intValue();
					if (type == 1) {// rectangle
						rec = new Rectangle(
								new Point(shapes[selected].getPosition().x - 2, shapes[selected].getPosition().y - 2),
								shapes[selected].getProperties().get("length").intValue() + 4,
								shapes[selected].getProperties().get("width").intValue() + 4);
						rec.setColor(Color.blue);
						rec.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						rec.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 2) {// square
						sq = shapes[selected];
						sq = new Square(new Point(sq.getPosition().x - 2, sq.getPosition().y - 2),
								sq.getProperties().get("side").intValue() + 4);
						sq.setColor(Color.blue);
						sq.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						sq.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 3) {// ellipse
						ell = shapes[selected];
						ell = new Ellipse(new Point(ell.getPosition().x - 2, ell.getPosition().y - 2),
								ell.getProperties().get("d_horizontal").intValue() + 4,
								ell.getProperties().get("d_vertical").intValue() + 4);
						ell.setColor(Color.blue);
						ell.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						ell.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 4) {// circle
						cir = shapes[selected];
						cir = new Circle(new Point(cir.getPosition().x - 2, cir.getPosition().y - 2),
								cir.getProperties().get("diameter").intValue() + 4);
						cir.setColor(Color.blue);
						cir.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						cir.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 5) {// triangle
						tri = shapes[selected];
						tri = new Triangle(
								new Point(tri.getProperties().get("x1").intValue() + 2,
										tri.getProperties().get("y1").intValue() + 2),
								new Point(tri.getProperties().get("x2").intValue() + 2,
										tri.getProperties().get("y2").intValue() + 2),
								new Point(tri.getProperties().get("x3").intValue() + 2,
										tri.getProperties().get("y3").intValue() + 2));
						tri.setColor(Color.blue);
						tri.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						tri.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 6) {// line
						line = shapes[selected];
						line = new Line(
								new Point(line.getProperties().get("x1").intValue() + 2,
										line.getProperties().get("y1").intValue() + 2),
								new Point(line.getProperties().get("x2").intValue() + 2,
										line.getProperties().get("y2").intValue() + 2));
						line.setColor(Color.blue);
						line.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						line.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					} else if (type == 7) {// supported shape (round rectangle)
						try {
							supported = (RoundRectangle) shapes[selected].clone();
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						supported.getProperties().put("Length",
								(double) (shapes[selected].getProperties().get("Length").intValue() + 4));
						supported.getProperties().put("Width",
								(double) (shapes[selected].getProperties().get("Width").intValue() + 4));
						supported.getProperties().put("ArcLength",
								(double) (shapes[selected].getProperties().get("ArcLength").intValue() + 4));
						supported.getProperties().put("ArcWidth",
								(double) (shapes[selected].getProperties().get("ArcWidth").intValue() + 4));
						supported.setPosition(
								new Point(shapes[selected].getPosition().x - 2, shapes[selected].getPosition().y - 2));
						supported.setColor(Color.blue);
						supported.setFillColor(Color.white);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						supported.draw(canvas.getGraphics());
						shapes[selected].draw(canvas.getGraphics());
					}
				}
				lbl_fillColor.setBackground(shapes[selected].getFillColor());
				lbl_borderColor.setBackground(shapes[selected].getColor());
				if (selected != 0) {
					backwardSelectBtn.setEnabled(true);
				}
				eng.refresh(canvas.getGraphics());
			}
		});
		// --------------------------------------------------------------------------------------------------------
		// canvas settings
		// ---------------------------------
		canvas = new Canvas();
		canvas.setBounds(5, 0, 946, 647);
		canvas.setBackground(Color.WHITE);
		contentPane.add(canvas);
		// ---------------------------------
		// color chooser frame appears when fill or border color buttons is clicked
		// including done button to confirm choosing a certain color
		// --------------------------------------------------------------
		JFrame frame = new JFrame("Color Chooser");
		frame.getContentPane().setLayout(new BorderLayout());
		// Create and set up the content pane.
		tcc = new JColorChooser();
		tcc.setOpaque(true);
		frame.getContentPane().add(tcc);

		JButton colorDone = new JButton("Done");
		frame.getContentPane().add(colorDone, BorderLayout.PAGE_END);

		// Display the window.
		frame.pack();
		frame.setVisible(false);
		// --------------------------------------------------------------
		// color done button action
		// the action takes place after the user chooses the color from the color
		// chooser frame
		// action >> changing the color variables -(fillcolor,bordercolor)- applied to
		// the shapes, also applying the choosen colors to the label boxes
		// -------------------------------------------------------------------------------------------------
		colorDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fillClicked) {
					fillcolor = tcc.getColor();
					Color keep = lbl_fillColor.getBackground();
					lbl_fillColor.setBackground(fillcolor);
					if (ShapeIsSelected) {
						Shape copy;
						try {
							copy = (Shape) shapes[selected].clone();
							copy.getProperties().put("fillcolor", (double) fillcolor.getRGB());
							copy.setFillColor(fillcolor);
							if (copy.getProperties().get("type").intValue() != 6)
								eng.updateShape(shapes[selected], copy);
							canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
							eng.refresh(canvas.getGraphics());
							select_btn.doClick();
							fillcolor = keep;
							lbl_fillColor.setBackground(keep);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (borderClicked) {
					bordercolor = tcc.getColor();
					Color keep = lbl_borderColor.getBackground();
					lbl_borderColor.setBackground(bordercolor);
					if (ShapeIsSelected) {
						Shape copy;
						try {
							copy = (Shape) shapes[selected].clone();
							copy.getProperties().put("color", (double) bordercolor.getRGB());
							copy.setColor(bordercolor);
							eng.updateShape(shapes[selected], copy);
							canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
							eng.refresh(canvas.getGraphics());
							select_btn.doClick();
							bordercolor = keep;
							lbl_borderColor.setBackground(keep);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				fillClicked = false;
				borderClicked = false;
				frame.dispose();
			}
		});
		// -------------------------------------------------------------------------------------------------
		// setting color and fillcolor
		// action >> showing the color chooser frame when clicked
		// ----------------------------------------------------------------------
		JButton btnBorderColor = new JButton("Border Color");
		btnBorderColor.setBounds(959, 0, 130, 23);
		contentPane.add(btnBorderColor);

		JButton btnFillColor = new JButton("Fill Color");
		btnFillColor.setBounds(959, 34, 130, 23);
		contentPane.add(btnFillColor);

		btnBorderColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(true);
				borderClicked = true;
			}
		});

		btnFillColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(true);
				fillClicked = true;
			}
		});
		// ----------------------------------------------------------------------
		// DEFINING VARIABLES
		// undo , redo , save , load and get supported shapes buttons
		// ---------------------------------------------------------------------------
		JButton btnUndo = new JButton("Undo");
		btnUndo.setBounds(959, 104, 130, 23);
		contentPane.add(btnUndo);

		JButton btnRedo = new JButton("Redo");
		btnRedo.setBounds(959, 143, 130, 23);
		contentPane.add(btnRedo);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(959, 589, 130, 23);
		contentPane.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(959, 623, 130, 23);
		contentPane.add(btnLoad);

		JButton btnGetSupportedShapes = new JButton("Supported Shapes");
		btnGetSupportedShapes.setBounds(959, 177, 130, 23);
		contentPane.add(btnGetSupportedShapes);
		// ---------------------------------------------------------------------------
		// get supported shapes button action
		// action >> adding an extra button in the menu bar for the supported shape to
		// control enable the user to draw this shape
		// ----------------------------------------------------------------------------------------------------------
		btnGetSupportedShapes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Class<? extends Shape>> returened = eng.getSupportedShapes();
				int i;
				int x = menuBar.getComponents().length;

				boolean newshape = true;
				for (i = 0; i < returened.size(); i++) {
					newshape = true;
					for (int j = 0; j < x; j++) {
						if (returened.get(i).getSimpleName()
								.equalsIgnoreCase(((JButton) menuBar.getComponents()[j]).getText().toString())) {

							newshape = false;

						}
					}
					if (newshape) {
						// creating the supported shape button and adding it to the menu bar
						btnSupported = new JButton(returened.get(i).getSimpleName());
						menuBar.add(btnSupported);
						menuBar.validate();
						// supported shape button action
						// --------------------------------------------------------------------------
						btnSupported.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								lbl_fillColor.setBackground(fillcolor);
								lbl_borderColor.setBackground(bordercolor);
								canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
								eng.refresh(canvas.getGraphics());
								lineBtnActive = false;
								recBtnActive = false;
								cirBtnActive = false;
								ellBtnActive = false;
								sqBtnActive = false;
								triBtnActive = false;
								roundRecBtnActive = true;
								ShapeIsSelected = false;
								move = false;
								resize = false;
								btnResize.setEnabled(false);
								btnMove.setEnabled(false);
								btnRemove.setEnabled(false);
								btnCopy.setEnabled(false);
							}

						});
						// --------------------------------------------------------------------------
					}
				}

			}
		});
		// ----------------------------------------------------------------------------------------------------------
		// Remove Button Actions
		// action >> remove the selected shape
		// -----------------------------------------------------------------------------------------
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.removeShape(shapes[selected]);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				if (eng.getShapes().length == 0) {
					forwardSelectBtn.setEnabled(false);
					backwardSelectBtn.setEnabled(false);
				}
				if (selected == eng.getShapes().length - 1) {
					forwardSelectBtn.setEnabled(false);
				}
				if (eng.getShapes().length == 1) {
					backwardSelectBtn.setEnabled(false);
				}
				if (selected > eng.getShapes().length - 1) {
					selected = eng.getShapes().length - 1;
				}
			}
		});
		// -----------------------------------------------------------------------------------------
		// Copy Button Actions
		// action >> creating t a copy of the selected shape
		// -----------------------------------------------------------------------------------------
		btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					eng.addShape((Shape) shapes[selected].clone());
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
			}
		});
		// -----------------------------------------------------------------------------------------
		// Move Button Actions
		// action >> applying the move mode for the selected shape
		// --------------------------------------------------------------------------------
		btnMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resize = false;
				move = true;
			}
		});
		// --------------------------------------------------------------------------------
		// Resize Button Actions
		// action >> applying the resizing mode for the selected shape
		// --------------------------------------------------------------------------------
		btnResize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				move = false;
				resize = true;
			}
		});
		// --------------------------------------------------------------------------------
		// file chooser settings
		// appears when save or load button is clicked
		// -----------------------------------------------------------------------------------------
		fileChooser = new JFileChooser();
		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(".xml", "xml");
		FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter(".json", "json");
		fileChooser.addChoosableFileFilter(xmlFilter);
		fileChooser.addChoosableFileFilter(jsonFilter);
		fileChooser.setFileFilter(xmlFilter);
		fileChooser.setFileFilter(jsonFilter);
		// -----------------------------------------------------------------------------------------
		// save button actions
		// action >> showing the file chooser window in save mode
		// -------------------------------------------------------------------------------------------
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.showSaveDialog(null);
				String path = fileChooser.getSelectedFile().getAbsolutePath()
						+ fileChooser.getFileFilter().getDescription();
				eng.save(path);
			}
		});
		// -------------------------------------------------------------------------------------------
		// load button actions
		// action >> showing the file chooser window in load mode
		// -------------------------------------------------------------------------------------------
		btnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.showOpenDialog(null);
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				eng.load(path);
				eng.refresh(canvas.getGraphics());
			}
		});
		// -------------------------------------------------------------------------------------------
		// undo button actions
		// action >> disable the last action done by the user
		// -------------------------------------------------------------------------------------------
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.undo();
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				if (eng.getShapes().length == 0) {
					forwardSelectBtn.setEnabled(false);
					backwardSelectBtn.setEnabled(false);
				}
				if (selected == eng.getShapes().length - 1) {
					forwardSelectBtn.setEnabled(false);
				}
				if (eng.getShapes().length == 0) {
					backwardSelectBtn.setEnabled(false);
				}
				if (selected > eng.getShapes().length - 1) {
					selected = eng.getShapes().length - 1;
				}
			}
		});
		// -------------------------------------------------------------------------------------------
		// redo button actions
		// action >> redone the disabled action done by the user
		// -------------------------------------------------------------------------------------------
		btnRedo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.redo();
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
			}
		});
		// -------------------------------------------------------------------------------------------
		// all shapes buttons actions
		// action >> enable the drawing mode for the selected shape button, also
		// disabling the action of any button pressed before
		// ---------------------------------------------------------------------------------------------------------------
		line_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				lineBtnActive = true;
				recBtnActive = false;
				cirBtnActive = false;
				ellBtnActive = false;
				sqBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});

		square_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				sqBtnActive = true;
				recBtnActive = false;
				cirBtnActive = false;
				ellBtnActive = false;
				lineBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});

		triangle_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				triBtnActive = true;
				recBtnActive = false;
				cirBtnActive = false;
				ellBtnActive = false;
				sqBtnActive = false;
				lineBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});

		ellipse_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				ellBtnActive = true;
				recBtnActive = false;
				cirBtnActive = false;
				sqBtnActive = false;
				lineBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});
		rectangle_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				recBtnActive = true;
				cirBtnActive = false;
				ellBtnActive = false;
				sqBtnActive = false;
				lineBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});

		circle_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lbl_fillColor.setBackground(fillcolor);
				lbl_borderColor.setBackground(bordercolor);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				eng.refresh(canvas.getGraphics());
				cirBtnActive = true;
				recBtnActive = false;
				ellBtnActive = false;
				sqBtnActive = false;
				lineBtnActive = false;
				triBtnActive = false;
				roundRecBtnActive = false;
				ShapeIsSelected = false;
				move = false;
				resize = false;
				btnResize.setEnabled(false);
				btnMove.setEnabled(false);
				btnRemove.setEnabled(false);
				btnCopy.setEnabled(false);
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
		// canvas drawing settings to allow drawing when shapes buttons are selected
		// contains mouse listeners and mouse motion listeners
		// ---------------------------------------------------------------------------
		canvas.addMouseListener(new MouseListener() {
			// action takes place when the mouse is released from the canvas
			// action >> drawing the final wanted shape by the user and adding it to an
			// array containing all the drawn shapes
			// in case of the move or resize mode ,the changes are updated to the selected
			// shape
			@Override
			public void mouseReleased(MouseEvent e) {
				x_rightbottom = e.getX();
				y_rightbottom = e.getY();
				// MOVE MODE
				if (move) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle , supported shape

						int add_x = 0, add_y = 0;
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (type == 1) {
							add_x = copy.getProperties().get("length").intValue() / 2;
							add_y = copy.getProperties().get("width").intValue() / 2;
						}
						if (type == 2) {
							add_x = copy.getProperties().get("side").intValue() / 2;
							add_y = copy.getProperties().get("side").intValue() / 2;
						}
						if (type == 3) {
							add_x = copy.getProperties().get("d_horizontal").intValue() / 2;
							add_y = copy.getProperties().get("d_vertical").intValue() / 2;
						}
						if (type == 4) {
							add_x = copy.getProperties().get("diameter").intValue() / 2;
							add_y = copy.getProperties().get("diameter").intValue() / 2;
						}
						if (type == 7) {
							add_x = copy.getProperties().get("Width").intValue() / 2;
							add_y = copy.getProperties().get("Length").intValue() / 2;
						}
						copy.setPosition(new Point(x - add_x, y - add_y));
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						eng.refresh(canvas.getGraphics());
					} else if (type == 5) {// triangle
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						int x3 = shapes[selected].getProperties().get("x3").intValue();
						int y3 = shapes[selected].getProperties().get("y3").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x3", (double) (x3 + (x - x1)));
						copy.getProperties().put("y3", (double) (y3 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						eng.refresh(canvas.getGraphics());
					} else if (type == 6) {// line
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
					}
				}
				// RESIZE MODE
				if (resize) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle , supported shape

						if (type == 1) {// rectangle
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							length = Math.abs(copy.getPosition().x - x);
							width = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("length", (double) length);
							copy.getProperties().put("width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
							}
						}
						if (type == 2) {// square
							int side = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							side = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("side", (double) side);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							}
						}
						if (type == 3) {// ellipse
							int d_hor = 0, d_ver = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							d_hor = Math.abs(copy.getPosition().x - x);
							d_ver = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("d_horizontal", (double) d_hor);
							copy.getProperties().put("d_vertical", (double) d_ver);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
							}
						}
						if (type == 4) {// circle
							int diameter = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							diameter = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("diameter", (double) diameter);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							}
						}
						if (type == 7) {// supported shape
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							width = Math.abs(copy.getPosition().x - x);
							length = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("Length", (double) length);
							copy.getProperties().put("Width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
							}
						}
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 5) {// triangle
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x3", (double) x);
						copy.getProperties().put("y3", (double) y);
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 6) {// line
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x2", (double) x);
						copy.getProperties().put("y2", (double) y);
						try {
							eng.updateShape(shapes[selected], copy);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						select_btn.doClick();
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					}
				}
				// DRAWING MODE
				// drawing the shape according to the selected button
				// knowing the selected shape button by booleans turned on when the button is
				// clicked
				if (recBtnActive) {
					rec = new Rectangle(new Point(x, y), Math.abs(x_rightbottom - x_topleft),
							Math.abs(y_rightbottom - y_topleft));
					rec.setColor(bordercolor);
					rec.setFillColor(fillcolor);
					rec.draw(canvas.getGraphics());
					eng.addShape(rec);
				} else if (cirBtnActive) {
					cir = new Circle(new Point(x, y), Math.abs(x_rightbottom - x_topleft));
					cir.setColor(bordercolor);
					cir.setFillColor(fillcolor);
					cir.draw(canvas.getGraphics());
					eng.addShape(cir);
				} else if (ellBtnActive) {
					ell = new Ellipse(new Point(x, y), Math.abs(x_rightbottom - x_topleft),
							Math.abs(y_rightbottom - y_topleft));
					ell.setColor(bordercolor);
					ell.setFillColor(fillcolor);
					ell.draw(canvas.getGraphics());
					eng.addShape(ell);
				} else if (sqBtnActive) {
					sq = new Square(new Point(x, y), Math.abs(x_rightbottom - x_topleft));
					sq.setColor(bordercolor);
					sq.setFillColor(fillcolor);
					sq.draw(canvas.getGraphics());
					eng.addShape(sq);
				} else if (lineBtnActive) {
					line = new Line(new Point(x_topleft, y_topleft), new Point(x_rightbottom, y_rightbottom));
					if (x_topleft != x_rightbottom || y_topleft != y_rightbottom) {
						line.setColor(bordercolor);
						line.draw(canvas.getGraphics());
						eng.addShape(line);
					}
				} else if (triBtnActive) {
					if (triPts.size() == 2) {
						if (triPts.get(0) != triPts.get(1)) {
							line = new Line(triPts.get(0), triPts.get(1));
							line.draw(canvas.getGraphics());
						}
					} else if (triPts.size() == 3) {
						triPts.set(2, new Point(e.getX(), e.getY()));
						tri = new Triangle(triPts.get(0), triPts.get(1), triPts.get(2));
						tri.setColor(bordercolor);
						tri.setFillColor(fillcolor);
						tri.draw(canvas.getGraphics());
						eng.addShape(tri);
						triPts.clear();
					} else if (triPts.size() == 1) {
						triPts.add(new Point(e.getX(), e.getY()));
					}
				} else if (roundRecBtnActive) {
					try {
						supported = (Shape) Rectangle.class.getClassLoader()
								.loadClass(Rectangle.class.getName().replace("Rectangle", btnSupported.getText()))
								.newInstance();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					supported.getProperties().put("Width", (double) Math.abs(x_rightbottom - x_topleft));
					supported.getProperties().put("Length", (double) Math.abs(y_rightbottom - y_topleft));
					supported.getProperties().put("ArcWidth", (double) (Math.abs(x_rightbottom - x_topleft) / 10));
					supported.getProperties().put("ArcLength", (double) (Math.abs(y_rightbottom - y_topleft) / 10));
					supported.getProperties().put("x", (double) x);
					supported.getProperties().put("y", (double) y);
					supported.getProperties().put("color", (double) bordercolor.getRGB());
					supported.getProperties().put("fillcolor", (double) fillcolor.getRGB());
					supported.setPosition(new Point(x, y));
					supported.getProperties().put("type", (double) 7);
					supported.setColor(bordercolor);
					supported.setFillColor(fillcolor);
					supported.draw(canvas.getGraphics());
					eng.addShape(supported);
				}
				eng.refresh(canvas.getGraphics());
			}

			// action takes place when the mouse is pressed on the canvas
			// action >> saving the selected point as the top left point for the shape to be
			// drawn
			// in case of the move or resize mode ,the changes are updated to the selected
			// shape
			@Override
			public void mousePressed(MouseEvent e) {
				// move button
				if (move) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle

						int add_x = 0, add_y = 0;
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (type == 1) {
							add_x = copy.getProperties().get("length").intValue() / 2;
							add_y = copy.getProperties().get("width").intValue() / 2;
						}
						if (type == 2) {
							add_x = copy.getProperties().get("side").intValue() / 2;
							add_y = copy.getProperties().get("side").intValue() / 2;
						}
						if (type == 3) {
							add_x = copy.getProperties().get("d_horizontal").intValue() / 2;
							add_y = copy.getProperties().get("d_vertical").intValue() / 2;
						}
						if (type == 4) {
							add_x = copy.getProperties().get("diameter").intValue() / 2;
							add_y = copy.getProperties().get("diameter").intValue() / 2;
						}
						if (type == 7) {
							add_x = copy.getProperties().get("Width").intValue() / 2;
							add_y = copy.getProperties().get("Length").intValue() / 2;
						}
						copy.setPosition(new Point(x - add_x, y - add_y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
					} else if (type == 5) {// triangle
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						int x3 = shapes[selected].getProperties().get("x3").intValue();
						int y3 = shapes[selected].getProperties().get("y3").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x3", (double) (x3 + (x - x1)));
						copy.getProperties().put("y3", (double) (y3 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
					} else if (type == 6) {// line
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
					}

					eng.refresh(canvas.getGraphics());
					select_btn.doClick();
					move = true;
				}
				// resize button
				if (resize) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle

						if (type == 1) {
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							length = Math.abs(copy.getPosition().x - x);
							width = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("length", (double) length);
							copy.getProperties().put("width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
							}
						}
						if (type == 2) {
							int side = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							side = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("side", (double) side);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							}
						}
						if (type == 3) {
							int d_hor = 0, d_ver = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							d_hor = Math.abs(copy.getPosition().x - x);
							d_ver = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("d_horizontal", (double) d_hor);
							copy.getProperties().put("d_vertical", (double) d_ver);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
							}
						}
						if (type == 4) {
							int diameter = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							diameter = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("diameter", (double) diameter);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							}
						}
						if (type == 7) {
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							width = Math.abs(copy.getPosition().x - x);
							length = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("Length", (double) length);
							copy.getProperties().put("Width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
							}
						}
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 5) {
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x3", (double) x);
						copy.getProperties().put("y3", (double) y);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 6) {
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x2", (double) x);
						copy.getProperties().put("y2", (double) y);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					}
				}
				// in case the triangle is the shape to be drawn ,we add the first point to an
				// array containing the 3 points of the rectangle
				// the array will be completely filled when the mouse is released
				if (triBtnActive) {
					if (triPts.size() < 3) {
						triPts.add(new Point(e.getX(), e.getY()));
					}
				} else {
					x_topleft = e.getX();
					y_topleft = e.getY();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			// action takes place when the mouse is dragged on the canvas , the action is
			// repeated until the mouse is released
			// action >> redrawing the shape with the new size according to the user mouse
			// motion
			// in case of the move or resize mode ,the changes are updated to the selected
			// shape
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				x = x_topleft;
				y = y_topleft;
				int dx = Math.abs(e.getX() - x_topleft);
				int dy = Math.abs(e.getY() - y_topleft);

				if (!triBtnActive && !lineBtnActive) {
					if (x_topleft > e.getX() && y_topleft < e.getY())
						x = e.getX();
					else if (y_topleft > e.getY() && x_topleft < e.getX())
						y = e.getY();
					else if (y_topleft > e.getY() && x_topleft > e.getX()) {
						x = e.getX();
						y = e.getY();
					}
				}

				// MOVE MODE
				if (move) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle

						int add_x = 0, add_y = 0;
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (type == 1) {
							add_x = copy.getProperties().get("length").intValue() / 2;
							add_y = copy.getProperties().get("width").intValue() / 2;
						}
						if (type == 2) {
							add_x = copy.getProperties().get("side").intValue() / 2;
							add_y = copy.getProperties().get("side").intValue() / 2;
						}
						if (type == 3) {
							add_x = copy.getProperties().get("d_horizontal").intValue() / 2;
							add_y = copy.getProperties().get("d_vertical").intValue() / 2;
						}
						if (type == 4) {
							add_x = copy.getProperties().get("diameter").intValue() / 2;
							add_y = copy.getProperties().get("diameter").intValue() / 2;
						}
						if (type == 7) {
							add_x = copy.getProperties().get("Width").intValue() / 2;
							add_y = copy.getProperties().get("Length").intValue() / 2;
						}
						copy.setPosition(new Point(x - add_x, y - add_y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 5) {// triangle
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						int x3 = shapes[selected].getProperties().get("x3").intValue();
						int y3 = shapes[selected].getProperties().get("y3").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x3", (double) (x3 + (x - x1)));
						copy.getProperties().put("y3", (double) (y3 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 6) {// line
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int x1 = shapes[selected].getProperties().get("x1").intValue();
						int x2 = shapes[selected].getProperties().get("x2").intValue();
						int y1 = shapes[selected].getProperties().get("y1").intValue();
						int y2 = shapes[selected].getProperties().get("y2").intValue();
						copy.getProperties().put("x2", (double) (x2 + (x - x1)));
						copy.getProperties().put("y2", (double) (y2 + (y - y1)));
						copy.getProperties().put("x1", (double) (x));
						copy.getProperties().put("y1", (double) (y));
						copy.setPosition(new Point(x, y));
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					}
				}
				// RESIZE MODE
				if (resize) {
					int x = e.getX();
					int y = e.getY();
					int type = shapes[selected].getProperties().get("type").intValue();

					Shape copy = null;
					if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7) {// rectangle , square , ellipse
																						// , circle

						if (type == 1) {// rectangle
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							length = Math.abs(copy.getPosition().x - x);
							width = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("length", (double) length);
							copy.getProperties().put("width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("width",
										shapes[selected].getProperties().get("width").intValue() + (double) width);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("length",
										shapes[selected].getProperties().get("length").intValue() + (double) length);
							}
						}
						if (type == 2) {// square
							int side = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							side = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("side", (double) side);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("side",
										shapes[selected].getProperties().get("side").intValue() + (double) side);
							}
						}
						if (type == 3) {// ellipse
							int d_hor = 0, d_ver = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							d_hor = Math.abs(copy.getPosition().x - x);
							d_ver = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("d_horizontal", (double) d_hor);
							copy.getProperties().put("d_vertical", (double) d_ver);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("d_vertical",
										shapes[selected].getProperties().get("d_vertical").intValue() + (double) d_ver);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("d_horizontal",
										shapes[selected].getProperties().get("d_horizontal").intValue()
												+ (double) d_hor);
							}
						}
						if (type == 4) {// circle
							int diameter = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							diameter = Math.abs(copy.getPosition().x - x);
							copy.getProperties().put("diameter", (double) diameter);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("diameter",
										shapes[selected].getProperties().get("diameter").intValue()
												+ (double) diameter);
							}
						}
						if (type == 7) {// supported shape
							int length = 0, width = 0;
							try {
								copy = (Shape) shapes[selected].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							width = Math.abs(copy.getPosition().x - x);
							length = Math.abs(copy.getPosition().y - y);
							copy.getProperties().put("Length", (double) length);
							copy.getProperties().put("Width", (double) width);
							if (x < copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(x, y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x > copy.getPosition().x && y < copy.getPosition().y) {
								copy.setPosition(new Point(shapes[selected].getPosition().x, y));
								copy.getProperties().put("Length",
										shapes[selected].getProperties().get("Length").intValue() + (double) length);
							} else if (x < copy.getPosition().x && y > copy.getPosition().y) {
								copy.setPosition(new Point(x, shapes[selected].getPosition().y));
								copy.getProperties().put("Width",
										shapes[selected].getProperties().get("Width").intValue() + (double) width);
							}
						}
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 5) {// triangle
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x3", (double) x);
						copy.getProperties().put("y3", (double) y);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (type == 6) {// line
						try {
							copy = (Shape) shapes[selected].clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						copy.getProperties().put("x2", (double) x);
						copy.getProperties().put("y2", (double) y);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						copy.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					}
				}
				// DRAWING MODE
				// drawing the selected shape controlled by a boolean for every button
				if (recBtnActive) {
					rec = new Rectangle(new Point(x, y), dx, dy);
					rec.setColor(bordercolor);
					rec.setFillColor(fillcolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					rec.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				} else if (cirBtnActive) {
					cir = new Circle(new Point(x, y), dx);
					cir.setColor(bordercolor);
					cir.setFillColor(fillcolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					cir.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				} else if (ellBtnActive) {
					ell = new Ellipse(new Point(x, y), dx, dy);
					ell.setColor(bordercolor);
					ell.setFillColor(fillcolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					ell.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				} else if (sqBtnActive) {
					sq = new Square(new Point(x, y), dx);
					sq.setColor(bordercolor);
					sq.setFillColor(fillcolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					sq.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				} else if (lineBtnActive) {
					line = new Line(new Point(x, y), new Point(e.getX(), e.getY()));
					line.setColor(bordercolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					line.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				} else if (triBtnActive) {
					if (triPts.size() == 1) {
						line = new Line(triPts.get(0), new Point(e.getX(), e.getY()));
						line.setColor(bordercolor);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						line.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					} else if (triPts.size() == 3) {
						tri = new Triangle(triPts.get(0), triPts.get(1), new Point(e.getX(), e.getY()));
						tri.setColor(bordercolor);
						tri.setFillColor(fillcolor);
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						tri.draw(canvas.getGraphics());
						eng.refresh(canvas.getGraphics());
					}

				} else if (roundRecBtnActive) {
					try {
						supported = (Shape) Rectangle.class.getClassLoader()
								.loadClass(Rectangle.class.getName().replace("Rectangle", btnSupported.getText()))
								.newInstance();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					supported.getProperties().put("Width", (double) dx);
					supported.getProperties().put("Length", (double) dy);
					supported.getProperties().put("ArcWidth", (double) (dx / 10));
					supported.getProperties().put("ArcLength", (double) (dy / 10));
					supported.getProperties().put("x", (double) x);
					supported.getProperties().put("y", (double) y);
					supported.getProperties().put("color", (double) bordercolor.getRGB());
					supported.getProperties().put("fillcolor", (double) fillcolor.getRGB());
					supported.setPosition(new Point(x, y));
					supported.setColor(bordercolor);
					supported.setFillColor(fillcolor);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					supported.draw(canvas.getGraphics());
					eng.refresh(canvas.getGraphics());
				}
			}
		});
		// ---------------------------------------------------------------------------
	}
}

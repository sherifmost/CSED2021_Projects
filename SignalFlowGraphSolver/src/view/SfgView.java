package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Edge;
import model.SfgMaker;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.List;

@SuppressWarnings("serial")
public class SfgView extends JPanel {
	private JTextField numberOfNodesTxt;
	private int numberOfNodes = 0;
	private JTextField gainTxt;
	private int nodeRadius = 10;
	private int[][] nodesCoordinates;
	private int endOfDrawingScreenHeight;
	private Canvas canvas = new Canvas();
	private SfgMaker graph;
	private JTextField inputTxt;
	private JTextField outputTxt;

	public SfgView() {
		setLayout(null);
		setSize(1200, 750);
		Graphics g = canvas.getGraphics();

		numberOfNodesTxt = new JTextField();
		numberOfNodesTxt.setBounds(10, 623, 43, 20);
		add(numberOfNodesTxt);
		numberOfNodesTxt.setColumns(10);

		JComboBox<Integer> fromNodesCbx = new JComboBox<Integer>();
		fromNodesCbx.setBounds(243, 623, 64, 20);
		add(fromNodesCbx);

		JLabel lblEdges = new JLabel("Edges :");
		lblEdges.setBounds(196, 598, 151, 14);
		add(lblEdges);

		JLabel lblNumberOfNodes = new JLabel("Number of Nodes :");
		lblNumberOfNodes.setBounds(10, 598, 176, 14);
		add(lblNumberOfNodes);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(197, 626, 36, 14);
		add(lblFrom);

		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(317, 626, 28, 14);
		add(lblTo);

		JComboBox<Integer> toNodesCbx = new JComboBox<Integer>();
		toNodesCbx.setBounds(355, 623, 64, 20);
		add(toNodesCbx);

		JLabel lblItsGain = new JLabel("It's Gain :");
		lblItsGain.setBounds(429, 626, 59, 14);
		add(lblItsGain);

		gainTxt = new JTextField();
		gainTxt.setBounds(498, 623, 59, 20);
		add(gainTxt);
		gainTxt.setColumns(10);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.setBounds(713, 622, 108, 23);
		add(btnSolve);
		
		List forwardPathsList = new List();
		forwardPathsList.setBounds(831, 598, 110, 100);
		add(forwardPathsList);
		
		JLabel lblForwardPaths = new JLabel("Forward Paths :");
		lblForwardPaths.setBounds(831, 570, 106, 14);
		add(lblForwardPaths);
		
		JLabel lblLoops = new JLabel("Loops :");
		lblLoops.setBounds(947, 570, 70, 14);
		add(lblLoops);
		
		List loopsList = new List();
		loopsList.setBounds(947, 598, 110, 100);
		add(loopsList);
		
		JLabel lblInput = new JLabel("Input :");
		lblInput.setBounds(10, 570, 64, 14);
		add(lblInput);
		endOfDrawingScreenHeight = lblInput.getY();

		
		inputTxt = new JTextField();
		inputTxt.setBounds(84, 570, 86, 20);
		add(inputTxt);
		inputTxt.setColumns(10);
		
		JLabel lblOutput = new JLabel("Output :");
		lblOutput.setBounds(180, 570, 70, 14);
		add(lblOutput);
		
		outputTxt = new JTextField();
		outputTxt.setBounds(261, 570, 86, 20);
		add(outputTxt);
		outputTxt.setColumns(10);

		canvas.setBounds(0, 0, 2000, endOfDrawingScreenHeight);
		add(canvas);

		JButton addNodesBtn = new JButton("Add Nodes");
		addNodesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numberOfNodes = Integer.parseInt(numberOfNodesTxt.getText());
				graph = new SfgMaker(0, numberOfNodes - 1, numberOfNodes);
				nodesCoordinates = new int[numberOfNodes][2];
				numberOfNodesTxt.setEditable(false);
				addNodesBtn.setEnabled(false);
				for (int i = 0; i < numberOfNodes; i++) {
					fromNodesCbx.addItem(i);
					toNodesCbx.addItem(i);
				}
				drawNodes(g);
			}
		});
		addNodesBtn.setBounds(63, 622, 124, 23);
		add(addNodesBtn);

		JButton addEdgeBtn = new JButton("Add Edge");
		addEdgeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int from = Integer.parseInt(fromNodesCbx.getSelectedItem().toString());
				int to = Integer.parseInt(toNodesCbx.getSelectedItem().toString());
				double gain = Double.parseDouble(gainTxt.getText());
				drawEdge(from, to, gainTxt.getText(), g);
				graph.addEdge(new Edge(from, to, gain));
				fromNodesCbx.setSelectedIndex(-1);
				toNodesCbx.setSelectedIndex(-1);
				gainTxt.setText("");
			}
		});
		addEdgeBtn.setBounds(567, 622, 136, 23);
		add(addEdgeBtn);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		btnRefresh.setBounds(10, 654, 176, 23);
		add(btnRefresh);
	}

	private void refresh() {

		canvas.getGraphics().clearRect(0, 0, 2000, endOfDrawingScreenHeight);
		drawNodes(canvas.getGraphics());
		ArrayList<ArrayList<Edge>> edgesWithSameFrom = graph.getEdgesWithSameFrom();
		int numberOfSameFrom = edgesWithSameFrom.size();
		for (int i = 0; i < numberOfSameFrom; i++) {
			int numberOfEdgesFromThisFrom = edgesWithSameFrom.get(i).size();
			for (int j = 0; j < numberOfEdgesFromThisFrom; j++) {
				Edge thisEdge = edgesWithSameFrom.get(i).get(j);
				drawEdge(thisEdge.getFrom(), thisEdge.getTo(), "" + thisEdge.getGain(), canvas.getGraphics());
			}
		}

	}

	/**
	 * Create the panel.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Signal Flow Graph Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		frame.setSize(1200, 750);
		frame.setResizable(false);

		SfgView panel = new SfgView();

		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}

	private void drawNodes(Graphics g) {
		g = canvas.getGraphics();
		Double screenWidth = super.getSize().getWidth();
		int x = 15;
		int y = endOfDrawingScreenHeight / 2;
		int xGap = (screenWidth.intValue() - numberOfNodes * nodeRadius * 2) / numberOfNodes;
		for (int i = 0; i < numberOfNodes; i++) {
			g.drawOval(x, y, 2 * nodeRadius, 2 * nodeRadius);
			g.drawString(String.valueOf(i), (x + nodeRadius - 3), (y + nodeRadius + nodeRadius / 2));
			nodesCoordinates[i][0] = x;
			nodesCoordinates[i][1] = y;
			x += 2 * nodeRadius + xGap;
		}
	}

	private void drawEdge(int from, int to, String gain, Graphics g) {
		Double screenWidth = super.getSize().getWidth();
		int xGap = (screenWidth.intValue() - numberOfNodes * nodeRadius * 2) / numberOfNodes;
		String rightArrow = "-->>";
		String leftArrow = "<<--";
		Graphics2D g2 = (Graphics2D) canvas.getGraphics();
		Path2D.Double edge = new Path2D.Double();
		int xFrom = nodesCoordinates[from][0];
		int yFrom = nodesCoordinates[from][1];
		int xTo = nodesCoordinates[to][0];
		int yTo = nodesCoordinates[to][1];
		g = canvas.getGraphics();
		if (Math.abs(from - to) == 1) {
			if (from < to) {
				edge.moveTo(xFrom + 2 * nodeRadius, yFrom + nodeRadius);
				g.drawString(gain, (xTo + xFrom + 2 * nodeRadius) / 2, yFrom);
				g.drawString(rightArrow, (xTo + xFrom + 2 * nodeRadius) / 2, yFrom + nodeRadius);
				edge.lineTo(xTo, yTo + nodeRadius);
			} else if (from > to) {
				edge.moveTo(xFrom, yFrom + nodeRadius);
				g.drawString(gain, ((xTo + xFrom + 2 * nodeRadius) / 2) + 5, yFrom);
				g.drawString(leftArrow, (xTo + xFrom + 2 * nodeRadius) / 2, yFrom + nodeRadius);
				edge.lineTo(xTo + 2 * nodeRadius, yTo + nodeRadius);
			}
			g2.draw(edge);
		} else if (Math.abs(from - to) > 1) {
			if (from < to) {
				edge.moveTo(xFrom + nodeRadius, yFrom);
				g.drawString(gain, ((xTo + xFrom + 2 * nodeRadius) / 2) - 5,
						(yFrom + (from - to) * (xGap / 2) / 2) - 5);
				g.drawString(rightArrow, ((xTo + xFrom + 2 * nodeRadius) / 2) - 5,
						(yFrom + (from - to) * (xGap / 2) / 2) + 5);
				edge.curveTo(xFrom + nodeRadius, yFrom, (xTo + xFrom + 2 * nodeRadius) / 2,
						yFrom + (from - to) * (xGap / 2), xTo + nodeRadius, yTo);
			} else if (from > to) {
				edge.moveTo(xFrom + nodeRadius, yFrom + 2 * nodeRadius);
				g.drawString(gain, ((xTo + xFrom + 2 * nodeRadius) / 2),
						(yFrom + 2 * nodeRadius + (from - to) * (xGap / 2) / 2) + 15);
				g.drawString(leftArrow, ((xTo + xFrom + 2 * nodeRadius) / 2),
						(yFrom + 2 * nodeRadius + (from - to) * (xGap / 2) / 2) + 5);
				edge.curveTo(xFrom + nodeRadius, yFrom + 2 * nodeRadius, (xTo + xFrom + 2 * nodeRadius) / 2,
						yFrom + 2 * nodeRadius + (from - to) * (xGap / 2), xTo + nodeRadius, yTo + 2 * nodeRadius);
			}
			g2.draw(edge);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

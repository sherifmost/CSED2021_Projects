package model;

public class Edge {

	private int from;
	private int to;
	private double gain;
    //can two edges have the same from,to,gain?
	
	public Edge(int from, int to, double gain) {
		this.from = from;
		this.to = to;
		this.gain = gain;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public boolean equals(Edge e) {
		if (this.getFrom() == e.getFrom() && this.getTo() == e.getTo())//no two edges can join the same node.
			return true;
		return false;
	}

}

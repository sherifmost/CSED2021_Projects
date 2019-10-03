package eg.edu.alexu.csd.filestructure.graphs;

public class Edge {// a class having all the data required for an edge
	private int from;
	private int to;
	private int weight;

	public Edge(int from, int to, int weight) {
		setFrom(from);
		setTo(to);
		setWeight(weight);
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	// additional methods that may be useful.
	public boolean equals(Edge e) {
		return (e.getFrom() == this.getFrom() && e.getTo() == this.getTo() && e.getWeight() == this.getWeight());

	}

	public boolean sameFrom(Edge e) {
		return (this.getFrom() == e.getFrom());
	}

	public boolean sameTo(Edge e) {
		return (this.getTo() == e.getTo());
	}
}

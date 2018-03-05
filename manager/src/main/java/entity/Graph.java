package entity;

import java.util.Set;

public class Graph {

	private Set<Node> nodes;
	private Set<Link> links;

	public Graph(Set<Node> nodes, Set<Link> links) {
		this.nodes = nodes;
		this.links = links;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public Set<Link> getLinks() {
		return links;
	}
}

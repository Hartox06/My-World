package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}

	
	public void generateGraph() {
		costGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile tile : GraphTraversal.BFS(super.source)) {
			for (Tile neighbour : tile.neighbors) {
				if (neighbour.isWalkable()) {
					double weight = neighbour.distanceCost;
					if (costGraph.areMetros(tile,neighbour)) {
						((MetroTile) tile).fixMetro(neighbour);
						weight = ((MetroTile) tile).metroDistanceCost;
					}
					costGraph.addEdge(tile, neighbour, weight);
				}
			}
		}

		damageGraph = new Graph(GraphTraversal.BFS(super.source));
		aggregatedGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile tile : GraphTraversal.BFS(source)) {
			for (Tile neighbour : tile.neighbors) {
				if (neighbour.isWalkable()) {
					damageGraph.addEdge(tile, neighbour, neighbour.damageCost);
					aggregatedGraph.addEdge(tile, neighbour, neighbour.damageCost);
				}
			}
		}
	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
		boolean returned = false;

		super.g = costGraph;
		ArrayList<Tile> pc = super.findPath(start,waypoints);		//shortest distance path
		double dPc = getDamage(pc);									//damage cost of shortest distance path

		if (dPc < health) {
			return pc;
		}

		super.g = damageGraph;
		ArrayList<Tile> pd = super.findPath(start,waypoints);		//least damage path
		double dPd = pd.get(pd.size()-1).costEstimate;				//damage cost of least damage path
		if (dPd > health) {
			return null;
		}

		while(!returned) {
			double cPc = pc.get(pc.size()-1).costEstimate;		//distance cost of shortest distance path
			double cPd = getDistance(pd);						//distance cost of least damage path

			double multiplier = (cPc - cPd)/(dPd - dPc);
			fillAggGraph(multiplier);

			super.g = aggregatedGraph;
			ArrayList<Tile> pr = super.findPath(start,waypoints);
			double damageCostpr = getDamage(pr);
			double totalAggCost = pr.get(pr.size()-1).costEstimate;
			double totalAggCostpc = cPc + (multiplier * dPc);

			if (totalAggCost == totalAggCostpc) {
				returned = true;
				return pd;
			} else if (health >= damageCostpr) {
				pd = new ArrayList<>(pr);
			} else {
				pc = new ArrayList<>(pc);
			}
		}
		return null;
	}

	private double getDamage(ArrayList<Tile> path) {
		double damage = 0;
		for (Tile tile : path) {
			damage += tile.damageCost;
		}
		damage -= path.get(0).damageCost;
		return damage;
	}

	private void fillAggGraph(double multiplier) {
		for (int row=0; row < aggregatedGraph.numOfVertices; row++) {
			for (int column=0; column < aggregatedGraph.numOfVertices; column++) {
				if (aggregatedGraph.Matrix[row][column] != null) {
					double distanceCost = costGraph.Matrix[row][column].weight;
					double damageCost = damageGraph.Matrix[row][column].weight;
					double aggCost = distanceCost + (multiplier * damageCost);
					aggregatedGraph.Matrix[row][column].weight = aggCost;
					aggregatedGraph.Matrix[column][row].weight = aggCost;
				}
			}
		}
	}

	private double getDistance(ArrayList<Tile> path) {
		double distance = 0;
		for (Tile tile : path) {
			distance += tile.distanceCost;
		}
		distance -= path.get(0).distanceCost;
		return distance;
	}

}

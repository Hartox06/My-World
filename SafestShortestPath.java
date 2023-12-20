package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;

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
		// TODO Auto-generated method stub
		costGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile specificTile : GraphTraversal.BFS(super.source)) {
			for (Tile neighbor : specificTile.neighbors) {
				if (neighbor.isWalkable()) {
					double weight = neighbor.distanceCost;
					if (specificTile.isMetro() && neighbor.isMetro()) {
						weight = g.helperLevel7Shortest(specificTile,neighbor);
					}
					costGraph.addEdge(specificTile, neighbor, weight);
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


	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
		//FIRST PART
		super.g = costGraph;	//we set the graph field from the superclass equal to CostGraph
		ArrayList<Tile> pc = super.findPath(start,waypoints);		//optimal path with least distance cost
		double damagePc = costGraph.computePathCost(pc);									//damage cost of pc
		if (damagePc<health) {
			return pc;
		}

		//SECOND PART
		super.g = damageGraph;	//we set the graph field from the superclass equal to DamageGraph
		ArrayList<Tile> pd = super.findPath(start,waypoints);		//optimal path with least damage cost
		double damagePd = damageGraph.computePathCost(pd);				//damage cost of the optimal path
		if (damagePd>health) {
			return null;
		}

		//THIRD PART
		while(true) {
			double distancePc = getTotalDistance(pc);		//distance cost of shortest distance path
			double distancePd = getTotalDistance(pd);						//distance cost of least damage path
			double lambda = (distancePc-distancePd)/(damagePd - damagePc);

			fillAggGraph(lambda);
			super.g = aggregatedGraph;
			ArrayList<Tile> pr = super.findPath(start,waypoints);
			double distancePr = getTotalDistance(pr);
			double damagePr = damageGraph.computePathCost(pr);
			double totalAggregateCostPr = distancePr + (lambda*damagePr);
			double totalAggregateCostPd = distancePc + (lambda*damagePc);

			if (totalAggregateCostPr == totalAggregateCostPd) {
				return pd;
			} else if (totalAggregateCostPr <= health) {
				pd=pr;
			} else {
				pc=pr;
			}
		}
	}

	private void fillAggGraph(double multiplier) {
		for (int row=0; row < aggregatedGraph.numberOfVertices; row++) {
			for (int col=0; col < aggregatedGraph.numberOfVertices; col++) {
				if (aggregatedGraph.Matrix[row][col] != null) {
					double aggCost = calculateAggregatedCost(row, col, multiplier);
					aggregatedGraph.Matrix[row][col].weight = aggCost;
					aggregatedGraph.Matrix[col][row].weight = aggCost;
				}
			}
		}
	}

	private double calculateAggregatedCost(int row, int col, double multiplier) {
		double costOfDistance = costGraph.Matrix[row][col].weight;
		double costOfDamage = damageGraph.Matrix[row][col].weight;
		return costOfDistance + (multiplier * costOfDamage);
	}

	private double getTotalDamage(ArrayList<Tile> path) {
		double totalDamage = 0;
		for (Tile tile : path) {
			totalDamage = totalDamage + tile.damageCost;
		}
		return totalDamage - path.get(0).damageCost;
	}

	private double getTotalDistance(ArrayList<Tile> path) {
		double totalDistance=0;
		for (Tile specificTile : path) {
			totalDistance = totalDistance + specificTile.distanceCost;
		}
		return totalDistance-path.get(0).distanceCost;
	}
}

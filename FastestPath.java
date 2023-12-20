package finalproject;

import finalproject.system.Tile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();

    }

	@Override
	public void generateGraph() {   //OKAY
		// TODO Auto-generated method stub
        super.g = new Graph(GraphTraversal.BFS(source));
        for (Tile specificTile : GraphTraversal.BFS(source)) {
            for (Tile neighbor : specificTile.neighbors) {
                if (neighbor.isWalkable()) {
                    double totalWeight = neighbor.timeCost;
                    if (specificTile.isMetro() && neighbor.isMetro()) {
                        totalWeight = g.helperLevel7Fastest(specificTile,neighbor);
                    }
                    g.addEdge(specificTile, neighbor, totalWeight);
                }
            }
        }
	}

}

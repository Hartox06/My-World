package finalproject;


import finalproject.system.Tile;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        super.g = new Graph(GraphTraversal.BFS(source));
        for (Tile specificTile : GraphTraversal.BFS(source)) {
            for (Tile neighbor : specificTile.neighbors) {
                if (neighbor.isWalkable()) {
                    double totalWeight = neighbor.distanceCost;
                    if (specificTile.isMetro() && neighbor.isMetro()) {
                        totalWeight = g.helperLevel7Shortest(specificTile,neighbor);
                    }
                    g.addEdge(specificTile, neighbor, totalWeight);
                }
            }
        }
	}
}

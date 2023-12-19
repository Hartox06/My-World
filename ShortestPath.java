package finalproject;


import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

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
        for (Tile tile : GraphTraversal.BFS(source)) {
            for (Tile neighbour : tile.neighbors) {
                if (neighbour.isWalkable()) {
                    double weight = neighbour.distanceCost;
                    if (tile.type == TileType.Metro && neighbour.type == TileType.Metro) {
                        ((MetroTile) tile).fixMetro(neighbour);
                        weight = ((MetroTile) tile).metroDistanceCost;
                    }
                    g.addEdge(tile, neighbour, weight);
                }
            }
        }
	}
}

package finalproject;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
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
                    double weight = neighbour.timeCost;
                    if (tile.type==TileType.Metro && neighbour.type==TileType.Metro) {
                        ((MetroTile) tile).fixMetro(neighbour);
                        weight = ((MetroTile) tile).metroTimeCost;
                    }
                    g.addEdge(tile, neighbour, weight);
                }
            }
        }
	}

}

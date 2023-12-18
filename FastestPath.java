package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

    @Override
    public void generateGraph() {
        super.g = new Graph(GraphTraversal.BFS(source));
        for (Tile tile : GraphTraversal.BFS(source)) {
            for (Tile neighbour : tile.neighbors) {
                if (neighbour.isWalkable()) {       //ADDED FOR TESTER
                    double weight = neighbour.timeCost;
                    if (g.areMetros(tile,neighbour)) {      //ADDED FOR TESTER
                        ((MetroTile) tile).fixMetro(neighbour);
                        weight = ((MetroTile) tile).metroTimeCost;
                    }
                    g.addEdge(tile, neighbour, weight);
                }
            }
        }
    }
}

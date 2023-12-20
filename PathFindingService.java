package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
    TilePriorityQ PriorityQueue;
    ArrayList<Tile> ShortestPath;

	public PathFindingService(Tile start) {

        this.source = start;
    }

	public abstract void generateGraph();

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {   //OKAY
        dijkstra(startNode);
        for (Tile specificTile : ShortestPath) {
            if (specificTile.isDestination) {
                startNode = specificTile;
            }
        }
        return pathToTile(startNode);
    }

    private ArrayList<Tile> pathToTile(Tile tile) { //OKAY
        ArrayList<Tile> actualPath = new ArrayList<>();
        actualPath.add(tile);
        while (tile.predecessor != null) {
            tile = tile.predecessor;
            actualPath.add(0,tile);
        }
        return actualPath;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) { //OKAY
        dijkstra(start);
        for (Tile specificTile : ShortestPath) {
            if (specificTile.nodeID == end.nodeID) {
                end = specificTile;
            }
        }
        return pathToTile(end);
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){    //OKAY 100%
        ArrayList<Tile> path = new ArrayList<>();
        for (Tile specificTile : waypoints) {
            path.addAll(findPath(start,specificTile));
            path.remove(path.size()-1);
            start = specificTile;
        }
        ArrayList<Tile> foundPath = findPath(start);
        for (Tile tile : foundPath){
            path.add(tile);
        }
        return path;
    }


    private void dijkstra(Tile origin) {    //OKAY
        ShortestPath = new ArrayList<>();
        initSingleSource(origin);
        while(!PriorityQueue.heap.isEmpty()) {      //CHANGE IN LAST MINUTE
            Tile u = PriorityQueue.removeMin();
            ShortestPath.add(u);
            for (Tile v : g.getNeighbors(u)) {
                relax(u, v);
            }
        }
    }

    private void initSingleSource(Tile originNode) {    //OKAY 100%
        for (Tile tile : g.listOfVertices) {        //set predecessor to null and cost estimate to max
            tile.costEstimate = Double.MAX_VALUE;
            tile.predecessor = null;
        }
        PriorityQueue = new TilePriorityQ(g.listOfVertices);
        PriorityQueue.updateKeys(originNode,null,0);
    }


    private void relax(Tile u, Tile v) {    //OKAY 100%
        ArrayList<Tile> path = new ArrayList<>();
        path.add(u);
        path.add(v);
        double newEstimate = u.costEstimate + g.computePathCost(path);
        if (v.costEstimate > newEstimate) {
            PriorityQueue.updateKeys(v,u,newEstimate);
        }
    }
}


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
    public ArrayList<Tile> findPath(Tile startNode) {
        dijkstra(startNode);
        for (Tile tile : ShortestPath) {
            if (tile.isDestination) {
                startNode = tile;
            }
        }
        return pathToTile(startNode);
    }

    private ArrayList<Tile> pathToTile(Tile tile) { //OKAY TRY TO MODIFY
        ArrayList<Tile> path = new ArrayList<>();
        path.add(tile);
        while (tile.predecessor != null) {
            tile = tile.predecessor;
            path.add(0,tile);
        }
        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        dijkstra(start);
        for (Tile tile : ShortestPath) {
            if (tile.nodeID == end.nodeID) {
                end = tile;
            }
        }
        return pathToTile(end);
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> path = new ArrayList<>();
        for (Tile tile : waypoints) {
            path.addAll(findPath(start,tile));
            path.remove(path.size()-1);
            start = tile;
        }
        path.addAll(findPath(start));
        return path;
    }


    private void dijkstra(Tile start) {
        ShortestPath = new ArrayList<>();
        initSingleSource(start);
        while(PriorityQueue.size > 0) {
            Tile u = PriorityQueue.removeMin();
            ShortestPath.add(u);
            for (Tile v : g.getNeighbors(u)) {
                relax(u, v);
            }
        }
    }

    private void initSingleSource(Tile startNode) {
        for (Tile tile : g.listOfVertices) {        //set predecessor to null and cost estimate to max
            tile.costEstimate = Double.MAX_VALUE;
            tile.predecessor = null;
        }
        PriorityQueue = new TilePriorityQ(g.listOfVertices);
        PriorityQueue.updateKeys(startNode,null,0);
    }


    private void relax(Tile u, Tile v) {
        ArrayList<Tile> path = new ArrayList<>();
        path.add(u);
        path.add(v);
        double newEstimate = u.costEstimate + g.computePathCost(path);
        if (v.costEstimate > newEstimate) {
            PriorityQueue.updateKeys(v,u,newEstimate);
        }
    }
}


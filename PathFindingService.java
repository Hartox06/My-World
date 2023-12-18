package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
    TilePriorityQ Q;
    ArrayList<Tile> S;
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        dijkstra(startNode);
        for (Tile t : S) {
            if (t.isDestination) {
                startNode = t;
            }
        }
        return pathToTile(startNode);
    }


    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        dijkstra(start);
        for (Tile t : S) {
            if (t.nodeID == end.nodeID) {
                end = t;
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
        S = new ArrayList<>();      //ArrayList to store Tiles in shortest path
        initSS(start);

        while(Q.size > 0) {
            Tile u = Q.removeMin();
            S.add(u);
            for (Tile v : g.getNeighbors(u)) {
                relax(u, v);
            }
        }
    }

    private void initSS(Tile startNode) {
        for (Tile tile : g.listOfVertices) {        //set predecessor to null and cost estimate to max
            tile.costEstimate = Double.MAX_VALUE;
            tile.predecessor = null;
        }
        Q = new TilePriorityQ(g.listOfVertices);
        Q.updateKeys(startNode,null,0);
    }

    private void relax(Tile u, Tile v) {
        ArrayList<Tile> path = new ArrayList<>();
        path.add(u); path.add(v);
        double newEstimate = u.costEstimate + g.computePathCost(path);
        if (v.costEstimate > newEstimate) {
            Q.updateKeys(v,u,newEstimate);
        }
    }


    private ArrayList<Tile> pathToTile(Tile tile) {
        ArrayList<Tile> path = new ArrayList<>();
        path.add(tile);
        while (tile.predecessor != null) {
            tile = tile.predecessor;
            path.add(0,tile);
        }
        return path;
    }
}


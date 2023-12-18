package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

    @Override
	public void generateGraph() {
        super.g = new Graph(GraphTraversal.BFS(source));
        for (Tile tile : GraphTraversal.BFS(source)) {
            for (Tile neighbour : tile.neighbors) {
                if (neighbour.isWalkable()) {       //ADDED FOR TESTER
                    double weight = neighbour.distanceCost;
                    if (g.areMetros(tile,neighbour)) {
                        ((MetroTile) tile).fixMetro(neighbour);
                        weight = ((MetroTile) tile).metroDistanceCost;
                    }
                    g.addEdge(tile, neighbour, weight);
                }
            }
        }
    }

    /*
    public static void main(String args[]) {
        ArrayList<Tile> stuff = new ArrayList<>();
        ArrayList<Tile> zeroNeighbours = new ArrayList<>();
        ArrayList<Tile> firstNeighbours = new ArrayList<>();
        ArrayList<Tile> secondNeighbours = new ArrayList<>();
        ArrayList<Tile> thirdNeighbours = new ArrayList<>();
        ArrayList<Tile> fourthNeighbours = new ArrayList<>();
        ArrayList<Tile> fifthNeighbours = new ArrayList<>();

        Tile zero = new DesertTile(); zero.nodeID = 0; //zero.costEstimate = 0.0;
        Tile first = new DesertTile();  first.nodeID = 1; //first.costEstimate = 1.0;
        Tile second = new DesertTile(); second.nodeID = 2; //second.costEstimate = 2.0;
        Tile third = new DesertTile(); third.nodeID = 3; //third.costEstimate = 3.0;
        Tile fourth = new DesertTile(); fourth.nodeID = 4; //fourth.costEstimate = 4.0;
        Tile fifth = new DesertTile(); fifth.nodeID = 5; //fifth.costEstimate = 5.0;
        //fourth.isDestination = true;

        zeroNeighbours.add(first); zeroNeighbours.add(second);
        firstNeighbours.add(zero); firstNeighbours.add(second); firstNeighbours.add(fourth); firstNeighbours.add(fifth);
        secondNeighbours.add(zero); secondNeighbours.add(first); secondNeighbours.add(third); secondNeighbours.add(fifth);
        thirdNeighbours.add(second); thirdNeighbours.add(fourth); thirdNeighbours.add(fifth);
        fourthNeighbours.add(first); fourthNeighbours.add(third); fourthNeighbours.add(fifth);
        fifthNeighbours.add(first); fifthNeighbours.add(second); fifthNeighbours.add(third); fifthNeighbours.add(fourth);

        zero.neighbors = zeroNeighbours;
        first.neighbors = firstNeighbours;
        second.neighbors = secondNeighbours;
        third.neighbors = thirdNeighbours;
        fourth.neighbors = fourthNeighbours;
        fifth.neighbors = fifthNeighbours;

        PathFindingService blue = new ShortestPath(zero);

        blue.g.addEdge(zero,first,2);
        blue.g.addEdge(zero,second,1);

        blue.g.addEdge(first,second,7);
        blue.g.addEdge(first,fifth,4);
        blue.g.addEdge(first,fourth,8);

        blue.g.addEdge(second,third,7);
        blue.g.addEdge(second,fifth,3);

        blue.g.addEdge(third,fourth,8);
        blue.g.addEdge(third,fifth,4);

        blue.g.addEdge(fourth,fifth,5);


        ArrayList<Tile> shortPath = blue.findPath(zero);
        System.out.println("Short path:");
        for (Tile tile : blue.findPath(zero)) {
          System.out.println(tile);
        }

        /*stuff.add(first); stuff.add(second); stuff.add(third); stuff.add(fourth); stuff.add(fifth);

        Graph plotter = new Graph(stuff);

         */

    //}

    
}
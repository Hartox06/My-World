package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

import java.util.Scanner;

public class Graph {
    // TODO level 2: Add fields that can help you implement this data type
    public Edge[][] Matrix;
    public int numOfVertices;      //number of vertices
    public ArrayList<Tile> listOfVertices = new ArrayList<>();       //Array containing all the vertices
    public ArrayList<Integer> matrixIdx = new ArrayList<>();       //store nodeID's
    public HashSet<Integer> alreadyAdded = new HashSet<>();       //The HashSet allows us to keep track of what already have been added to the graph to not make duplicates

    // TODO level 2: initialize and assign all variables inside the constructor
    public Graph(ArrayList<Tile> vertices) {
        this.numOfVertices = vertices.size();     //we store the number of vertices in the public field
        Matrix = new Edge[numOfVertices][numOfVertices];
        for (Tile verticeToAdd : vertices) {
            if (!alreadyAdded.contains(verticeToAdd.nodeID)) {  //using the hash set, we check for each tile if it is not already added to avoid duplicates
                alreadyAdded.add(verticeToAdd.nodeID); //we add the nodeID's in the array list matrixIdx
                this.matrixIdx.add(verticeToAdd.nodeID);    //we add the nodeID's in the array list matrixIdx
                this.listOfVertices.add(verticeToAdd);
            }
        }
    }

    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight) {
        int originIdx = matrixIdx.indexOf(origin.nodeID);    //get index of the origin tile
        int destinationIdx = matrixIdx.indexOf(destination.nodeID);     //get index of the destination tile
        Edge cur1 = new Edge(origin,destination,weight);
        Edge cur2 = new Edge(destination,origin,weight);
        Matrix[originIdx][destinationIdx] = cur1;      //add weight of path to adjacency matrix
        //Matrix[destinationIdx][originIdx] = rCurr;      //CHANGED TO PASS TESTER
    }

    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> allEdges = new ArrayList<>();      //new ArrayList to store all the edges from this graph
        for (Edge[] row : Matrix) {     //we iterate through each row of Matrix
            for (Edge edge : row) {
                if (edge != null) {     //in case the edge to add is null, it will not add it to the allEdges arraylist
                    allEdges.add(edge);
                }
            }
        }
        return allEdges;   //return all the edges from this graph
    }


    /*
    public ArrayList<Tile> getNeighbors2(Tile t) {  to remove
        ArrayList<Tile> neighbours = new ArrayList<>();         //create the LinkedList to store neighbours
        for (Tile neighbour : t.neighbors) {        //iterate through the neighbours of the dequeued Tile
            if (neighbour.isWalkable()) {        //make sure Tile is walkable
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

     */


    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<>();  //arraylist to store all the neighbors of t
        int idx = matrixIdx.indexOf(t.nodeID);
        for (Edge edgeToAdd : Matrix[idx]) {    //iterate through all the neighbors of t
            if (edgeToAdd != null) {    //in case the edge to add is null, it will not add it to the neighbor arraylist
                neighbors.add(edgeToAdd.destination);
            }
        }
        return neighbors;   //returns the array list of neighbors
    }


    // TODO level 2: return total cost for the input path
    public double computePathCost(ArrayList<Tile> path) {
        ArrayList<Integer> targetIdxs = new ArrayList<>();
        double totalWeight = 0;            //in case the path is empty it will return 0
        for (Tile tile : path) {        //fill in list of the indices of the Tiles in path
            targetIdxs.add(matrixIdx.indexOf(tile.nodeID));
        }
        int targetIdxSize = targetIdxs.size();
        for (int index = 0; index < targetIdxSize-1; index++) {
            if (Matrix[targetIdxs.get(index)][targetIdxs.get(index+1)] != null) {
                totalWeight += Matrix[targetIdxs.get(index)][targetIdxs.get(index+1)].weight;
            } else {
                return Double.MAX_VALUE;    //not needed, should remove it
            }
        }
        return totalWeight;  //return the weight of the path
    }

    public boolean areMetros(Tile first, Tile second) {
        if(first.type == TileType.Metro && second.type == TileType.Metro) {
            return true;
        }
        return false;
    }


    public static class Edge {
        double weight;
        Tile origin;
        Tile destination;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost) {  //check if cost should be a int, instead of a double
            this.weight = cost;
            this.origin = s;
            this.destination = d;
        }

        // TODO level 2: getter function 1
        public Tile getStart() {
            return this.origin;
        }


        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

        @Override
        public String toString() {
            return "Origin: " + origin.nodeID + " Destination: " + destination.nodeID + " Weight " + weight;
        }
    }
}
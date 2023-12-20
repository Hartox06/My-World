package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type  //OKAY 100%
    public Edge[][] Matrix;
    public int numberOfVertices;
    public ArrayList<Tile> listOfVertices = new ArrayList<>();       //Array containing all the vertices
    public ArrayList<Integer> matrixIndex = new ArrayList<>();       //store nodeID's
    public HashSet<Integer> alreadyAdded = new HashSet<>();       //The HashSet allows us to keep track of what already have been added to the graph to not make duplicates


    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {    //OKAY 100%
        this.numberOfVertices = vertices.size();     //we store the number of vertices in the public field
        Matrix = new Edge[numberOfVertices][numberOfVertices];
        for (Tile verticeToAdd : vertices) {
            alreadyAdded.add(verticeToAdd.nodeID); //we add the nodeID's in the array list matrixIdx
            this.listOfVertices.add(verticeToAdd);
            this.matrixIndex.add(verticeToAdd.nodeID);    //we add the nodeID's in the array list matrixIdx
        }
	}
	
    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
        int originIndex = matrixIndex.indexOf(origin.nodeID);    //get index of the origin tile
        int destinationIndex = matrixIndex.indexOf(destination.nodeID);     //get index of the destination tile
        Edge cur1 = new Edge(origin,destination,weight);
        Edge cur2 = new Edge(destination,origin,weight);
        Matrix[originIndex][destinationIndex] = cur1;      //add weight of path to adjacency matrix
    }
    
    // TODO level 2: return a list of all edges in the graph
	public ArrayList<Edge> getAllEdges() {  //OKAY 100%
        ArrayList<Edge> allEdges = new ArrayList<>();      //new ArrayList to store all the edges from this graph
        for (Edge[] elements : Matrix) {     //we iterate through each row of Matrix
            for (Edge edge : elements) {
                if (edge != null) {     //in case the edge to add is null, it will not be added in the allEdges arraylist
                    allEdges.add(edge);
                }
            }
        }
        return allEdges;   //return all the edges from this graph
	}
  
	// TODO level 2: return list of tiles adjacent to t
	public ArrayList<Tile> getNeighbors(Tile t) {   //OKAY 100%
        ArrayList<Tile> neighbors = new ArrayList<>();  //arraylist to store all the neighbors of t
        int index = matrixIndex.indexOf(t.nodeID);
        for (Edge edgeToAdd : Matrix[index]) {    //iterate through all the neighbors of t
            if (edgeToAdd != null) {    //in case the edge to add is null, it will not add it to the neighbor arraylist
                neighbors.add(edgeToAdd.destination);
            }
        }
        return neighbors;   //returns the array list of neighbors
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        ArrayList<Integer> targetIndexs = new ArrayList<>();
        double totalWeight = 0;            //in case the path is empty it will return 0
        for (Tile tile : path) {        //fill in list of the indices of the Tiles in path
            targetIndexs.add(matrixIndex.indexOf(tile.nodeID));
        }
        int targetSize = targetIndexs.size();
        for (int index=0;index<targetSize-1;index++) {
            if (Matrix[targetIndexs.get(index)][targetIndexs.get(index+1)] != null) {
                totalWeight += Matrix[targetIndexs.get(index)][targetIndexs.get(index+1)].weight;
            } else {
                return Double.MAX_VALUE;    //not needed, should remove it
            }
        }
        return totalWeight;  //return the weight of the path
	}

    public double helperLevel7Fastest(Tile specificTile, Tile neighbor){
        ((MetroTile) specificTile).fixMetro(neighbor);
        return ((MetroTile) specificTile).metroTimeCost;

    }

    public double helperLevel7Shortest(Tile specificTile, Tile neighbor){
        ((MetroTile) specificTile).fixMetro(neighbor);
        return ((MetroTile) specificTile).metroDistanceCost;

    }
	
   
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){   //OKAY 100%
            this.weight = cost;
            this.origin = s;
            this.destination = d;
        }
        
        // TODO level 2: getter function 1
        public Tile getStart(){     //OKAY 100%

            return this.origin;
        }

        
        // TODO level 2: getter function 2
        public Tile getEnd() {  //OKAY 100%

            return this.destination;
        }
    }

    /*
    public static void main(String[] args) {
        ArrayList<Tile> testVertices = new ArrayList<>();
        testVertices.add(new DesertTile());
        testVertices.add(new MountainTile());
        testVertices.add(new PlainTile());
        testVertices.add(new MetroTile());
        testVertices.add(new ZombieInfectedRuinTile());
        Graph testGraph = new Graph(testVertices);
        System.out.println(testVertices);
        System.out.println(testGraph.listOfVertices);
        for (int i =0;i<testGraph.numberOfVertices;i++){
            testGraph.addEdge(testGraph.listOfVertices.get(0), testGraph.listOfVertices.get(1), i);
        }
        System.out.println(testGraph.getAllEdges());
    }

     */
}

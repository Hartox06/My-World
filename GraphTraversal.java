package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {		//in case s do not have any neighbors, it returns tilesVisited with only s in it
		ArrayList<Tile> tilesVisited = new ArrayList<>();	//initialize ArrayList to return containing Tiles visited in order and determine if visited or not
		LinkedList<Tile> queue = new LinkedList<>();			//initialize a queue to go through Tiles

		tilesVisited.add(s);	//add the first Tile to visitedTiles
		queue.add(s);		//add the first tile to the queue

		while (!queue.isEmpty()) {			//stop iteration when there are no more element in the queue
			Tile curr = queue.poll();		//start with the first element of the queue
			for (Tile neighbor : curr.neighbors) {		//iterate through the neighbours of the dequeued Tile
				if (neighbor.isWalkable() && !tilesVisited.contains(neighbor)) {		//check if tile is walkable and hasn't been visitied
					tilesVisited.add(neighbor); 	//we add the neighbor to the tiles visited
					queue.add(neighbor);		//add neighbor to the queue
				}
			}
		}
		return tilesVisited;		//return the tiles visited in order
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> visitedTiles = new ArrayList<>();	//initialize ArrayList to return containing Tiles visited in order and determine if visited or not
		Stack<Tile> stack = new Stack<>();		//initialize a stack to go through Tiles

		visitedTiles.add(s);	//we add s to the tiles visited
		stack.add(s);	//we add s to the stack

		while (!stack.isEmpty()) {			//stop iteration when there are no more element in the queue
			Tile curr = stack.pop();		//start with last stack item
			for (Tile neighbour : curr.neighbors) {		//iterate through the neighbours of the popped Tile
				if (!visitedTiles.contains(neighbour) && neighbour.isWalkable()) {		//make sure Tile hasn't been visited and is walkable
					visitedTiles.add(neighbour);	//we add the neighbor to the tiles visited
					stack.add(neighbour);		//we add the neighbor to the stack
				}
			}
		}
		return visitedTiles;		//return the tiles visited in order
	}

}  
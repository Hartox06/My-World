package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s)	//OKAY 100%
	{
		ArrayList<Tile> tilesVisited = new ArrayList<>();	//initializing an ArrayList containing the tiles visited (we will also use it to know if a tile already have been visited).
		LinkedList<Tile> queue = new LinkedList<>();			//initialize a queue to go through the tiles

		tilesVisited.add(s);	//add the first Tile to visitedTiles
		queue.add(s);		//add the first tile to the queue

		while (!queue.isEmpty()) {			//stop iteration when there are no more elements in the queue
			Tile currentTile = queue.poll();
			for (Tile neighbor : currentTile.neighbors) {		//iterate through the neighbours of the dequeued Tile
				if (neighbor.isWalkable() && !tilesVisited.contains(neighbor)) {		//check if tile is walkable and hasn't been visitied yet
					tilesVisited.add(neighbor); 	//we add the neighbor to the tiles visited
					queue.add(neighbor);		//add neighbor to the queue
				}
			}
		}
		return tilesVisited;		//return the tiles visited in order
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {		//OKAY 100%
		ArrayList<Tile> visitedTiles = new ArrayList<>();	//initializing an ArrayList containing the tiles visited (we will also use it to know if a tile already have been visited).
		LinkedList<Tile> stack = new LinkedList<>();		//initialize a stack to go through Tiles

		visitedTiles.add(s);	//we add s to the tiles visited
		stack.add(s);	//we add s to the stack

		while (!stack.isEmpty()) {			//stop iteration when there are no more elements in the stack
			Tile cur = stack.remove(stack.size() - 1);
			for (Tile neighbor : cur.neighbors) {		//iterate through the neighbours of the popped tile
				if (!visitedTiles.contains(neighbor) && neighbor.isWalkable()) {		//make sure Tile hasn't been visited and is walkable
					visitedTiles.add(neighbor);	//we add the neighbor to the tiles visited
					stack.add(neighbor);		//we add the neighbor to the stack
				}
			}
		}
		return visitedTiles;		//return the tiles visited in order
	}

}  

package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;
import finalproject.tiles.DesertTile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	public ArrayList<Tile> heap = new ArrayList<>();
	public int size;

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {	//OKAY
		for (Tile verticesToAdd : vertices){	//we add all the vertices to the array list heap
			heap.add(verticesToAdd);
		}
		size = heap.size();
		for (int index=(size/2) -1; index>= 0; index--) {
			downHeap(index);
		}
	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {	//OKAY
		Tile minTile = heap.get(0);	//we store the root
		Tile lastTile = heap.remove(--size);	//store and remove the last element
		if (size > 0){
			heap.set(0,lastTile);	//we set the root to the last Tile
			downHeap(0);
		}
		return minTile;	//we return the element removed
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int tileIndex = TileIndex(t);
		if (tileIndex == -1) return;	//we check wether or not the tile belongs to the queue
		Tile tile = heap.get(tileIndex);
		updateTile(tile, newPred, newEstimate, tileIndex);
	}

	private void updateTile(Tile tile, Tile newPred, double newEstimate, int tileIndex) {
		tile.predecessor = newPred;
		double previousEstimate = tile.costEstimate;
		if (previousEstimate > newEstimate) {
			tile.costEstimate = newEstimate;
			adjustHeap(tileIndex, previousEstimate, newEstimate);
		}
	}

	private void adjustHeap(int tileIndex, double oldEstimate, double newEstimate) {	//Helper method to adjust the heap
		if (newEstimate < oldEstimate) {
			upHeap(tileIndex);
		} else if (newEstimate > oldEstimate) {
			downHeap(tileIndex);
		}
	}
	private int TileIndex(Tile t) {		//Helper methods to find the index of a specific tile, if the tile doesn't exist it returns -1
		for (int index=0; index<size; index++) {
			if (heap.get(index).nodeID == t.nodeID) {
				return index;
			}
		}
		return -1;
	}

	/*
	public void add(Tile tile) {
		if (TileIndex(tile) != -1) {
			updateKeys(tile,tile.predecessor,tile.costEstimate);
		} else {
			heap.add(size, tile);
			size++;
			upHeap(size - 1);
		}
	}

	 */

	private void downHeap(int originIndex) {
		while (2*originIndex + 1 < size) {		//we check wether or no there is a left child
			int smallerChild = findSmallerChild(originIndex);
			if (heap.get(smallerChild).costEstimate >= heap.get(originIndex).costEstimate) {
				break;
			}
			swapElements(originIndex,smallerChild);
			originIndex = smallerChild;
			}
	}

	private int findSmallerChild(int index) {	//helper method to find the smallest child
		int leftChild = 2*index+1;
		int rightChild = leftChild + 1;
		if (rightChild < size && heap.get(rightChild).costEstimate < heap.get(leftChild).costEstimate) {
			return rightChild;
		}
		return leftChild;
	}


	private void upHeap(int index) {
		while (index > 0){
			if (heap.get(index).costEstimate >= heap.get((index-1)/2).costEstimate){
				break;
			}
			swapElements(index,(index-1)/2);
			index = (index-1)/2;
		}
	}

	private void swapElements(int indexOne, int indexTwo) {	//helper method to swap elements (use in downHeap and upHeap to make the methode more readable)
		Tile tmp = heap.get(indexOne);
		heap.set(indexOne, heap.get(indexTwo));
		heap.set(indexTwo, tmp);
	}
}

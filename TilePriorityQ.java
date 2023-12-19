package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	public int size;
	public ArrayList<Tile> heap = new ArrayList<>();

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {	//OKAY 100%
		for (Tile verticesToAdd : vertices){	//we add all the vertices to the array list heap
			heap.add(verticesToAdd);
		}
		size = heap.size();
		for (int index=(size/2) -1; index>= 0; index--) {
			downHeap(index);
		}
	}
	
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {	//CHECK IF WORKS
		Tile minTile = heap.get(0);	//we store the root
		Tile lastTile = heap.remove(--size);	//store and remove the last element
		if (size > 0){
			heap.set(0,lastTile);	//we set the root to the last Tile
			downHeap(0);
		}
		return minTile;	//we return the element removed
	}
	
	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {	//CHECK IF WORKS
		int tileIndex = TileIndex(t);
		if (tileIndex == -1) return;	//we check wether or not the tile belongs to the queue
		Tile tile = heap.get(tileIndex);
		updateTile(tile, newPred, newEstimate, tileIndex);
	}

	private void updateTile(Tile tile, Tile newPred, double newEstimate, int tileIndex) {
		tile.predecessor = newPred;
		double previousEstimate = tile.costEstimate;
		if (previousEstimate != newEstimate) {
			tile.costEstimate = newEstimate;
			adjustHeap(tileIndex, previousEstimate, newEstimate);
		}
	}

	private void adjustHeap(int tileIndex, double oldEstimate, double newEstimate) {	//Helper method to adjust the heap NOT SURE 100% CHECK THE INEQUALITY
		if (newEstimate < oldEstimate) {
			upHeap(tileIndex);
		} else {
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

	private void downHeap(int originIndex) {
		while (originIndex*2 + 1 < size) {		//if there is a left child
			int child = originIndex*2+1;
			if (child+1<size) {		//if there is a right sibling
				if (heap.get(child+1).costEstimate < heap.get(child).costEstimate) {		//if right child < left child
					child++;
				}
			}
			if (heap.get(child).costEstimate < heap.get(originIndex).costEstimate) {		//do we need to swap with child?
				swap(originIndex, child);
				originIndex = child;
			} else {
				break;
			}
		}
	}

	private void upHeap(int index) {
		while (index > 0){
			if (heap.get(index).costEstimate >= heap.get((index-1)/2).costEstimate){
				break;
			}
			swap(index,(index-1)/2);
			index = (index-1)/2;
		}
	}

	private void swap(int indexOne, int indexTwo) {	//helper method to swap elements (use in downHeap and upHeap to make the methode more readable)
		Tile tmp = heap.get(indexOne);
		heap.set(indexOne, heap.get(indexTwo));
		heap.set(indexTwo, tmp);
	}
}

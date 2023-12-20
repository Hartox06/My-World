package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;


public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	public ArrayList<Tile> heap = new ArrayList<>();

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {	//OKAY 100%
		for (Tile verticeToAdd : vertices){	//we add all the vertices to the array list heap
			heap.add(verticeToAdd);
		}
		for (int index=(heap.size()/2)-1; index>= 0; index--) {
			downHeap(index);
		}
	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		if (heap.isEmpty()) return null;	//edge case, if the heap is empty we return null
		Tile minTile = heap.get(0);		//we store the root
		heap.set(0,heap.get(heap.size() -1));		//we set the root to the last Tile
		heap.remove(heap.size()-1);	//we remove the last element
		if (!heap.isEmpty()){
			downHeap(0);
		}
		return minTile;	//we return the element removed
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int tileIndex = heap.indexOf(t);
		if (tileIndex == -1 ) return;	//we check if the tile belongs to the queue

		Tile curTile = heap.get(tileIndex);
		curTile.predecessor = newPred;	//we update the predecessor
		curTile.costEstimate = newEstimate;	//we update the estimate cost

		while (tileIndex>0 && curTile.costEstimate < heap.get(parent(tileIndex)).costEstimate){
			swap(tileIndex, parent(tileIndex));
			tileIndex=parent(tileIndex);
		}
	}

	private int rightChild(int index){	//helper method to avoid writting 2*index+2 which is not readable
		return (2*index+2);
	}

	private int leftChild(int index){	//helper method to avoid writting 2*index+1 which is not readable
		return (2*index+1);
	}
	private int parent(int index){	//helper method to avoid writting index-1/2 which is not readable
		return (index-1)/2;
	}

	private void downHeap(int originIndex) {
		int leftChild = leftChild(originIndex);	//determine the index of the left child of the current index
		int rightChild = rightChild(originIndex);	//determine the index of the right child of the current index
		int small=originIndex;	//hold the index of the smallest value
		if (leftChild < heap.size() && heap.get(leftChild).costEstimate < heap.get(small).costEstimate){	//check if left child exists and...
			small=leftChild;
		}

		if (rightChild < heap.size() && heap.get(rightChild).costEstimate < heap.get(small).costEstimate){	//check if right child exists and...
			small=rightChild;
		}

		if (small != originIndex){	//if smallest value is not the current index swap them and call the method itself to keep going
			swap(originIndex,small);
			downHeap(small);
		}
	}

	private void swap(int indexOne, int indexTwo) {	//helper method to swap elements (use in downHeap, updateKeys to make the methode more readable)
		Tile tmp = heap.get(indexOne);
		heap.set(indexOne, heap.get(indexTwo));
		heap.set(indexTwo, tmp);
	}
}

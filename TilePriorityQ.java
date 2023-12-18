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
	public TilePriorityQ (ArrayList<Tile> vertices) {
		for (Tile verticesToAdd : vertices){	//we add all the vertices to the array list heap
			heap.add(verticesToAdd);
		}
		size = heap.size();
		for (int k=(size/2) -1; k>= 0; k--) {
			downHeap(k);
		}
	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile tmp = heap.get(0);	//we store the root
		heap.set(0,heap.get(size-1));	//we set the root to the last leaf node
		heap.remove(size -1);	//we remove the last leaf node
		size--;	//as we remove one element, the size decreases by one as well
		downHeap(0);
		return tmp;	//we return the element remove
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int tileIdx = findTile(t);
		if (tileIdx == -1) {
			return;
		}

		Tile tile = heap.get(tileIdx);
		tile.predecessor = newPred;		//update predecessor field
		double oldEstimate = tile.costEstimate;

		if (oldEstimate != newEstimate) {
			tile.costEstimate = newEstimate;		//update cost estimate field
			//make sure heap property is maintained:
			if (oldEstimate > newEstimate) {
				upHeap(tileIdx);
			} else {
				downHeap(tileIdx);
			}
		}
	}

	public void add(Tile tile) {
		if (findTile(tile) != -1) {
			updateKeys(tile,tile.predecessor,tile.costEstimate);
		} else {
			heap.add(size, tile);
			size++;
			upHeap(size - 1);
		}

	}


	private int findTile(Tile t) {
		for (int index=0; index<size; index++) {
			if (heap.get(index).nodeID == t.nodeID) {
				return index;
			}
		}
		return -1;
	}

	private void downHeap(int startIdx) {
		while (2*startIdx + 1 < size) {		//if there is a left child
			int child = 2*startIdx+1;
			if (child + 1 < size) {		//if there is a right sibling
				if (heap.get(child + 1).costEstimate < heap.get(child).costEstimate) {		//if right child < left child
					child++;
				}
			}
			if (heap.get(child).costEstimate < heap.get(startIdx).costEstimate) {		//do we need to swap with child?
				Tile temp = heap.get(startIdx);
				heap.set(startIdx, heap.get(child));
				heap.set(child, temp);
				startIdx = child;
			} else {
				break;
			}
		}
	}

	private void upHeap(int i) {
		while ( i > 0 && heap.get(i).costEstimate < heap.get((i-1)/2).costEstimate){
			Tile temp = heap.get(i);
			heap.set(i, heap.get((i-1)/2));
			heap.set((i-1)/2, temp);
			i = (i-1)/2;
		}
	}
}

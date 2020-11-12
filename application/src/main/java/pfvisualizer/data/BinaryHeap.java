package pfvisualizer.data;

import pfvisualizer.util.Node;

public class BinaryHeap implements Heap {
  private Node[] heap;
  private int capacity;
  private int size;

  /**
   * A min heap for Node objects (sorted by heuristic).
   */
  public BinaryHeap() {
    this.heap = new Node[INITIAL_CAPACITY + 1];
    Node leftEdge = new Node(0, 0, null);
    leftEdge.setHeuristic(Integer.MIN_VALUE);
    heap[0] = leftEdge;
    this.capacity = INITIAL_CAPACITY;
    this.size = 0;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void insert(Node node) {
    if (size >= capacity) {
      capacity = capacity * 2;
      Node[] newHeap = new Node[capacity + 1];
      for (int i = 0; i < heap.length; i++) {
        newHeap[i] = heap[i];
      }
      heap = newHeap;
    }
    heap[++size] = node;
    int current = size;
    int parent = size / 2;
    while (heap[current].getHeuristic() < heap[parent].getHeuristic()) {
      Node tmp = heap[current];
      heap[current] = heap[parent];
      heap[parent] = tmp;
      current = parent;
      parent = current / 2;
    }
  }

  @Override
  public Node extractMin() {
    int current = 1;
    Node node = heap[current];
    heap[current] = heap[size--];

    // heapify
    while (true) {
      int left = 2 * current;
      int right = 2 * current + 1;

      // if current node is smaller or equal to its children, we are done
      if ((left > size || heap[current].getHeuristic() <= heap[left].getHeuristic())
          && (right > size || heap[current].getHeuristic() <= heap[right].getHeuristic())) {
        break;
      }

      Node tmp = heap[current];
      if (heap[left].getHeuristic() < heap[right].getHeuristic()) {
        heap[current] = heap[left];
        heap[left] = tmp;
        current = left;
      } else {
        heap[current] = heap[right];
        heap[right] = tmp;
        current = right;
      }
    }
    return node;
  }
}

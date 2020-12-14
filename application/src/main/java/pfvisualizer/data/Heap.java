package pfvisualizer.data;

import pfvisualizer.util.Node;

public interface Heap {
  /**
   * The initial capacity of the heap.
   */
  int INITIAL_CAPACITY = 2;

  /**
   * Checks if the heap is empty of not.
   *
   * @return true if the heap is empty and false if it is not
   */
  boolean isEmpty();


  /**
   * Inserts a node into the heap.
   *
   * @param node node to be inserted
   */
  void insert(Node node);


  /**
   * Removes and returns the min node.
   *
   * @return the min node
   */
  Node extractMin();
}

package pfvisualizer.data;

import pfvisualizer.util.Node;

public interface Heap {
  int INITIAL_CAPACITY = 2;
  boolean isEmpty();

  void insert(Node node);

  Node extractMin();
}

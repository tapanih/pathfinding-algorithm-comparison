package pfvisualizer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pfvisualizer.data.BinaryHeap;
import pfvisualizer.util.Node;

public class BinaryHeapTest {
  float delta = 0.001f;

  private Node nodeWithHeuristicValueOf(float heuristic) {
    Node node = new Node(0, 0,  null);
    node.setHeuristic(heuristic);
    return node;
  }

  @Test
  public void binaryHeapIsEmptyWhenItIsReallyEmpty() {
    BinaryHeap heap = new BinaryHeap();
    assertTrue(heap.isEmpty());
    heap.insert(nodeWithHeuristicValueOf(0));
    assertFalse(heap.isEmpty());
    heap.extractMin();
    assertTrue(heap.isEmpty());
  }

  @Test
  public void binaryHeapElementsAreRemovedInTheCorrectOrder() {
    BinaryHeap heap = new BinaryHeap();
    heap.insert(nodeWithHeuristicValueOf(1));
    assertEquals(1, heap.extractMin().getHeuristic(), delta);

    heap.insert(nodeWithHeuristicValueOf(1));
    heap.insert(nodeWithHeuristicValueOf(2));
    assertEquals(1, heap.extractMin().getHeuristic(), delta);
    heap.insert(nodeWithHeuristicValueOf(3));
    assertEquals(2, heap.extractMin().getHeuristic(), delta);
    heap.insert(nodeWithHeuristicValueOf(2));
    assertEquals(2, heap.extractMin().getHeuristic(), delta);
    assertEquals(3, heap.extractMin().getHeuristic(), delta);

    heap.insert(nodeWithHeuristicValueOf(4));
    heap.insert(nodeWithHeuristicValueOf(2));
    heap.insert(nodeWithHeuristicValueOf(1));
    heap.insert(nodeWithHeuristicValueOf(3));
    assertEquals(1, heap.extractMin().getHeuristic(), delta);
    assertEquals(2, heap.extractMin().getHeuristic(), delta);
    assertEquals(3, heap.extractMin().getHeuristic(), delta);
    assertEquals(4, heap.extractMin().getHeuristic(), delta);
  }
}

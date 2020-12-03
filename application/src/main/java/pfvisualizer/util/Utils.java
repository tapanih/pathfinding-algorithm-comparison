package pfvisualizer.util;

/**
 * A static utility class.
 */
public class Utils {

  /**
   * Creates a 2d array initialized with the given value.
   *
   * @param width width of the new array
   * @param height height of the new array
   * @param value the initial value of all elements
   * @return a 2d array initialized with the given value
   */
  public static float[][] initialize2dArray(int width, int height, float value) {
    float[][] array = new float[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        array[row][col] = value;
      }
    }
    return array;
  }


  /**
   * Copies the first n elements of a given array to a new array of size n.
   *
   * @param array array to copy elements from
   * @param n number of elements to be copied
   * @return a new array with n first elements from the given array
   */
  public static Node[] arrayCopyOf(Node[] array, int n) {
    Node[] newArray = new Node[n];
    for (int i = 0; i < n; i++) {
      newArray[i] = array[i];
    }
    return newArray;
  }


  /**
   * Copies a given 2d array.
   *
   * @param width width of the array
   * @param height height of the array
   * @param array array to be copied
   * @return copy of the given array
   */
  public static int[][] copy2dArray(int width, int height, int[][] array) {
    int[][] newArray = new int[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newArray[row][col] = array[row][col];
      }
    }
    return newArray;
  }
}

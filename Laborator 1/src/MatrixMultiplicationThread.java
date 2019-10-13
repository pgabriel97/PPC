import java.util.List;

public class MatrixMultiplicationThread implements Runnable {
    private int line;
    private int column;
    private int productElement;
    private List<List<Integer>> firstMatrix;
    private List<List<Integer>> secondMatrix;
    private Thread thread;

    /**
     *
     * @param firstMatrix First full matrix to be multiplied
     * @param line Line number of the first matrix to be used by thread
     * @param secondMatrix Second full matrix to be multiplied
     * @param column Column number of the second matrix to be used by thread
     */
    MatrixMultiplicationThread(List<List<Integer>> firstMatrix, int line, List<List<Integer>> secondMatrix, int column) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.line = line;
        this.column = column;
        this.thread = new Thread(this);
        thread.start();
    }

    /**
     * Multiplies elements from @line of a matrix with elements from @column of another matrix
     * @param line Line number of the first matrix
     * @param column Column of the second matrix
     * @return Product of elements from @line and elements from @column
     */
    public int multiplyLineColumn(int line, int column) {
        int sumOfProducts = 0;
        for (int i = 0; i < firstMatrix.get(0).size(); i++) {
            sumOfProducts +=
                    firstMatrix.get(line).get(i)
                    * secondMatrix.get(i).get(column);
        }
        return sumOfProducts;
    }

    /**
     * getThread
     *
     * @return Thread that executes Merge Sort of an array
     */
    Thread getThread() {
        return this.thread;
    }

    /**
     * getNumbersArray
     *
     * @return The product of elements from a line of a matrix and a column of another matrix
     */
    int getProductElement() {
        return this.productElement;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running... ");
        this.productElement = multiplyLineColumn(line, column);
        System.out.println("Thread " + Thread.currentThread().getId() + " finished product for line "
                + (line + 1) + " of A and column " + (column + 1) + " of B: " + this.productElement);
    }
}

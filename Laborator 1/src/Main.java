import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("\n----- SUM USING THREADS -----");
        Utility.sumUsingThreads();

        System.out.println("----- MERGE SORT USING THREADS -----");
        Utility.mergeSortUsingThreads();

        System.out.println("----- MATRIX MULTIPLICATION USING THREADS -----");
        Utility.matrixMultiplicationUsingThreads();
    }
}
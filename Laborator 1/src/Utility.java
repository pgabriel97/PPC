import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Utility {
    private static final String FILENAME_SUM = "sum_data.txt";
    private static final String FILENAME_SORT = "sort_data.txt";
    private static final String FILENAME_FIRST_MATRIX = "first_matrix.txt";
    private static final String FILENAME_SECOND_MATRIX = "second_matrix.txt";
    /**
     * Reads array of Integers from @file
     *
     * @param file File that contains Integers
     * @return A list of the integers read from @file
     * @throws FileNotFoundException Throws error if file doesn't exist
     */
    private static List<Integer> readArrayFromFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        List<Integer> numbersArray = new ArrayList<>();

        while (sc.hasNext()) {
            int current = Integer.parseInt(sc.next());
            numbersArray.add(current);
        }
        return numbersArray;
    }

    /**
     * Reads the number of threads from keyboard
     *
     * @return An integer, the number of threads to be used
     */
    private static int readThreads() {
        System.out.print("How many threads: ");
        Scanner scKey = new Scanner(System.in);
        return Integer.parseInt(scKey.next());
    }

    /**
     * Sums a list of Integers using a given number of threads
     *
     * @throws InterruptedException  Throws error if there is a threading problem
     * @throws FileNotFoundException Throws error if file is not found
     */
    static void sumUsingThreads() throws InterruptedException, FileNotFoundException {
        File file = new File(System.getProperty("user.dir") + "\\src\\" + FILENAME_SUM);
        List<Integer> numbersArray = readArrayFromFile(file);

        int threadsNumber = readThreads();
        if (threadsNumber > numbersArray.size())
            threadsNumber = numbersArray.size();

        int step = numbersArray.size() / threadsNumber;
        int arraySize = numbersArray.size();
        int start = 0;
        int end = step;

        long startTime = System.currentTimeMillis();
        SumThread[] sumThreadsArray = new SumThread[arraySize];

        for (int i = 0; i < threadsNumber; i++) {
            List<Integer> subArray;
            if (i == threadsNumber - 1) {
                subArray = numbersArray.subList(start, arraySize);
                sumThreadsArray[i] = new SumThread(subArray);
                break;
            }
            subArray = numbersArray.subList(start, end);
            sumThreadsArray[i] = new SumThread(subArray);
            start = end;
            end += step;
        }

        int totalSum = 0;
        for (int i = 0; i < threadsNumber; i++) {
            sumThreadsArray[i].getThread().join();
            totalSum += sumThreadsArray[i].getSum();
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Total sum: " + totalSum);
        System.out.println("It took " + elapsedTime / 1000F + " seconds.\n");
    }

    /**
     * Sorts an array using multithreaded Merge Sort
     *
     * @throws FileNotFoundException Throws error if there is a threading problem
     * @throws InterruptedException  Throws error if file is not found
     */
    static void mergeSortUsingThreads() throws FileNotFoundException, InterruptedException {
        File file = new File(System.getProperty("user.dir") + "\\src\\" + FILENAME_SORT);
        List<Integer> numbersArray = readArrayFromFile(file);

        long startTime = System.currentTimeMillis();
        MergeSortThread firstMergeSortThread =
                new MergeSortThread(numbersArray.subList(0, numbersArray.size() / 2));
        MergeSortThread secondMergeSortThread =
                new MergeSortThread(numbersArray.subList(numbersArray.size() / 2, numbersArray.size()));

        firstMergeSortThread.getThread().join();
        secondMergeSortThread.getThread().join();

        System.out.println("After merging: " + MergeSortThread.merge
                (firstMergeSortThread.getNumbersArray(), secondMergeSortThread.getNumbersArray()));
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("It took " + elapsedTime / 1000F + " seconds.\n");

    }

    /**
     * Reads matrix from text file
     * @param file File which contains dimensions of matrix, then matrix elements
     * @return Matrix read from the file
     * @throws FileNotFoundException Exception when no file is found
     */
    static List<List<Integer>> readMatrixFromFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        int lines = Integer.parseInt(sc.next());
        int columns = Integer.parseInt(sc.next());

        List<List<Integer>> matrix = new ArrayList<>(lines);

        for (int i = 0; i < lines; i++) {
            List<Integer> currentLine = new ArrayList<>(columns);
            for (int j = 0; j < columns; j++) {
                currentLine.add(Integer.parseInt(sc.next()));
            }
            matrix.add(currentLine);
        }

        return matrix;
    }

    /**
     * Multiplies two matrices using threads
     * @throws FileNotFoundException Exception when no file is found
     * @throws InterruptedException Exception when there is a problem with threads
     */
    static void matrixMultiplicationUsingThreads() throws FileNotFoundException, InterruptedException {

        File file = new File(System.getProperty("user.dir") + "\\src\\" + Utility.FILENAME_FIRST_MATRIX);
        List<List<Integer>> firstMatrix = Utility.readMatrixFromFile(file);
        file = new File(System.getProperty("user.dir") + "\\src\\" + Utility.FILENAME_SECOND_MATRIX);
        List<List<Integer>> secondMatrix = Utility.readMatrixFromFile(file);

        MatrixMultiplicationThread[][] matrixThreads = new MatrixMultiplicationThread[firstMatrix.size()][secondMatrix.get(0).size()];
        Integer[][] productMatrix = new Integer[firstMatrix.size()][secondMatrix.get(0).size()];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < firstMatrix.size(); i++) {
            for (int j = 0; j < secondMatrix.get(0).size(); j++) {
                matrixThreads[i][j] = new MatrixMultiplicationThread(firstMatrix, i, firstMatrix, j);
            }
        }

        for (int i = 0; i < firstMatrix.size(); i++) {
            for (int j = 0; j < secondMatrix.get(0).size(); j++) {
                matrixThreads[i][j].getThread().join();
                productMatrix[i][j] = matrixThreads[i][j].getProductElement();
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("\nMatrix multiplication result:");
        for (int i = 0; i < firstMatrix.size(); i++) {
            for (int j = 0; j < secondMatrix.get(0).size(); j++) {
                System.out.print(productMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nIt took " + elapsedTime / 1000F + " seconds.");
    }
}

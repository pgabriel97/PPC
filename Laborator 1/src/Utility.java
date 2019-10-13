import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Utility {
    private static final String FILENAME_SUM = "sum_data.txt";
    private static final String FILENAME_SORT = "sort_data.txt";

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
        System.out.print("\nHow many threads: ");
        Scanner scKey = new Scanner(System.in);
        return Integer.parseInt(scKey.next());
    }

    /**
     * Sums a list of Integers using a given number of threads
     * @throws InterruptedException Throws error if there is a threading problem
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
        System.out.println("It took " + elapsedTime/1000F + " seconds.\n");
    }

    /**
     * Sorts an array using multithreaded Merge Sort
     * @throws FileNotFoundException Throws error if there is a threading problem
     * @throws InterruptedException Throws error if file is not found
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
        System.out.println("It took " + elapsedTime/1000F + " seconds.\n");

    }
}

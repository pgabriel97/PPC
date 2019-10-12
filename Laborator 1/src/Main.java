import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String FILENAME = "sum_data.txt";

    /**
     * Reads array of Integers from @file
     * @param file File that contains Integers
     * @return A list of the integers read from @file
     * @throws FileNotFoundException Throws error if file doesn't exist
     */
    private static List<Integer> readArrayFromFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        List<Integer> numbersArray = new ArrayList<>();

        System.out.print("\nThe complete array:\n");
        while (sc.hasNext()) {
            int current = Integer.parseInt(sc.next());
            System.out.print(current + " ");
            numbersArray.add(current);
        }
        return numbersArray;
    }

    /**
     * Reads the number of threads from keyboard
     * @return An integer, the number of threads to be used
     */
    private static int readThreads() {
        System.out.print("\n\nHow many threads: ");
        Scanner scKey = new Scanner(System.in);
        return Integer.parseInt(scKey.next());
    }

    /**
     * Sums the Integers of @numbersArray using @threadsNumber threads
     * @param numbersArray List of Integers to be summed
     * @param threadsNumber Number of threads to be used
     * @return The sum of the Integers in @numbersArray
     * @throws InterruptedException Throws error if there is a threading problem
     */
    private static int sumUsingThreads(List<Integer> numbersArray, int threadsNumber) throws InterruptedException {
        int step = numbersArray.size() / threadsNumber;
        int arraySize = numbersArray.size();
        int start = 0;
        int end = step;

        Thread[] threadsArray = new Thread[arraySize];
        SumThread[] sumThreadsArray = new SumThread[arraySize];

        for (int i = 0; i < threadsNumber; i++) {
            List<Integer> subArray;
            if (i == threadsNumber - 1) {
                subArray = numbersArray.subList(start, arraySize);
                sumThreadsArray[i] = new SumThread(subArray);
                threadsArray[i] = new Thread(sumThreadsArray[i]);
                threadsArray[i].start();
                break;
            }
            subArray = numbersArray.subList(start, end);
            sumThreadsArray[i] = new SumThread(subArray);
            threadsArray[i] = new Thread(sumThreadsArray[i]);
            threadsArray[i].start();
            start = end;
            end += step;
        }

        int totalSum = 0;
        for (int i = 0; i < threadsNumber; i++) {
            threadsArray[i].join();
            totalSum += sumThreadsArray[i].getSum();
        }

        return totalSum;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        File file = new File(System.getProperty("user.dir") + "\\src\\" + FILENAME);
        List<Integer> numbersArray = readArrayFromFile(file);

        int threadsNumber = readThreads();
        if (threadsNumber > numbersArray.size())
            threadsNumber = numbersArray.size();

        System.out.println("Total sum: " + sumUsingThreads(numbersArray, threadsNumber));
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSortThread implements Runnable {

    private List<Integer> numbersArray;
    private Thread thread;

    /**
     * MergeSortThread constructor. Creates a thread that automatically sorts an array using Merge Sort
     * @param arrayToMergeSort Array of numbers to be sorted inside this thread
     */
    MergeSortThread(List<Integer> arrayToMergeSort) {
        this.numbersArray = arrayToMergeSort;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Sorts @array using Merge Sort algorithm
     * @param array An array to be sorted
     * @return The sorted @array
     */
    private static List<Integer> mergeSort(List<Integer> array) {
        if (array.size() == 1) {
            return Collections.singletonList(array.get(0));
        }
        int middle = array.size() / 2;
        List<Integer> firstHalf = mergeSort(array.subList(0, middle));
        List<Integer> secondHalf = mergeSort(array.subList(middle, array.size()));
        return merge(firstHalf, secondHalf);
    }

    /**
     * Merges to sorted arrays
     * @param firstHalf First sorted array to be merged
     * @param secondHalf Second sorted array to be merged
     * @return The array obtained after merging @firstHalf and @secondHalf
     */
    static List<Integer> merge(List<Integer> firstHalf, List<Integer> secondHalf) {
        int i = 0, j = 0;
        List<Integer> sortedArray = new ArrayList<>();
        while (i < firstHalf.size() && j < secondHalf.size()) {
            if (firstHalf.get(i) <= secondHalf.get(j)) {
                sortedArray.add(firstHalf.get(i));
                i++;
            } else {
                sortedArray.add(secondHalf.get(j));
                j++;
            }
        }

        while (i == firstHalf.size() && j < secondHalf.size()) {
            sortedArray.add(secondHalf.get(j));
            j++;
        }
        while (j == secondHalf.size() && i < firstHalf.size()) {
            sortedArray.add(firstHalf.get(i));
            i++;
        }

        return sortedArray;
    }

    /**
     * getNumbersArray
     *
     * @return The array to be sorted or already sorted
     */
    List<Integer> getNumbersArray() {
        return this.numbersArray;
    }

    /**
     * getThread
     *
     * @return Thread that executes Merge Sort of an array
     */
    Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running... ");
        this.numbersArray = mergeSort(this.numbersArray);
        System.out.println("Thread " + Thread.currentThread().getId()
                + " finished sorting: " + this.numbersArray);
    }
}


import java.util.List;

public class SumThread implements Runnable {

    private int sum;
    private Thread thread;
    private List<Integer> arrayToSum;

    /**
     * SumThread constructor. Creates a thread that automatically sums a list of Integers
     *
     * @param arrayToSum Array of numbers to be summed inside this thread
     */
    SumThread(List<Integer> arrayToSum) {
        this.sum = 0;
        this.arrayToSum = arrayToSum;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * getSum
     *
     * @return Sum of the array computed for this thread
     */
    int getSum() {
        return this.sum;
    }

    /**
     * getThread
     *
     * @return Thread that does the sum of an array
     */
    Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running... ");
        for (int i : this.arrayToSum) {
            this.sum += i;
        }
        System.out.println("Thread " + Thread.currentThread().getId()
                + " finished the sum: " + this.sum);
    }
}

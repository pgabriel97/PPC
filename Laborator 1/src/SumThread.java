import java.util.List;

public class SumThread implements Runnable {

    private int sum;

    /**
     * SumThread constructor. Creates a thread that automatically sums a list of Integers
     * @param arrayToSum Array of numbers to be summed inside this thread
     */
    SumThread(List<Integer> arrayToSum) {
        this.sum = 0;
        for (int i : arrayToSum) {
            this.sum += i;
        }
    }

    /**getSum
     * @return Sum of the array computed for this thread
     */
    int getSum() {
        return this.sum;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running... " +
                    "Sum of subarray is " + this.sum);
        } catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }
}

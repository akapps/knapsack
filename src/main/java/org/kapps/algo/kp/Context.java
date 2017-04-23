package org.kapps.algo.kp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Context environment for the knapsack problem
 *
 * @author Antoine Kapps
 */
public class Context {

    private final int[][] items;
    private final int capacity;

    Context(int[][] items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    /**
     * Access all candidate items.
     * <pre>int[] -> {weight, value}</pre>
     *
     * @return all candidate items
     */
    public int[][] getItems() {
        return items;
    }

    /**
     * @return the knapsack maximum acceptable weight
     */
    public int getCapacity() {
        return capacity;
    }

    public Examiner createExaminer() {
        return new Examiner();
    }


    /**
     * An examiner, able to evaluate a possible solution, store the different values tested, and feed back all optima.
     */
    public class Examiner {

        private AtomicInteger testNumber = new AtomicInteger();

        private List<Result> optima = new ArrayList<>();
        private AtomicInteger optimumValue = new AtomicInteger();

        private Examiner() {
            // Not visible
        }

        /**
         * Evaluate the candidate solution.
         * <p>
         *     The solution is provided as a {@code boolean[]} where each entry represent an item from the list.
         *     If {@code true} the item is to be put in the sack, when {@code false} it is left away.
         * </p>
         * <p>
         *     Note that the solution is not registered at this time.
         * </p>
         *
         * @param arr solution to evaluate
         * @return total weight and total value of the items taken by the solution
         *
         * @see #compare(boolean[], int, int)
         */
        public int[] evaluate(boolean[] arr) {
            int weight = 0;
            int value = 0;

            for (int i=0; i<arr.length; i++) {
                if (arr[i]) {
                    weight += getItems()[i][0];
                    value += getItems()[i][1];
                }
            }

            return new int[]{ weight, value };
        }

        /**
         * Compare the solution with the current best ones, and register it if an optimum.
         *
         * @param arr candidate solution
         * @param weight total weight evaluated
         * @param value total value evaluated
         */
        public void compare(boolean[] arr, int weight, int value) {
            final int testNum = testNumber.incrementAndGet();

            if (weight <= getCapacity()) {
                final int max = optimumValue.get();
                // if better then this is our new optimum
                if (value > max) {
                    optimumValue.compareAndSet(max, value);

                    optima.clear();
                    optima.add(new Result(testNum, arr, weight, value));
                }

                // If same than optimum, we register this solution too
                else if (value == max) {
                    optima.add(new Result(testNum, arr, weight, value));
                }
            }
        }

        /**
         * @return all optima registered
         */
        public Collection<Result> getOptima() {
            return optima;
        }

    }

}

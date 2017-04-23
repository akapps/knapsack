package org.kapps.algo.kp;

/**
 * @author Antoine Kapps
 */
public class Result {

    private final int testNumber;

    private final boolean[] vector;

    private final int weight;

    private final int value;

    public Result(int testNumber, boolean[] vector, int weight, int value) {
        this.testNumber = testNumber;
        this.weight = weight;
        this.value = value;
        this.vector = vector.clone();
    }

    public int getTestNumber() {
        return testNumber;
    }

    public boolean[] getVector() {
        return vector;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}

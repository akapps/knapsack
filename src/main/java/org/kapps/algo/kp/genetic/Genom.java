package org.kapps.algo.kp.genetic;

import java.util.Arrays;

/**
 * @author Antoine Kapps
 */
class Genom {

    final boolean[] vector;
    final int weight, value;

    Genom(boolean[] vector, int weight, int value) {
        this.vector = vector;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genom genom = (Genom) o;

        if (weight != genom.weight) return false;
        if (value != genom.value) return false;
        return Arrays.equals(vector, genom.vector);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(vector);
        result = 31 * result + weight;
        result = 31 * result + value;
        return result;
    }
}

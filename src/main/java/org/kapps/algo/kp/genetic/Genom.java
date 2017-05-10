package org.kapps.algo.kp.genetic;

import java.util.Arrays;

/**
 * Simple wrapper around a solution candidate.
 *
 * @author Antoine Kapps
 */
class Genom {

    final boolean[] vector;

    Genom(boolean[] vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genom genom = (Genom) o;
        return Arrays.equals(vector, genom.vector);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }
}

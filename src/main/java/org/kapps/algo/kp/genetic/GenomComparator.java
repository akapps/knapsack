package org.kapps.algo.kp.genetic;

import java.util.Comparator;

/**
 * @author Antoine Kapps
 */
class GenomComparator implements Comparator<Genom> {

    private final int maximumWeight;

    GenomComparator(int maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    @Override
    public int compare(Genom o1, Genom o2) {
        final int compare = Boolean.compare(o1.weight <= maximumWeight, o2.weight <= maximumWeight);

        return compare == 0 ? Integer.compare(o1.value, o2.value) : compare;
    }
}

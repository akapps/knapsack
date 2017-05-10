package org.kapps.algo.kp.genetic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * A population supplier that increment one by one over the possible combination, using the lesser possible
 * used items.
 *
 * @author Antoine Kapps
 */
class IncrementalPopulationGenerator implements Supplier<List<Genom>> {

    private final int vectorSize;
    private final int targetSize;

    public IncrementalPopulationGenerator(int vectorSize, int targetSize) {
        this.vectorSize = vectorSize;
        this.targetSize = targetSize;
    }

    @Override
    public List<Genom> get() {
        final List<Genom> result = new ArrayList<>(targetSize);

        ArrayFiller filler = new ArrayFiller(new boolean[vectorSize], 0);
        while (result.size() < targetSize) {

            if (filler.hasNext()) {
                result.add(filler.next());
            }
            else {
                filler = nextFiller(filler);
            }
        }

        return result;
    }

    private ArrayFiller nextFiller(ArrayFiller current) {
        if (current.indexMin < current.prototype.length - 1) {
            boolean[] prototype = current.prototype.clone();
            prototype[current.indexMin] = true;
            return new ArrayFiller(prototype, current.indexMin + 1);
        }

        throw new IndexOutOfBoundsException("No more ArrayFiller can be created - all possible generated combinations used");
    }


    // Inner component to iterate over the combinations
    private static class ArrayFiller implements Iterator<Genom> {

        final boolean[] prototype;
        final int indexMin;
        int current;

        ArrayFiller(boolean[] prototype, int indexMin) {
            this.prototype = prototype;
            this.indexMin = indexMin;
            this.current = indexMin;
        }

        @Override
        public boolean hasNext() {
            return current < prototype.length;
        }

        @Override
        public Genom next() {
            boolean[] vector = prototype.clone();
            vector[current++] = true;
            return new Genom(vector);
        }
    }
}

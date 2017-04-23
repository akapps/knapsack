package org.kapps.algo.kp.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.function.Function;

import org.kapps.algo.kp.Context;

/**
 * @author Antoine Kapps
 */
class BestElementsReproduction implements Function<List<Genom>, List<Genom>> {

    private static final Random RNG = new Random();

    // FIXME maxWeight hard-coded
    private final Comparator<Genom> priorityComparator = Collections.reverseOrder(new GenomComparator(29));

    private final Context.Examiner examiner;

    public BestElementsReproduction(Context.Examiner examiner) {
        this.examiner = examiner;
    }

    @Override
    public List<Genom> apply(List<Genom> parents) {
        List<Genom> sortedParents = new ArrayList<>(parents);
        sortedParents.sort(priorityComparator);

        List<Genom> children = new ArrayList<>(parents.size());
        while (children.size() < parents.size()) {
            reproduce(sortedParents, children);
        }
        children.sort(priorityComparator);

        // Best genoms are at the beginning of the list
        return bestCombination(new LinkedList<>(sortedParents), new LinkedList<>(children));
    }

    private static List<Genom> bestCombination(Queue<Genom> one, Queue<Genom> two) {
        final int length = one.size();
        List<Genom> combination = new ArrayList<>(length);

        // FIXME maxWeight hard-coded
        Comparator<Genom> comparator = new GenomComparator(29);
        while (combination.size() < length) {
            Genom g1 = one.peek();
            Genom g2 = two.peek();

            if (comparator.compare(g1, g2) < 0)     combination.add(two.poll());
            else                                    combination.add(one.poll());
        }

        return combination;
    }

    private void reproduce(List<Genom> parents, List<Genom> children) {
        Genom papa = choose(parents);
        List<Genom> remaining = new ArrayList<>(parents);
        remaining.remove(papa);
        Genom mama = choose(remaining);

        // 2. Reproduction
        final int length = mama.vector.length;

        int indX = RNG.nextInt(length);  // Is it systematic ?
        boolean[] child1 = new boolean[length];
        boolean[] child2 = new boolean[length];

        System.arraycopy(mama.vector, 0, child1, 0, indX);
        System.arraycopy(papa.vector, 0, child2, 0, indX);
        System.arraycopy(mama.vector, indX, child2, indX, length-indX);
        System.arraycopy(papa.vector, indX, child1, indX, length-indX);

        // 3. Put children into nextGeneration[i] and nextGeneration[i+1]
        // TODO Maybe we should ensure that we have no doubles...
        int[] w_v = examiner.evaluate(child1);
        examiner.compare(child1, w_v[0], w_v[1]);
        children.add(new Genom(child1, w_v[0], w_v[1]));

        w_v = examiner.evaluate(child2);
        examiner.compare(child2, w_v[0], w_v[1]);
        children.add(new Genom(child2, w_v[0], w_v[1]));
    }

    private static Genom choose(List<Genom> source) {
        for (Genom genom : source) {
            // if (isLucky(20))     Statistically the 6th item has very low chances to get lucky here...
            if (isLucky(100 / source.size()))
                // The higher the value, the higher the risk to always have the same parents
                return genom;
        }

        return source.get(source.size() - 1); // last one (that gives it quite high chances actually...)
    }

    private static boolean isLucky(int percentage) {
        return RNG.nextInt(100) < percentage;
    }
}

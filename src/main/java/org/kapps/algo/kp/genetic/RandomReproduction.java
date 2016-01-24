package org.kapps.algo.kp.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.kapps.algo.kp.Context;

/**
 * @author Antoine Kapps
 */
class RandomReproduction implements Function<List<Genom>, List<Genom>> {

    private static final Random RNG = new Random();

    private final Context.Examiner examiner;

    RandomReproduction(Context.Examiner examiner) {
        this.examiner = examiner;
    }

    @Override
    public List<Genom> apply(List<Genom> parents) {
        List<Genom> children = new ArrayList<>(parents.size());
        while (children.size() < parents.size()) {
            reproduce(parents, children);
        }

        return children;
    }

    private void reproduce(List<Genom> parents, List<Genom> children) {
        // 1. Pick a couple
        // TODO Each individual should have its own probability of being picked
        int ind1 = RNG.nextInt(parents.size());
        int ind2 = RNG.nextInt(parents.size() - 1);
        if (ind2 >= ind1)
            ind2++;

        Genom mama = parents.get(ind1);
        Genom daddy = parents.get(ind2);


        // 2. Reproduction
        final int length = mama.vector.length;

        int indX = RNG.nextInt(length);  // Is it systematic ?
        boolean[] child1 = new boolean[length];
        boolean[] child2 = new boolean[length];

        System.arraycopy(mama.vector, 0, child1, 0, indX);
        System.arraycopy(daddy.vector, 0, child2, 0, indX);
        System.arraycopy(mama.vector, indX, child2, indX, length-indX);
        System.arraycopy(daddy.vector, indX, child1, indX, length-indX);

        /*System.out.println("   (Cut happened at pos " + indX + ")");
        System.out.println("   > Child 1 is "+Context.toString(child1));
        System.out.println("   > Child 2 is "+Context.toString(child2));*/



        // 3. Put children into nextGeneration[i] and nextGeneration[i+1]
        // TODO Maybe we should ensure that we have no doubles...
        int[] w_v = examiner.evaluate(child1);
        examiner.compare(child1, w_v[0], w_v[1]);
        children.add(new Genom(child1, w_v[0], w_v[1]));

        w_v = examiner.evaluate(child2);
        examiner.compare(child2, w_v[0], w_v[1]);
        children.add(new Genom(child2, w_v[0], w_v[1]));
    }
}

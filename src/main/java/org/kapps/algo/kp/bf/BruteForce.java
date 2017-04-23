package org.kapps.algo.kp.bf;

import java.util.Collection;
import java.util.function.Consumer;

import org.kapps.algo.kp.Algorithm;
import org.kapps.algo.kp.Context;
import org.kapps.algo.kp.Result;

/**
 * Simple algorithm to resolve the problem using "brute force", ie by evaluating all possible combination.
 *
 * @author Antoine Kapps
 */
public class BruteForce implements Algorithm {

    public Collection<Result> evaluate(Context problem) {
        final int nbItems = problem.getItems().length;

        boolean[] candidate = new boolean[nbItems];
        final Context.Examiner examiner = problem.createExaminer();

        explore(candidate, 0, nbItems, (arr) -> {
            final int[] w_v = examiner.evaluate(arr);
            examiner.compare(arr, w_v[0], w_v[1]);
        });

        return examiner.getOptima();
    }

    /**
     * Recursive method that proposes for each index both "true" then "false" values.
     *
     * @param callback called whenever a full solution is ready (ie all indexes are set)
     */
    private static void explore(boolean[] tab, int depth, int n, Consumer<boolean[]> callback) {
        if (depth == n) {
            callback.accept(tab);
        }

        else {
            tab[depth] = false;
            explore(tab, depth+1, n, callback);

            tab[depth] = true;
            explore(tab, depth+1, n, callback);
        }
    }

}

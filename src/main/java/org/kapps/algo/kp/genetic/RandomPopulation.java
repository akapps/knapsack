package org.kapps.algo.kp.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.kapps.algo.kp.Context;
import org.kapps.algo.kp.PrintUtils;

/**
 * @author Antoine Kapps
 */
class RandomPopulation implements Supplier<List<Genom>> {

    private static final Random RNG = new Random();

    private final int populationNumber;
    private final int genomLength;

    private final Context.Examiner examiner;

    public RandomPopulation(int populationNumber, int genomLength, Context.Examiner examiner) {
        this.populationNumber = populationNumber;
        this.genomLength = genomLength;
        this.examiner = examiner;
    }

    @Override
    public List<Genom> get() {
        List<Genom> population = new ArrayList<>(populationNumber);

        for (int i = 0; i< populationNumber; i++) {
            boolean[] individual = generate(genomLength);
            int[] w_v = examiner.evaluate(individual);
            examiner.compare(individual, w_v[0], w_v[1]);

            population.add(new Genom(individual, w_v[0], w_v[1]));
            System.out.println("Generated "+ PrintUtils.toString(individual)
                    + "  weight = " + w_v[0] + ", value = " + w_v[1]);
        }

        return population;
    }

    private static boolean[] generate(int size) {
        boolean[] result = new boolean[size];

        for (int i=0; i<size; i++) {
            result[i] = RNG.nextBoolean();
        }

        return result;
    }
}

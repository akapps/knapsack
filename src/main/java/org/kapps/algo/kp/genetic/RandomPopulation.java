package org.kapps.algo.kp.genetic;

import org.kapps.algo.kp.PrintUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author Antoine Kapps
 */
class RandomPopulation implements Supplier<List<Genom>> {

    private static final Random RNG = new Random();

    private final int populationNumber;
    private final int genomLength;

    public RandomPopulation(int populationNumber, int genomLength) {
        this.populationNumber = populationNumber;
        this.genomLength = genomLength;
    }

    @Override
    public List<Genom> get() {
        List<Genom> population = new ArrayList<>(populationNumber);

        for (int i = 0; i< populationNumber; i++) {
            boolean[] individual = generate(genomLength);

            population.add(new Genom(individual));
            System.out.println("Generated "+ PrintUtils.toString(individual));
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

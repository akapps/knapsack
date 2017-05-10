package org.kapps.algo.kp.genetic;

import org.kapps.algo.kp.Context;

import java.util.List;

/**
 * @author Antoine Kapps
 */
class PopulationEvaluation {

    private final Genom[] population;
    private final Context.Examiner examiner;

    // evaluation
    private int nbSuccess = 0;
    private int moyenne;
    private int minValue = Integer.MAX_VALUE;
    private int maxValue = 0;

    PopulationEvaluation(Genom[] population, int capacity, Context.Examiner examiner) {
        this.population = population;
        this.examiner = examiner;

        evaluate(capacity);
    }

    PopulationEvaluation(List<Genom> population, int capacity, Context.Examiner examiner) {
        this(population.toArray(new Genom[population.size()]), capacity, examiner);
    }

    private void evaluate(int capacity) {
        int sumValues = 0;

        for (Genom genom : population) {
            final int[] w_v = examiner.evaluate(genom.vector);

            int weight = w_v[0];
            int value = w_v[1];
            examiner.compare(genom.vector, weight, value);

            if (weight <= capacity) {
                nbSuccess++;
                sumValues += value;

                if (value > maxValue) maxValue = value;
                if (value < minValue) minValue = value;
            }
        }

        if (nbSuccess > 0) {
            moyenne = sumValues / nbSuccess;
        } else {
            minValue = 0;
            moyenne = 0;
        }
    }

    public void printSummary() {
        System.out.println("  " + nbSuccess + " success, min=" + minValue + ", max=" + maxValue
                + ", moyenne=" + moyenne);
    }
}

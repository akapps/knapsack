package org.kapps.algo.kp.genetic;

import java.util.List;

/**
 * @author Antoine Kapps
 */
class PopulationEvaluation {

    private final Genom[] population;

    // evaluation
    private int nbSuccess = 0;
    private int moyenne;
    private int minValue = Integer.MAX_VALUE;
    private int maxValue = 0;

    PopulationEvaluation(Genom[] population, int capacity) {
        this.population = population;

        evaluate(capacity);
    }

    PopulationEvaluation(List<Genom> population, int capacity) {
        this(population.toArray(new Genom[population.size()]), capacity);
    }

    private void evaluate(int capacity) {
        int sumValues = 0;

        for (Genom genom : population) {
            int weight = genom.weight;
            int value = genom.value;

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

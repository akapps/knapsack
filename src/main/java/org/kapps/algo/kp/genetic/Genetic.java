package org.kapps.algo.kp.genetic;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.kapps.algo.kp.Algorithm;
import org.kapps.algo.kp.Context;
import org.kapps.algo.kp.Result;

/**
 * @author Antoine Kapps
 */
public class Genetic implements Algorithm {

    private static final int POPULATION_NUMBER = 150;
    private static final int GENERATIONS_NUMBER = 300;

    private static Supplier<List<Genom>> firstGenerationSupplier;
    private static Function<List<Genom>, List<Genom>> reproducer;

    @Override
    public Collection<Result> evaluate(Context problem) {

        final int n = problem.getItems().length;
        final Context.Examiner examiner = problem.createExaminer();

        firstGenerationSupplier = new RandomPopulation(POPULATION_NUMBER, n);
        reproducer = new RandomReproduction();

        int turnsLeft = GENERATIONS_NUMBER;

        // ------------------
        // First generation :
        // ------------------
        List<Genom> population = firstGenerationSupplier.get();
        new PopulationEvaluation(population, problem.getCapacity(), examiner)
                .printSummary();


        while(turnsLeft-- > 0) {
            // ------------------
            // Next generation :
            // ------------------
            System.out.println("****  GENERATION " + (GENERATIONS_NUMBER - turnsLeft) + "  ****");
            List<Genom> nextGeneration = reproducer.apply(population);

            // TODO Optimiser en fonction des succès dans les générations
            //   on pourrait les mixer cad garder les meilleurs de chaque
            //   on pourrait donner une espérance de vie max de qq generations en particulier si la gen est complète
            //   il faudrait aussi trouver un moyen pour privilégier les meilleurs éléments
            //          (mais sans "trop" d'elitisme)

            // No bias, no optimization : we turn to next generation
            population = nextGeneration;
            new PopulationEvaluation(population, problem.getCapacity(), examiner)
                    .printSummary();
        }

        return examiner.getOptima();
    }

}

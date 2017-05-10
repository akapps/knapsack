package org.kapps.algo.kp.genetic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class IncrementalPopulationGeneratorTest {

    private static Genom aGenomWith(int... vectorUnit) {
        boolean[] vector = new boolean[vectorUnit.length];
        for (int i = 0; i < vectorUnit.length; i++) {
            vector[i] = vectorUnit[i] > 0;
        }

        return new Genom(vector);
    }

    /**
     * Verifies all combinations of the first cycle
     */
    @Test
    public void get_fullCycle() {
        final int vectorSize = 5;
        IncrementalPopulationGenerator generator = new IncrementalPopulationGenerator(vectorSize, vectorSize);

        // when
        final List<Genom> list = generator.get();
        // then
        assertThat(list).containsExactly(
                aGenomWith(1, 0, 0, 0, 0),
                aGenomWith(0, 1, 0, 0, 0),
                aGenomWith(0, 0, 1, 0, 0),
                aGenomWith(0, 0, 0, 1, 0),
                aGenomWith(0, 0, 0, 0, 1)
        );
    }

    @Test
    public void get_startingNextCycle() {
        final int vectorSize = 5;
        IncrementalPopulationGenerator generator = new IncrementalPopulationGenerator(vectorSize, vectorSize+1);

        // when
        final List<Genom> list = generator.get();
        // then
        assertThat(list).endsWith(
                aGenomWith(0, 0, 0, 0, 1),  // last of cycle 1
                aGenomWith(1, 1, 0, 0, 0)   // beginning cycle 2
        );
    }

    @Test
    public void get_allPossibleValues() {
        final int vectorSize = 3;
        final int maxSize = 6;
        IncrementalPopulationGenerator generator = new IncrementalPopulationGenerator(vectorSize, maxSize);

        // when
        final List<Genom> list = generator.get();
        // then
        assertThat(list).containsExactly(
                aGenomWith(1, 0, 0),
                aGenomWith(0, 1, 0),
                aGenomWith(0, 0, 1),
                aGenomWith(1, 1, 0),
                aGenomWith(1, 0, 1),
                aGenomWith(1, 1, 1)
        );
        // [0, 0, 0] and [0, 1, 1] cannot be generated at this stage
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_exceedingAllGeneratedValues() {
        final int vectorSize = 3;
        final int maxSize = 6;
        IncrementalPopulationGenerator generator = new IncrementalPopulationGenerator(vectorSize, maxSize + 1);

        // when - then exception
        generator.get();
    }

}
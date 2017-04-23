package org.kapps.algo.kp.genetic;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Antoine Kapps
 */
public class GenomComparatorTest {

    private static final boolean[] VECTOR = new boolean[0];

    private static final int MAXIMAL_WEIGHT = 30;

    private Genom miniOK = new Genom(VECTOR, 25, 20);
    private Genom miniNotOK = new Genom(VECTOR, 32, 20);

    private Genom maxiOK = new Genom(VECTOR, 25, 30);
    private Genom maxiNotOK = new Genom(VECTOR, 32, 30);

    private GenomComparator comparator = new GenomComparator(MAXIMAL_WEIGHT);

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void compare() throws Exception {
        softly.assertThat(comparator.compare(miniOK, maxiOK)).as("Standard OK case").isLessThan(0);
        softly.assertThat(comparator.compare(miniNotOK, maxiNotOK)).as("Standard Not OK case").isLessThan(0);

        softly.assertThat(comparator.compare(miniOK, maxiNotOK)).as("OK first, even if lower").isGreaterThan(0);
        softly.assertThat(comparator.compare(miniNotOK, maxiOK)).as("Not OK first -> like standard").isLessThan(0);
    }

}
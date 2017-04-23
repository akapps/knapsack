package org.kapps.algo.kp;

import java.util.Collection;

/**
 * Strategy interface that defines an algorithm capable to resolve our "knapsack problem".
 *
 * @author Antoine Kapps
 */
public interface Algorithm {

    Collection<Result> evaluate(Context problem);

}

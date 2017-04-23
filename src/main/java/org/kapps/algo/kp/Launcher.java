package org.kapps.algo.kp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.kapps.algo.kp.bf.BruteForce;

/**
 * @author Antoine Kapps
 */
public class Launcher {

    private static final String FILENAME = "context.txt";

    private static Context createContext() {
        try {
            final Path filePath = Paths.get(Context.class.getClassLoader().getResource(FILENAME).toURI());

            final List<String> lines = Files.readAllLines(filePath).stream()
                    .filter(s -> !s.trim().isEmpty() && !s.startsWith("#"))
                    .collect(Collectors.toList());

            String str = lines.remove(0).split("=")[1].trim();
            int capacity = Integer.parseInt(str);

            final int[][] items = lines.stream()
                    .map(l -> {
                        String[] split = l.split(" ");
                        return new int[]{Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim())};
                    }).collect(Collectors.toList())
                    .toArray(new int[lines.size()][]);

            return new Context(items, capacity);
        }
        catch (Exception e) {
            throw new IllegalStateException("Impossible to load context from file", e);
        }
    }

    public static void main(String[] args) {
        final Context context = createContext();
        final int[][] items = context.getItems();
        System.out.println("Problem with n = " + items.length + " and capacity = " + context.getCapacity());
        System.out.println("  -> number of possible combinations = 2^" + items.length + " = " + (int) Math.pow(2, items.length));
        System.out.println();

        final Algorithm algorithm = new BruteForce();
        System.out.println("Algorithm used to resolve : " + algorithm.getClass().getSimpleName());

        final long start = System.currentTimeMillis();
        final Collection<Result> results = algorithm.evaluate(context);
        final long end = System.currentTimeMillis();

        printResult(results);
        System.out.println("Time for examination : " + prettyTime(end - start));
    }


    private static String prettyTime(long value) {
        long seconds = value / 1000;
        long ms = value % 1000;
        String result = ms + "ms";

        if (seconds > 0) {
            result = (seconds % 60) + "sec " + result;
            long min = seconds / 60;

            if (min > 0) {
                result = (min % 60) + "min " + result;
                long hours = min / 60;

                if (hours > 0)
                    result = hours + "h " + result;
            }
        }

        return result;
    }


    private static void printResult(Collection<Result> optima) {
        System.out.println();
        if (optima.size() == 1) {
            System.out.println("--> 1 unique optimum found.");
            Result r = optima.iterator().next();

            System.out.println("    Optimum encountered at test n°" + r.getTestNumber()
                    + " - " + toString(r.getVector()) + "  with value = " + r.getValue() + " and weight = " + r.getWeight());
        }

        else {
            System.out.println("--> "+optima.size() + " different optima found.");

            for (Result r : optima) {
                System.out.println("    * test n°" + r.getTestNumber()
                        + " - " + toString(r.getVector()) + "  with value = " + r.getValue() + " and weight = " + r.getWeight());
            }
        }
    }

    /** That's stupid but it helps me translate from boolean[] to int[] ... */
    private static String toString(boolean[] tab) {
        StringBuilder sb = new StringBuilder().append('[');
        for (boolean b : tab)
            sb.append(b ? '1' : '0').append(',');

        sb.setLength(sb.length() -1);
        return sb.append(']').toString();
    }

}

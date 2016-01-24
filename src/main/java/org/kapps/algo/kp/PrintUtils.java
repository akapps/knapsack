package org.kapps.algo.kp;

/**
 * @author Antoine Kapps
 */
public class PrintUtils {

    private PrintUtils() {
        throw new AssertionError("Not instantiable");
    }


    /** That's stupid but it helps me translate from boolean[] to int[] ... */
    public static String toString(boolean[] tab) {
        StringBuilder sb = new StringBuilder().append('[');
        for (boolean b : tab)
            sb.append(b ? '1' : '0').append(',');

        sb.setLength(sb.length() -1);
        return sb.append(']').toString();
    }

    public static String prettyTime(long value) {
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
}

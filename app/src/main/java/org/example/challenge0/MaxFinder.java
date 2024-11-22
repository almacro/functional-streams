package org.example.challenge0;

import java.util.Arrays;

/**
 *
 * @author almacro
 */
public class MaxFinder {
    static int findLargest(int[] numbers) {
        return Arrays.stream(numbers).max().getAsInt();
    }
}

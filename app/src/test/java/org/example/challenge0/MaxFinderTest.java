
package org.example.challenge0;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author almacro
 */
public class MaxFinderTest {
    @Test
    public void testMaxFinder() {
        int[] numbers = { 7, 17, 13, 19, 5 };
        int result = MaxFinder.findLargest(numbers);
        assertEquals(result, 19);
        System.out.println(result);
    }
}

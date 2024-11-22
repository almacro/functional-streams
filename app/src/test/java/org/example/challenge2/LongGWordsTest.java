
package org.example.challenge2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author almacro
 */
public class LongGWordsTest {
    
    @Test
    public void testLongGWords() {
        List<String> words = new ArrayList<>(Arrays.asList("Argentina", "Ghana", "Bangladesh",
                "New Zealand", "Germany", "Peru", "Zimbabwe", "Guyana"));
        List<String> result = LongGWords.applyFilters(words);
        System.out.println(result);
    }
}

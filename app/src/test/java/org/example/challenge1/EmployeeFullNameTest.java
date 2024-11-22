
package org.example.challenge1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author almacro
 */
public class EmployeeFullNameTest {
    @Test
    public void testEmployeeFullName() {
        Employee employee = new Employee("Jerome", "Donaldson");
        String result = EmployeeFullName.getFullName(employee);
        System.out.println(result);
    }
}

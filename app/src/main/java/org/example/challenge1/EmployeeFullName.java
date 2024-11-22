
package org.example.challenge1;

import java.util.function.Function;

/**
 *
 * @author almacro
 */
public class EmployeeFullName {
    static String getFullName(Employee employee) {
        Function<Employee, String> fullName = (e) -> 
            e.getFirstName() +  " " + e.getLastName();
        return fullName.apply(employee);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.challenge1;

/**
 *
 * @author almacro
 */
public class Employee {
    private String firstName;
    private String lastName;
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    String getFirstName() { return firstName; }
    String getLastName() { return lastName; }
}

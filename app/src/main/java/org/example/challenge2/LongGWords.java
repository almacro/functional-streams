/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.challenge2;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author almacro
 */
public class LongGWords {

    static List<String> applyFilters(List<String> words) {
        return words.stream()
                .filter(word -> word.length() > 5)
                .filter(word -> word.startsWith("G"))
                .collect(Collectors.toList());
   }
    
}

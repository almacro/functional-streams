
package org.example.collisionDetection2D;

import java.util.ArrayList;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.example.Time;

import static org.example.collisionDetection2D.BasicDetection.forEachCollision1;
import static org.example.collisionDetection2D.BasicDetection.forEachCollision2;
import static org.example.collisionDetection2D.BasicDetection.forEachCollision3;

/**
 * Collision counting tests
 * 
 * @author almacro
 */
public class CountingTest {
    
    public static final int XRES =1920, YRES = 1080, MAXX = 200, MAXY = 200;
    
    // Generate random boxes
    public static ArrayList<Box> makeRandom(int n) {
        ArrayList<Box> result = new ArrayList<>();
        for(int i=0; i<n; i++)
            result.add(Box.newRandom(XRES, YRES, MAXX, MAXY));
        return result;
    }
    
    public static void main(String[] args) {
        final int TEST_SIZE = 5_000;
        ArrayList<Box> world = makeRandom(TEST_SIZE);
        
        System.out.println("\nCounting the number of collisions:");
        
        final LongAdder atomicCounter = new LongAdder();
        Consumer<Collision> action = c -> atomicCounter.increment();
        
        System.out.println("\nStandard imperative:");
        // Warm up
        forEachCollision1(world, action);
        atomicCounter.reset();
        // Timed run
        Time.timeIt(() -> forEachCollision1(world, action));
        System.out.println("# of collisions: " + atomicCounter);
        
        System.out.println("\nStandard imperative (no duplicates):");
        // Warm up
        forEachCollision2(world, action);
        atomicCounter.reset();
        // Timed run
        Time.timeIt(() -> forEachCollision2(world, action));
        System.out.println("# of collisions: " + atomicCounter);
        
        System.out.println("\nParallel stream (ForEachCollisionCollector):");
        // Warm up
        forEachCollision3(world, action);
        atomicCounter.reset();        
        // Timed run
        Time.timeIt(() -> forEachCollision3(world, action));
        System.out.println("# of collisions: " + atomicCounter);    }
}


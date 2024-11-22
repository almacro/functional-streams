
package org.example.collisionDetectionTwoPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.example.collisionDetection2D.Box;
import org.example.collisionDetection2D.Collision;

/**
 * Collects collisions in a list
 * 
 * @author almacro
 */
public class CollisionListCollector {
    // stores previously seen boxes
    private ArrayList<Box> cache = new ArrayList<>();
    
    private ArrayList<Collision> collisions = new ArrayList<>();
    private Consumer<Collision> handler;

    public CollisionListCollector() {
        handler = c -> collisions.add(c);
    }
    
    public void update(Box b) {
        // for every previously seen box 'a'
        for(Box a: cache)
            // check if box 'a' overlaps with the new box 'b'
            if(Box.areOverlapping(a, b))
                handler.accept(new Collision(a,b));
        // store the new box
        cache.add(b);
    }
    
    public CollisionListCollector merge(CollisionListCollector other) {
         // for every pair of boxes in the two caches
        for(Box a: cache) 
            for(Box b: other.cache) 
                if(Box.areOverlapping(a, b))
                    handler.accept(new Collision(a, b));
        // merge the two caches
        cache.addAll(other.cache);
        return this;
    }
    
    public List<Collision> finish() {
        return collisions;
    }
    
    public static Collector<Box, ?, List<Collision>> make() {
        //(left,right) -> { left.addAll(right); return left; },
        //Collector.Characteristics.UNORDERED
        return Collector.of(
                CollisionListCollector::new,
                CollisionListCollector::update,
                CollisionListCollector::merge,
                CollisionListCollector::finish,
                Collector.Characteristics.UNORDERED);
    }
}

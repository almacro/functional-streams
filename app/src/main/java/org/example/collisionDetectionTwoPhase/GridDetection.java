package org.example.collisionDetectionTwoPhase;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.collisionDetection2D.Box;
import org.example.collisionDetection2D.Collision;
import org.example.collisionDetection2D.CountingTest;

/**
 * Two phase grid-based algorithms
 * 
 * @author almacro
 */
public class GridDetection {
    public static final GridSpec gridSpec = new GridSpec();
    static {
        gridSpec.rows = 4;
        gridSpec.cols = 6;
        gridSpec.rowHeight = CountingTest.YRES / gridSpec.rows;
        gridSpec.colWidth = CountingTest.XRES / gridSpec.cols;
    }
    
    public static List<List<Box>> broadPhase(Collection<Box> c, GridSpec gridSpec) {
        return c.parallelStream().collect(GridCollector.of(gridSpec));
    }
    
    /**
     * Builds the set of all collisions.
     * Based on broad phase / narrow phase.
     * Duplicate removal is performed by the final 'toSet' collector.
     * 
     * @param c
     * @return   
     */
    public static Set<Collision> allCollisions(Collection<Box> c) {
        // Broad phase
        List<List<Box>> gridCells = broadPhase(c, GridDetection.gridSpec);
        // Narrow phase
        return gridCells.stream()       // stream of List<Box>
                        .flatMap(
                                (List<Box> l) -> l.stream()
                                                  .collect(CollisionListCollector.make())  // List<Collision>
                                                  .stream()                                 // stream of collisions
                                )   // stream of collisions
                        .collect(Collectors.toSet());
    }
    
    public static Set<Collision> allCollisionsAlt(Collection<Box> c) {
        // Broad phase
        List<List<Box>> gridCells = GridDetection.broadPhase(c, GridDetection.gridSpec);
        // Narrow phase
        return gridCells.parallelStream()       // parallel!
                        .flatMap(
                                (List<Box> l) -> l.parallelStream()
                                                  .collect(CollisionListCollector.make())
                                                  .stream()
                                )
                        .collect(Collectors.toSet());
    }
    
     /**
     * Builds the stream of all collisions from a given set of boxes.
     * Based on broad phase / narrow phase.
     * Duplicate removal is performed by the final 'distinct' operation.
     * 
     * @param c
     * @return   
     */
    public static Stream<Collision> collisionStream(Collection<Box> c) {
        return broadPhase(c, gridSpec).stream()
                .flatMap(
                    l -> l.stream()
                          .collect(CollisionListCollector.make())
                          .stream()
                )
                .unordered()
                .distinct();
    }
}

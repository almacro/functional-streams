
package org.example.collisionDetection2D;

/**
 * A Collision is a pair of boxes.
 * The pair represents a collision between two boxes.
 * 
 * Order is not relevant, as witnessed by hashCode and equals.
 * 
 * @author almacro
 */
public class Collision {
    public final Box a, b;
    
    public Collision(Box a, Box b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Collision)) {
            return false;
        }
        Collision c = (Collision)o;
        return (a.equals(c.a) && b.equals(c.b)) || (a.equals(c.b) && b.equals(c.a));
    }
}

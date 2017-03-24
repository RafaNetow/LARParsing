/**
 * This class is used to temporarily store data pairs, having regard to the
 * high presence of data in pairs within the project.
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

public class Pair<X, Y>
{

    public final X x;
    public final Y y;

    /**
     * Constructor for the class
     * @param x
     * @param y
     */
    public Pair(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }
}
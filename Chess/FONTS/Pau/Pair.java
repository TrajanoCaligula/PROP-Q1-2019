/**
 * @author Pau Charques Rius
 */
package Pau;

/**
 * Auxiliar class we use to make things easier, java doesnt have any mutable pair struct.
 * @param <U> First element (Key)
 * @param <V> Second element (Value)
 */
// Pair class
public class Pair<U, V>
{
    public final U first;	// first field of a Pair
    public final V second; 	// second field of a Pair

    /**
     * Constructor
     * @param first First element of the pair
     * @param second Second element of the pair
     */
    // Constructs a new Pair with specified values
    private Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    // Checks specified object is "equal to" current object or not
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        // call equals() method of the underlying objects
        if (!first.equals(pair.first))
            return false;
        return second.equals(pair.second);
    }

    @Override
    // Computes hash code for an object to support hash tables
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return 31 * first.hashCode() + second.hashCode();
    }

    /**
     * Getter
     * @return The first element of the pair
     */
    public U getKey() {
        return first;
    }

    /**
     * Getter
     * @return Returns the first element of the pair
     */
    public V getValue() {
        return second;
    }

    @Override
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    // Factory method for creating a Typed Pair instance
    public static <U, V> Pair <U, V> of(U a, V b)
    {
        // calls private constructor
        return new Pair<>(a, b);
    }
}


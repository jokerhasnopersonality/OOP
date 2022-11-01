package io.github.jokerhasnopersonality;

/**
 * Class for representing vertex in Graph class.
 */
public class Vertex<V> {
    private V value;

    /**
     * Vertex constructor. The value of a vertex cannot be null.
     */
    public Vertex(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }

    /**
     * Sets new value for a vertex.
     */
    public void setValue(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }

    public V getValue() {
        return value;
    }

}

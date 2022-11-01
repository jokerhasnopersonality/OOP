package io.github.jokerhasnopersonality;

/**
 * Class representing edges for Graph class.
 *
 * @param <V> type of edge vertices
 * @param <E> type of edge value
 */
public class Edge<V, E extends Number> {
    private final Vertex<V> start;
    private final Vertex<V> end;
    private E weight;

    /**
     * Edge constructor. An edge cannot be constructed from or to a null vertex.
     */
    public Edge(Vertex<V> start, Vertex<V> end, E weight) throws NullPointerException {
        if (start == null || end == null) {
            throw new NullPointerException("Edge vertices must be specified.");
        }
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void setWeight(E weight) {
        this.weight = weight;
    }

    public E getWeight() {
        return weight;
    }

    public Vertex<V> getStart() {
        return start;
    }

    public Vertex<V> getEnd() {
        return end;
    }
}

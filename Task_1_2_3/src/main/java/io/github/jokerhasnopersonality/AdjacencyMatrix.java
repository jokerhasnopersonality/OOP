package io.github.jokerhasnopersonality;

import java.util.HashMap;

public class AdjacencyMatrix<V, E extends Number> implements Graph<V, E> {
    /**
     * Hashmap matrix of vertices adjacency. Every vertex has a hashmap for its edges.
     */
    private HashMap<Vertex<V>, HashMap<Vertex<V>,Edge<E>>> matrix;

    public AdjacencyMatrix() {
        matrix = new HashMap<>();
    }
    @Override
    public Vertex addVertex(V vertex) {
        return null;
    }

    @Override
    public Vertex removeVertex(V vertex) {
        return null;
    }

    @Override
    public Edge addEdge(V v1, V v2) {
        return null;
    }

    @Override
    public Edge removeEdge(V v1, V v2) {
        return null;
    }

    @Override
    public Vertex getVertex(V value) {
        return null;
    }

    @Override
    public Edge getEdge(E weight) {
        return null;
    }
}

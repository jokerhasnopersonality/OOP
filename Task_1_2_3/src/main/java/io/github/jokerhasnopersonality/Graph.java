package io.github.jokerhasnopersonality;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;

/**
 * Graph interface for graph realizations.
 */
public interface Graph<V, E extends Number> {
    public Vertex<V> addVertex(V value);

    public void removeVertex(Vertex<V> vertex);

    public Edge<V, E> addEdge(Vertex<V> v1, Vertex<V> v2, E weight);

    public void removeEdge(Edge<V, E> edge);

    public Vertex<V> getVertex(V value);

    public Edge<V, E> getEdge(Vertex<V> start, Vertex<V> end);

    public List<Vertex<V>> getVertices();

    public List<Edge<V, E>> getEdges();

    public int getVerticesCnt();

    public int getEdgesCnt();
}
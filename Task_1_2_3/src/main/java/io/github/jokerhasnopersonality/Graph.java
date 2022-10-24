package io.github.jokerhasnopersonality;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;

public interface Graph<V, E extends Number> {
    public Vertex addVertex(V vertex);

    public Vertex removeVertex(V vertex);

    public Edge addEdge(V v1, V v2);

    public Edge removeEdge(V v1, V v2);

    public Vertex getVertex(V value);

    public Edge getEdge(E weight);
}
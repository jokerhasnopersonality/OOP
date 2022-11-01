package io.github.jokerhasnopersonality;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Graph realization supporting adjacency matrix.
 */
public class AdjacencyMatrix<V, E extends Number> implements Graph<V, E> {
    private int verticesCnt;
    private int edgesCnt;
    /**
     * Hashmap matrix of vertices adjacency. Every vertex has a hashmap for its edges.
     */
    private final HashMap<Vertex<V>, HashMap<Vertex<V>, Edge<V, E>>> matrix;

    public AdjacencyMatrix() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V value) {
        Vertex<V> newVertex = new Vertex(value);
        matrix.putIfAbsent(newVertex, new HashMap<Vertex<V>, Edge<V, E>>());
        verticesCnt++;
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        matrix.get(vertex).clear();
        matrix.remove(vertex);
        verticesCnt--;
    }

    @Override
    public Edge<V, E> addEdge(Vertex<V> v1, Vertex<V> v2, E weight)
            throws NullPointerException, IllegalStateException {
        if (v1 == null || v2 == null) {
            throw new NullPointerException("Edge vertices must be specified.");
        }
        Edge<V, E> newEdge = new Edge<>(v1, v2, weight);
        matrix.get(v1).put(v2, newEdge);
        edgesCnt++;
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        matrix.get(edge.getStart()).remove(edge.getEnd());
        edgesCnt--;
    }

    @Override
    public Vertex<V> getVertex(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("Vertex value must be specified.");
        }
        for (Vertex<V> vertex : matrix.keySet()) {
            if (vertex.getValue().equals(value)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public Edge<V, E> getEdge(Vertex<V> start, Vertex<V> end) {
        return matrix.get(start).get(end);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        List<Vertex<V>> vertices = new ArrayList<Vertex<V>>(matrix.keySet());
        return vertices;
    }

    @Override
    public List<Edge<V, E>> getEdges() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vertex : matrix.keySet()) {
            set.addAll(matrix.get(vertex).values());
        }
        List<Edge<V, E>> edges = new ArrayList<>();
        edges.addAll(set);
        return edges;
    }

    public int getVerticesCnt() {
        return verticesCnt;
    }

    public int getEdgesCnt() {
        return edgesCnt;
    }
}

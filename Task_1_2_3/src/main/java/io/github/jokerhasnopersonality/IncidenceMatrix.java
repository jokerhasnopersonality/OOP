package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Graph realization supporting incidence matrix.
 */
public class IncidenceMatrix<V, E extends Number> implements Graph<V, E> {
    private int verticesCnt;
    private int edgesCnt;
    private HashMap<Vertex<V>, HashMap<Edge<V, E>, EdgeSideType>> matrix;

    /**
     * Edge types for maintaining directions.
     */
    public enum EdgeSideType {
        END,
        START
    }

    public IncidenceMatrix() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V value) {
        Vertex<V> newVertex = new Vertex<>(value);
        matrix.put(newVertex, new HashMap<Edge<V, E>, EdgeSideType>());
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
    public Edge<V, E> addEdge(Vertex<V> v1, Vertex<V> v2, E weight) {
        Edge<V, E> newEdge;
        newEdge = this.getEdge(v2, v1);
        newEdge = new Edge<>(v1, v2, weight);
        matrix.get(v1).put(newEdge, EdgeSideType.START);
        matrix.get(v2).put(newEdge, EdgeSideType.END);
        edgesCnt++;
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        for (Vertex<V> vertex : matrix.keySet()) {
            matrix.get(vertex).remove(edge);
        }
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
        for (Edge<V, E> edge : matrix.get(start).keySet()) {
            if (matrix.get(start).get(edge).equals(EdgeSideType.START)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public List<Vertex<V>> getVertices() {
        List<Vertex<V>> vertices = new ArrayList<>(matrix.keySet());
        return vertices;
    }

    @Override
    public List<Edge<V, E>> getEdges() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vertex : matrix.keySet()) {
            set.addAll(matrix.get(vertex).keySet());
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

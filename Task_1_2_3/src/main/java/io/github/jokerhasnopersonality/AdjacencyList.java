package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Graph realization supporting adjacency lists.
 */
public class AdjacencyList<V, E extends Number> implements Graph<V, E> {
    private int verticesCnt;
    private int edgesCnt;
    HashMap<Vertex<V>, ArrayList<Tuple<Edge<V, E>, Vertex<V>>>> map;

    public AdjacencyList() {
        map = new HashMap<>();
    }
    @Override
    public Vertex<V> addVertex(V value) {
        Vertex<V> newVertex = new Vertex<>(value);
        map.putIfAbsent(newVertex, new ArrayList<>());
        verticesCnt++;
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        for (ArrayList<Tuple<Edge<V, E>, Vertex<V>>> l : map.values()) {
            l.removeIf(tuple -> tuple.getSecond().getValue().equals(vertex.getValue()));
        }
        map.remove(vertex);
        verticesCnt--;
    }

    @Override
    public Edge<V, E> addEdge(Vertex<V> v1, Vertex<V> v2, E weight) {
        Edge<V, E> newEdge = new Edge<>(v1, v2, weight);
        map.get(v1).add(new Tuple<>(newEdge, v2));
        edgesCnt++;
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        map.get(edge.getStart()).removeIf(t -> (t.getFirst().getEnd().getValue().equals(edge.getEnd().getValue())));
        edgesCnt--;
    }

    @Override
    public Vertex<V> getVertex(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("Vertex value must be specified.");
        }
        for (Vertex<V> vertex : map.keySet()) {
            if (vertex.getValue().equals(value)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public Edge<V, E> getEdge(Vertex<V> start, Vertex<V> end) {
        for (Tuple<Edge<V, E>, Vertex<V>> t : map.get(start)) {
            if (t.getFirst().getEnd().getValue().equals(end.getValue())) {
                return t.getFirst();
            }
        }
        return null;
    }

    @Override
    public List<Vertex<V>> getVertices() {
        List<Vertex<V>> vertices = new ArrayList<Vertex<V>>(map.keySet());
        return vertices;
    }

    @Override
    public List<Edge<V, E>> getEdges() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vertex : map.keySet()) {
            for (Tuple<Edge<V, E>, Vertex<V>> t : map.get(vertex)){
                set.add(t.getFirst());
            }
        }
        List<Edge<V, E>> edges = new ArrayList<>();
        edges.addAll(set);
        return edges;
    }

    private class Tuple<A, B> {
        private A first;
        private B second;

        private Tuple(A first, B second) {
            this.first = first;
            this.second = second;
        }

        private A getFirst() {
            return first;
        }

        private B getSecond() {
            return second;
        }
    }

    public int getVerticesCnt() {
        return verticesCnt;
    }

    public int getEdgesCnt() {
        return edgesCnt;
    }
}

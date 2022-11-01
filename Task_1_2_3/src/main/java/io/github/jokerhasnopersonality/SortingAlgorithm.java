package io.github.jokerhasnopersonality;

import java.util.*;

public class SortingAlgorithm<V> {
    public static <V> Map<V, Integer> Sort(Vertex<V> start, Graph<V, Integer> graph) {
        final Map<V, Integer> sort = new HashMap<>();
        for (Vertex<V> vertex : graph.getVertices()) {
            sort.put(vertex.getValue(), java.lang.Integer.MAX_VALUE);
        }
        sort.replace(start.getValue(), 0);
        for (int i = 0; i < graph.getVerticesCnt(); i++) {
            for (Edge<V, Integer> edge : graph.getEdges()) {
                if (sort.get(edge.getStart().getValue()) == Integer.MAX_VALUE) {
                    continue;
                }
                if ((edge.getWeight()+sort.get(edge.getStart().getValue())) < sort.get(edge.getEnd().getValue())) {
                    sort.replace(edge.getEnd().getValue(), edge.getWeight()+sort.get(edge.getStart().getValue()));
                }
            }
        }

        return sort;
    }
}

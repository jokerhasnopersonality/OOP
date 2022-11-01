package io.github.jokerhasnopersonality;

import java.util.HashMap;
import java.util.Map;

/**
 * Sorting algorithm for graphs.
 */
public class SortingAlgorithm<V> {
    /**
     * Sorts the vertices based on their shortest path from the given vertex.
     *
     * @param start vertex to start the sorting from
     *
     * @param graph graph to run sorting algorithm on
     */
    public static <V> Map<V, Integer> sort(Vertex<V> start, Graph<V, Integer> graph) {
        final Map<V, Integer> sort = new HashMap<>();
        for (Vertex<V> vertex : graph.getVertices()) {
            sort.put(vertex.getValue(), java.lang.Integer.MAX_VALUE);
        }
        sort.replace(start.getValue(), 0);
        int compare;
        for (int i = 0; i < graph.getVerticesCnt(); i++) {
            for (Edge<V, Integer> edge : graph.getEdges()) {
                if (sort.get(edge.getStart().getValue()) == Integer.MAX_VALUE) {
                    continue;
                }
                compare = edge.getWeight() + sort.get(edge.getStart().getValue());
                if (compare < sort.get(edge.getEnd().getValue())) {
                    sort.replace(edge.getEnd().getValue(), compare);
                }
            }
        }

        return sort;
    }
}

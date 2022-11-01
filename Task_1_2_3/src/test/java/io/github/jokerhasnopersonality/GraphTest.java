package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.jokerhasnopersonality.GraphReader.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to check Graph realizations.
 */
public class GraphTest {
    @Test
    public void test0() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        try {
            graph.addEdge(null, null, 9);
        } catch (NullPointerException thrown) {}
        try {
            graph.getVertex(null);
        } catch (NullPointerException thrown) {}
    }

    @Test
    public void graphReaderTest() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        GraphReader.graphReader(graph);
        Vertex<String> v1, v2;
        v1 = graph.getVertex("C");
        v2 = graph.getVertex("G");
        Assertions.assertEquals("C", v1.getValue());
        Assertions.assertEquals("G", v2.getValue());
        Assertions.assertEquals(10, graph.getEdge(v1, v2).getWeight());

        graph = new IncidenceMatrix<>();
        GraphReader.graphReader(graph);
        v1 = graph.getVertex("A");
        v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getValue());
        Assertions.assertEquals("B", v2.getValue());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());

        graph = new AdjacencyMatrix<>();
        GraphReader.graphReader(graph);
        v1 = graph.getVertex("A");
        v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getValue());
        Assertions.assertEquals("B", v2.getValue());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());
    }

    @Test
    public void simpleTest() {
        Graph<String, Integer> graph = new AdjacencyList<>();
        GraphReader.graphReader(graph);
        Vertex<String> v1, v2;
        v1 = graph.getVertex("C");
        Map<String, Integer> testSort = SortingAlgorithm.Sort(v1, graph);
        Map<String, Integer> expectedSort = new HashMap<>();
        expectedSort.put("C", 0);
        expectedSort.put("D", 2);
        expectedSort.put("E", 4);
        expectedSort.put("F", 5);
        expectedSort.put("G", 9);
        expectedSort.put("B", 10);
        expectedSort.put("A", 14);
        Assertions.assertEquals(expectedSort, testSort);
    }
}
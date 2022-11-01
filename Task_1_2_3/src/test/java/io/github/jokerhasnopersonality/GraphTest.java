package io.github.jokerhasnopersonality;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests to check Graph realizations.
 */
public class GraphTest {
    @Test
    public void test0() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        Assertions.assertThrows(NullPointerException.class, () -> graph.addEdge(null, null, 9));
        Assertions.assertThrows(NullPointerException.class, () -> graph.getVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> new Vertex<String>(null));
        Assertions.assertThrows(NullPointerException.class, () -> new Edge<>(null, null, 12));
        Assertions.assertThrows(NullPointerException.class, () ->
                new Vertex<String>("A").setValue(null));
    }

    @Test
    public void graphReaderTest() {
        String path = "/Graph.txt";
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        GraphReader.graphReader(graph, path);
        Vertex<String> v1;
        Vertex<String> v2;
        v1 = graph.getVertex("C");
        v2 = graph.getVertex("G");
        Assertions.assertEquals("C", v1.getValue());
        Assertions.assertEquals("G", v2.getValue());
        Assertions.assertEquals(10, graph.getEdge(v1, v2).getWeight());

        graph = new IncidenceMatrix<>();
        GraphReader.graphReader(graph, path);
        v1 = graph.getVertex("A");
        v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getValue());
        Assertions.assertEquals("B", v2.getValue());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());

        graph = new AdjacencyList<>();
        GraphReader.graphReader(graph, path);
        v1 = graph.getVertex("A");
        v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getValue());
        Assertions.assertEquals("B", v2.getValue());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());
    }

    @Test
    public void testAdjacencyMatrix() {
        AdjacencyMatrix<String, Integer> graph = new AdjacencyMatrix<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        Vertex<String> d = graph.addVertex("D");
        Vertex<String> e = graph.addVertex("E");
        Vertex<String> f = graph.addVertex("F");
        graph.addEdge(a, b, 3);
        graph.addEdge(a, c, 6);
        graph.addEdge(a, e, 9);
        graph.addEdge(a, d, 1);
        graph.addEdge(b, e, 5);
        graph.addEdge(e, c, 2);
        graph.addEdge(c, f, 7);
        for (Edge<String, Integer> edge : graph.getEdges()) {
            Assertions.assertTrue(graph.getEdges().contains(edge));
        }
        Assertions.assertEquals(6, graph.getVerticesCnt());
        Assertions.assertEquals(7, graph.getEdgesCnt());
    }

    @Test
    public void testIncidenceMatrix() {
        IncidenceMatrix<String, Integer> graph = new IncidenceMatrix<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        Vertex<String> d = graph.addVertex("D");
        Vertex<String> e = graph.addVertex("E");
        Vertex<String> f = graph.addVertex("F");
        graph.addEdge(a, b, 3);
        graph.addEdge(a, c, 6);
        graph.addEdge(a, e, 9);
        graph.addEdge(a, d, 1);
        graph.addEdge(b, e, 5);
        graph.addEdge(e, c, 2);
        graph.addEdge(c, f, 7);
        for (Edge<String, Integer> edge : graph.getEdges()) {
            Assertions.assertTrue(graph.getEdges().contains(edge));
        }
        Assertions.assertEquals(6, graph.getVerticesCnt());
        Assertions.assertEquals(7, graph.getEdgesCnt());
    }

    @Test
    public void testAdjacencyList() {
        AdjacencyList<String, Integer> graph = new AdjacencyList<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        Vertex<String> d = graph.addVertex("D");
        Vertex<String> e = graph.addVertex("E");
        Vertex<String> f = graph.addVertex("F");
        graph.addEdge(a, b, 3);
        graph.addEdge(a, c, 6);
        graph.addEdge(a, e, 9);
        graph.addEdge(a, d, 1);
        graph.addEdge(b, e, 5);
        graph.addEdge(e, c, 2);
        graph.addEdge(c, f, 7);
        for (Edge<String, Integer> edge : graph.getEdges()) {
            Assertions.assertTrue(graph.getEdges().contains(edge));
        }
        Assertions.assertEquals(6, graph.getVerticesCnt());
        Assertions.assertEquals(7, graph.getEdgesCnt());
    }

    @Test
    public void simpleTest() {
        Graph<String, Integer> graph = new AdjacencyList<>();
        String path = "/Graph.txt";
        GraphReader.graphReader(graph, path);
        Vertex<String> v1 = graph.getVertex("C");
        Map<String, Integer> expectedSort = new HashMap<>();
        expectedSort.put("C", 0);
        expectedSort.put("D", 2);
        expectedSort.put("E", 4);
        expectedSort.put("F", 5);
        expectedSort.put("G", 9);
        expectedSort.put("B", 10);
        expectedSort.put("A", 14);
        Map<String, Integer> testSort = SortingAlgorithm.sort(v1, graph);
        Assertions.assertEquals(expectedSort, testSort);
        Edge<String, Integer> edge = graph.getEdge(
                graph.getVertex("E"),
                graph.getVertex("G")
        );
        graph.removeEdge(edge);
        testSort = SortingAlgorithm.sort(v1, graph);
        expectedSort.replace("G", 10);
        Assertions.assertEquals(expectedSort, testSort);
    }
}
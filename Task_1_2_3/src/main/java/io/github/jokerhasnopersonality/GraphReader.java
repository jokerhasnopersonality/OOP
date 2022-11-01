package io.github.jokerhasnopersonality;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * GraphReader class for readings graphs from text files.
 */
public class GraphReader {
    /**
     * Initializes a new graph based on the given text file.
     */
    public static void graphReader(Graph<String, Integer> graph, String path) {
        try {
            InputStream in = GraphReader.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            String[] numbers = line.split(" ");
            int verticesNumber = Integer.parseInt(numbers[0]);
            int edgesNumber = Integer.parseInt(numbers[1]);
            line = reader.readLine();
            String[] vertices = line.split(" ");
            for (int i = 0; i < verticesNumber; i++) {
                graph.addVertex(vertices[i]);
            }
            Vertex<String> v1;
            Vertex<String> v2;
            for (int i = 0; i < edgesNumber; i++) {
                line = reader.readLine();
                String[] edge = line.split(" ");
                v1 = graph.getVertex(edge[0]);
                v2 = graph.getVertex(edge[1]);
                graph.addEdge(v1, v2, Integer.parseInt(edge[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

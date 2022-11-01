package io.github.jokerhasnopersonality;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class GraphReader {
    public static void graphReader(Graph<String, Integer> graph) {
        try {
            Path path = Paths.get("C:\\Users\\37730\\OOP\\Task_1_2_3\\src\\main\\resources\\Graph.txt");
            InputStream in = new FileInputStream(path.toFile());
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
            Vertex<String> v1, v2;
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

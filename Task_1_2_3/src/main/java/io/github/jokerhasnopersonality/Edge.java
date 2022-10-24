package io.github.jokerhasnopersonality;

public class Edge<T extends Number> {
    private Vertex start;
    private Vertex end;
    private T weight;

    public Edge(Vertex start, Vertex end, T weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void setWeight(T weight) {
        this.weight = weight;
    }

    public T getWeight() {
        return weight;
    }
}

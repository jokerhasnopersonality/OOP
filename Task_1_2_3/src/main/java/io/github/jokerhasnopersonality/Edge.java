package io.github.jokerhasnopersonality;

public class Edge<V, E extends Number> {
    private Vertex<V> start;
    private Vertex<V> end;
    private E weight;

    public Edge(Vertex<V> start, Vertex<V> end, E weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void setWeight(E weight) {
        this.weight = weight;
    }

    public E getWeight() {
        return weight;
    }

    public Vertex<V> getStart() {
        return start;
    }

    public Vertex<V> getEnd() {
        return end;
    }
}

package io.github.jokerhasnopersonality;

public class Vertex<V> {
    private V value;

    public Vertex(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

}

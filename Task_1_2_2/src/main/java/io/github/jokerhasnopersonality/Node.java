package io.github.jokerhasnopersonality;

import java.util.ArrayList;

public class Node<T> {
    public T value;
    public Node<T> parent;
    public ArrayList<Node<T>> children;

    public Node() {
        children = new ArrayList<Node<T>>();
        parent = null;
        value = null;
    }

    public Node(T set_value) {
        children = new ArrayList<Node<T>>();
        parent = null;
         value = set_value;
    }

    public Node(T set_value, Node<T> set_parent) {
        children = new ArrayList<Node<T>>();
        value = set_value;
        parent = set_parent;
    }

    public Node(Node<T> set_parent) {
        children = new ArrayList<Node<T>>();
        parent = set_parent;
    }

    public int count_children() {
        return children.size();
    }
}

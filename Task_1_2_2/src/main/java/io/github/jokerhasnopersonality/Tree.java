package io.github.jokerhasnopersonality;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Generic class of a Tree collection.
 */
public class Tree<T> implements Collection<T> {
    private int count;
    private final Node<T> root;

    public Tree() {
        root = new Node<T>();
        count = 0;
    }

    /**
     * Returns the number of elements in this collection.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns {@code true} if this collection contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns {@code true} if this collection contains the specified element.
     *
     * @param o element whose presence in this collection is to be tested
     * @return {@code true} if this collection contains the specified
     * element
     */
    @Override
    public boolean contains(Object o) {
        for (T node : this) {
            if (node == o) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(IntFunction generator) {
        return Collection.super.toArray(generator);
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return Collection.super.removeIf(filter);
    }

    @Override
    public void clear() {

    }

    @Override
    public Spliterator spliterator() {
        return Collection.super.spliterator();
    }

    @Override
    public Stream stream() {
        return Collection.super.stream();
    }

    @Override
    public Stream parallelStream() {
        return Collection.super.parallelStream();
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
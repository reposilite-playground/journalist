package com.reposilite.journalist.backend;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class CircularBuffer<T> {

    private final Object[] bufferArray;

    private int current = -1;
    private int size;

    public CircularBuffer(int bufferSize) {
        this.bufferArray = new Object[bufferSize];
    }

    public synchronized CircularBuffer<T> add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }

        this.current = (current + 1) % bufferArray.length;
        this.bufferArray[current] = item;

        if (size + 1 > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException("Argument cannot be bigger than max value of an integer.");
        }

        this.size = Math.min(size + 1, bufferArray.length);

        return this;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isFull() {
        return bufferArray[Math.min(current + 1, bufferArray.length - 1)] != null;
    }

    @SuppressWarnings("unchecked")
    public synchronized List<T> getValues() {
        Object[] result = new Object[size()];

        if (!isFull()) {
            System.arraycopy(bufferArray, 0, result, 0, size);
        } else {
            int rightLength = bufferArray.length - (current + 1);
            int leftLength = bufferArray.length - rightLength;

            System.arraycopy(bufferArray, current + 1, result, 0, rightLength);
            System.arraycopy(bufferArray, 0, result, rightLength, leftLength);
        }

        return (List<T>) Arrays.asList(result);
    }

    @SuppressWarnings("unchecked")
    public synchronized Optional<T> find(Predicate<T> filter) {
        for (Object element : bufferArray) {
            if (element == null) {
                break;
            }

            if (filter.test((T) element)) {
                return Optional.of((T) element);
            }
        }

        return Optional.empty();
    }

}
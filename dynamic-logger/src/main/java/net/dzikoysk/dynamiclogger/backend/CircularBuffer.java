package net.dzikoysk.dynamiclogger.backend;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class CircularBuffer<T> {

    private final Object[] bufferArray;

    private int front;
    private int insertLocation;
    private int size;

    public CircularBuffer(int bufferSize) {
        this.bufferArray = new Object[bufferSize];
    }

    public synchronized void add(T item) {
        this.bufferArray[insertLocation] = item;
        this.insertLocation = (insertLocation + 1) % bufferArray.length;

        if (size == bufferArray.length) {
            this.front = (front + 1) % bufferArray.length;
        } else {
            this.size++;
        }
    }

    public synchronized int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public synchronized List<T> getValues() {
        Object[] result = new Object[size()];

        System.arraycopy(bufferArray, insertLocation, result, 0, size - insertLocation);
        System.arraycopy(bufferArray, 0, result, size - insertLocation, insertLocation);

        return (List<T>) Arrays.asList(result);
    }

    @SuppressWarnings("unchecked")
    public synchronized Optional<T> find(Predicate<T> filter) {
        for (Object element : bufferArray) {
            if (filter.test((T) element)) {
                return Optional.of((T) element);
            }
        }

        return Optional.empty();
    }

}
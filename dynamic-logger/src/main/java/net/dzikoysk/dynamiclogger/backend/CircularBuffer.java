package net.dzikoysk.dynamiclogger.backend;

class CircularBuffer<T> {

    private final Object[] bufferArray;

    private int front = 0;
    private int insertLocation = 0;
    private int size = 0;

    public CircularBuffer(int bufferSize) {
        bufferArray = new Object[bufferSize];
    }

    public synchronized void add(T item) {
        bufferArray[insertLocation] = item;
        insertLocation = (insertLocation + 1) % bufferArray.length;

        if (size == bufferArray.length) {
            front = (front + 1) % bufferArray.length;
        } else {
            size++;
        }
    }

    public synchronized int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public synchronized T peekAt(int n) {
        if (size <= n) {
            return null;
        } else {
            return (T) bufferArray[(front + n) % bufferArray.length];
        }
    }
}
package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object o) {
        Objects.requireNonNull(o);
        enqueueImpl(o);
        size++;
    }

    abstract void enqueueImpl(Object o);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    abstract Object elementImpl();

    public Object dequeue() {
        assert size > 0;
        Object result = dequeueImpl();
        size--;
        return result;
    }

    abstract Object dequeueImpl();

    public int size() {
        return size;
    }

    public void clear() {
        clearImp();
        size = 0;
    }

    abstract void clearImp();

    public boolean isEmpty() {
        return size == 0;
    }
}

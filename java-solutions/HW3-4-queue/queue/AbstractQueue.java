package queue;


public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object o) {
        assert o != null;
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
        Object result = elementImpl();
        dequeueImpl();
        size--;
        return result;
    }

    abstract void dequeueImpl();

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

    public void dedup() {
        if (size < 2) {
            return;
        }
        Object previous = dequeue();
        enqueue(previous);
        Object current = dequeue();
        int s = size;
        for (int i = 1; i < s; i++) {
            if (!previous.equals(current)) {
                enqueue(current);
            }
            previous = current;
            current = dequeue();
        }
        if (!current.equals(previous)) {
            enqueue(current);
        }
    }
}

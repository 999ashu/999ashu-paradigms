package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[9];
    private int head;

    private int getTail() {
        return head + size < elements.length ? head + size : head + size - elements.length;
    }

    @Override
    protected void enqueueImpl(Object o) {
        ensureCapacity();
        elements[getTail()] = o;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] temp = new Object[elements.length * 3];
            System.arraycopy(elements, head, temp, 0, elements.length - head);
            System.arraycopy(elements, 0, temp, elements.length - head, head);
            elements = temp;
            head = 0;
        }
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected Object dequeueImpl() {
        Object result = element();
        head = head + 1 < elements.length ? ++head : 0;
        return result;
    }

    // Pre: true.
    // Post: returns count of elements in queue that match condition.
    public int countIf(Predicate<Object> predicate) {
        int count = 0;
        int i;
        if (head + size >= elements.length) {
            for (i = head; i < elements.length - 1; i++) {
                if (predicate.test(elements[i])) {
                    count++;
                }
            }
        }
        for (i = 0; i < getTail() - 1; i++) {
            if (predicate.test(elements[i])) {
                count++;
            }
        }
        return count;
    }

    protected void clearImp() {
        elements = new Object[9];
        head = 0;
    }
}

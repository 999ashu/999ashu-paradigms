package queue;

import java.util.Arrays;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[5];
    private int head;

    private int getTail() {
        int tail = head + size;
        return tail < elements.length ? tail : tail - elements.length;
    }

    private int getPos(int pos) {
        return pos < elements.length ? pos : 0;
    }

    @Override
    protected void enqueueImpl(Object o) {
        ensureCapacity();
        elements[getTail()] = o;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] temp = new Object[elements.length * 2];
            System.arraycopy(elements, head, temp, 0, elements.length - head);
            System.arraycopy(elements, 0, temp, elements.length - head, head);
            elements = temp;
            head = 0;
        }
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected void dequeueImpl() {
        head = getPos(++head);
    }

    protected void clearImp() {
        elements = new Object[5];
        head = 0;
    }

    // Pre: true.
    // Post: returns count of elements in queue that match condition.
    public int countIf(Predicate<Object> predicate) {
        if (size == 0) {
            return 0;
        }
        int count = 0;
        int pointer = head;
        int tail = getTail();
        do {
            if (predicate.test(elements[pointer])) {
                count++;
            }
            pointer = getPos(pointer + 1);
        } while (pointer != tail);
        return count;
    }

    protected void dedupImp() {
        Object[] temp = new Object[5];
        int newSize = 0;
        temp[newSize++] = dequeue();
        while (size > 0) {
            Object curr = dequeue();
            if (!temp[newSize - 1].equals(curr)) {
                if (newSize == temp.length - 1) {
                    temp = Arrays.copyOf(temp, temp.length * 2);
                }
                temp[newSize++] = curr;
            }
        }
        elements = temp;
        size = newSize;
        head = 0;
    }
}

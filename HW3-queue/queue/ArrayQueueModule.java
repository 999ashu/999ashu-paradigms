package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueueModule {
    private static Object[] elements = new Object[9];
    private static int head;
    private static int size;

    private static int getTail() {
        return head + size < elements.length ? head + size : head + size - elements.length;
    }

    public static void enqueue(Object o) {
        Objects.requireNonNull(o);
        ensureCapacity();
        elements[getTail()] = o;
        size++;
    }

    private static void ensureCapacity() {
        if (size == elements.length) {
            Object[] temp = new Object[elements.length * 3];
            System.arraycopy(elements, head, temp, 0, elements.length - head);
            System.arraycopy(elements, 0, temp, elements.length - head, head);
            elements = temp;
            head = 0;
        }
    }

    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    public static Object dequeue() {
        assert size > 0;
        Object result = element();
        head = head + 1 < elements.length ? ++head : 0;
        size--;
        return result;
    }

    // Pre: true.
    // Post: returns count of elements in queue that match condition.
    public static int countIf(Predicate<Object> predicate) {
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

    public static int size() {
        return size;
    }

    public static void clear() {
        elements = new Object[9];
        head = 0;
        size = 0;
    }

    public static boolean isEmpty() {
        return size == 0;
    }
}

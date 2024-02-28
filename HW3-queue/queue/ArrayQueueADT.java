package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueueADT {
    private Object[] elements = new Object[9];
    private int head;
    private int size;

    private static int getTail(ArrayQueueADT queue) {
        return queue.head + queue.size < queue.elements.length ? queue.head + queue.size : queue.head + queue.size - queue.elements.length;
    }

    public static void enqueue(ArrayQueueADT queue, Object o) {
        Objects.requireNonNull(o);
        ensureCapacity(queue);
        queue.elements[getTail(queue)] = o;
        queue.size++;
    }

    private static void ensureCapacity(ArrayQueueADT queue) {
        if (queue.size == queue.elements.length) {
            Object[] temp = new Object[queue.elements.length * 3];
            System.arraycopy(queue.elements, queue.head, temp, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, temp, queue.elements.length - queue.head, queue.head);
            queue.elements = temp;
            queue.head = 0;
        }
    }

    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object result = element(queue);
        queue.head = queue.head + 1 < queue.elements.length ? ++queue.head : 0;
        queue.size--;
        return result;
    }

    // Pre: true.
    // Post: returns count of elements in queue that match condition.
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate) {
        int count = 0;
        int i;
        if (queue.head + queue.size >= queue.elements.length) {
            for (i = queue.head; i < queue.elements.length - 1; i++) {
                if (predicate.test(queue.elements[i])) {
                    count++;
                }
            }
        }
        for (i = 0; i < getTail(queue) - 1; i++) {
            if (predicate.test(queue.elements[i])) {
                count++;
            }
        }
        return count;
    }

    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[9];
        queue.head = 0;
        queue.size = 0;
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
}

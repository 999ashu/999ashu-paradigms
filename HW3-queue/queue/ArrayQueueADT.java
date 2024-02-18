package queue;

import java.util.Objects;

// Model: HEAD -> a[1], a[2], ..., a[n] <- TAIL.
//        New elements are appended to the tail.
//        Old elements are took from the head one by one.
// Invariant: n ⩾ 0, ∀ i ∈ [1; n]: a'[i] != null.
// Let: immutable(k), ∀ i ∈ [1; k]: a'[i] != a[i].
    public class ArrayQueueADT {
    private int amount = 9;
    private Object[] elements = new Object[amount];
    private int head = amount - 1;
    private int tail = head;
    private int size;

    // Pre: element != null.
    // Post: n' = n + 1, a'[n'] = element, immutable(n).
    public static void enqueue(ArrayQueueADT queue, Object o) {
        Objects.requireNonNull(o);
        ensureCapacity(queue);
        queue.elements[queue.tail] = o;
        queue.tail = queue.tail > 0 ? --queue.tail : queue.amount - 1;
        queue.size++;
    }

    // Pre: true.
    // Post: n' = n + 1, immutable(n).
    private static void ensureCapacity(ArrayQueueADT queue) {
        if ((queue.head == queue.tail) && (queue.elements[queue.head] != null)) {
            Object[] temp = new Object[queue.amount * 3];
            for (int i = temp.length - 1 - queue.size; i < temp.length; i++) {
                temp[i] = queue.elements[queue.tail];
                queue.tail = queue.tail < (queue.amount - 1) ? ++queue.tail : 0;
            }
            queue.amount *= 3;
            queue.head = queue.amount - 1;
            queue.tail = queue.head - queue.size;
            queue.elements = temp;
        }
    }

    // Pre: true.
    // Post: returns a[n], n' = n, immutable(n).
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    // Pre: true.
    // Post: returns a[n], n' = n - 1, immutable(n).
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object result = element(queue);
        queue.elements[queue.head] = null;
        queue.head = queue.head > 0 ? --queue.head : queue.amount - 1;
        queue.size--;
        return result;
    }

    // Pre: true.
    // Post: returns n, n' = n, immutable(n).
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: true.
    // Post: returns (n == 0), n' = n, immutable(n).
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pre: true.
    // Post: n' = 0.
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[queue.amount];
        queue.head = queue.amount - 1;
        queue.tail = queue.head;
        queue.size = 0;
    }
}

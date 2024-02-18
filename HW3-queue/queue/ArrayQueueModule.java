package queue;

import java.util.Objects;

// Model: HEAD -> a[1], a[2], ..., a[n] <- TAIL.
//        New elements are appended to the tail.
//        Old elements are took from the head one by one.
// Invariant: n ⩾ 0, ∀ i ∈ [1; n]: a'[i] != null.
// Let: immutable(k), ∀ i ∈ [1; k]: a'[i] != a[i].
public class ArrayQueueModule {
    private static int amount = 9;
    private static Object[] elements = new Object[amount];
    private static int head = amount - 1;
    private static int tail = head;
    private static int size;

    // Pre: element != null.
    // Post: n' = n + 1, a'[n'] = element, immutable(n).
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity();
        elements[tail] = element;
        tail = tail > 0 ? --tail : amount - 1;
        size++;
    }

    // Pre: true.
    // Post: n' = n + 1, immutable(n).
    private static void ensureCapacity() {
        if ((head == tail) && (elements[head] != null)) {
            Object[] temp = new Object[amount * 3];
            for (int i = temp.length - 1 - size; i < temp.length; i++) {
                temp[i] = elements[tail];
                tail = tail < (amount - 1) ? ++tail : 0;
            }
            amount *= 3;
            head = amount - 1;
            tail = head - size;
            elements = temp;
        }
    }

    // Pre: true.
    // Post: returns a[n], n' = n, immutable(n).
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pre: true.
    // Post: returns a[n], n' = n - 1, immutable(n).
    public static Object dequeue() {
        assert size > 0;
        Object result = element();
        elements[head] = null;
        head = head > 0 ? --head : amount - 1;
        size--;
        return result;
    }

    // Pre: true.
    // Post: returns n, n' = n, immutable(n).
    public static int size() {
        return size;
    }

    // Pre: true.
    // Post: returns (n == 0), n' = n, immutable(n).
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: true.
    // Post: n' = 0.
    public static void clear() {
        elements = new Object[amount];
        head = amount - 1;
        tail = head;
        size = 0;
    }
}

package queue;

// Model: HEAD -> a[1], a[2], ..., a[n] <- TAIL.
//        New elements are to the tail.
//        Old elements are took from the head one by one.
// Invariant: n ⩾ 0, ∀ i ∈ [1; n]: a'[i] != null.
// Let: immutable(k), ∀ i ∈ [1; k]: a'[i] != a[i].
public interface Queue {
    // Pre: element != null.
    // Post: n' = n + 1, a'[n'] = element, immutable(n).
    void enqueue(Object o);

    // Pre: true.
    // Post: returns a[n], n' = n, immutable(n).
    Object element();

    // Pre: true.
    // Post: returns a[n], n' = n - 1, immutable(n).
    Object dequeue();

    // Pre: true.
    // Post: returns n, n' = n, immutable(n).
    int size();

    // Pre: true.
    // Post: returns (n == 0), n' = n, immutable(n).
    boolean isEmpty();

    // Pre: true.
    // Post: n' = 0.
    void clear();
}

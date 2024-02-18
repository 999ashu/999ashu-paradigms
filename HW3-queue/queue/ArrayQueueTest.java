package queue;

public class ArrayQueueTest {
    public static void fill(ArrayQueue queue, int x) {
        for (int i = 1; i <= x; i++) {
            queue.enqueue("bebra numba " + i);
        }
    }

    public static void dump(ArrayQueue queue, int x) {
        for (int i = 1; i <= x; i++) {
            System.out.println(
                    queue.size() + " | " +
                            queue.element() + " | " +
                            queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fill(queue1, 4);
        fill(queue2, 10);
        System.out.println("queue1:");
        dump(queue1, 4);
        System.out.println(System.lineSeparator() + "queue2:");
        dump(queue2, 10);
    }
}

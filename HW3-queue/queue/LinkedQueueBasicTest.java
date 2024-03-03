package queue;

public class LinkedQueueBasicTest {
    public static void fillDup(Queue queue, int x) {
        for (int i = 1; i <= x; i++) {
            queue.enqueue("bebra numba " + i);
            queue.enqueue("bebra numba " + i);
        }
    }

    public static void dump(Queue queue, int x) {
        for (int i = 1; i <= x; i++) {
            if (!queue.isEmpty()) {
                System.out.println(
                        queue.size() + " | " +
                                queue.element() + " | " +
                                queue.dequeue());
            } else {
                System.out.println("Empty queue");
            }
        }
    }

    public static void main(String[] args) {
        Queue queue1 = new ArrayQueue();
        Queue queue2 = new LinkedQueue();
        fillDup(queue1, 20);
        fillDup(queue2, 20);
        queue1.dedup();
        queue2.dedup();
        System.out.println("ArrayQueue:");
        dump(queue1, 20);
        System.out.println(System.lineSeparator() + "LinkedQueue:");
        dump(queue2, 20);
    }
}

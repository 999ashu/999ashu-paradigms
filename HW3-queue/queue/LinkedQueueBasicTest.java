package queue;

public class LinkedQueueBasicTest {
    public static void fill(Queue queue, int x) {
        for (int i = 1; i <= x; i++) {
            queue.enqueue("bebra numba " + i);
        }
    }

    public static void dump(Queue queue, int x) {
        for (int i = 1; i <= x; i++) {
            System.out.println(
                    queue.size() + " | " +
                            queue.element() + " | " +
                            queue.dequeue());
        }
    }

    public static void main(String[] args) {
        Queue queue1 = new ArrayQueue();
        Queue queue2 = new LinkedQueue();
        fill(queue1, 10);
        fill(queue2, 10);
        System.out.println("ArrayQueue:");
        dump(queue1, 10);
        System.out.println(System.lineSeparator() + "LinkedQueue:");
        dump(queue2, 10);
    }
}

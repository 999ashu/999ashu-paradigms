package queue;

import java.util.function.Predicate;

public class ArrayQueueBasicTest {
    public static void fillWithOnes(ArrayQueue queue, int x) {
        for (int i = 1; i <= x; i++) {
            queue.enqueue(1);
        }
    }

    public static void fill(ArrayQueue queue, int x) {
        for (int i = 1; i <= x; i++) {
            queue.enqueue(i);
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
        Predicate<Object> isOne = i -> (i.equals(1));
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fillWithOnes(queue1, 4);
        fill(queue2, 4);
        System.out.println("First queue");
        System.out.println("Elements that match predicate: " + queue1.countIf(isOne));
        dump(queue1, 4);
        System.out.println(System.lineSeparator() + "Second queue");
        System.out.println("Elements that match predicate: " + queue2.countIf(isOne));
        dump(queue2, 4);
    }
}

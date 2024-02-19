package queue;

public class ArrayQueueADTBasicTest {
    public static void fill(ArrayQueueADT queue, int x) {
        for (int i = 1; i <= x; i++) {
            ArrayQueueADT.enqueue(queue, "bebra numba " + i);
        }
    }

    public static void dump(ArrayQueueADT queue, int x) {
        for (int i = 1; i <= x; i++) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " | " +
                            ArrayQueueADT.element(queue) + " | " +
                            ArrayQueueADT.dequeue(queue));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();
        fill(queue1, 4);
        fill(queue2, 10);
        System.out.println("queue1:");
        dump(queue1, 4);
        System.out.println(System.lineSeparator() + "queue2:");
        dump(queue2, 10);
    }
}

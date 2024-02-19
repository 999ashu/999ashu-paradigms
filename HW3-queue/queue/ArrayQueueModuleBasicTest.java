package queue;

public class ArrayQueueModuleBasicTest {
    public static void fill(int x) {
        for (int i = 1; i <= x; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump(int x) {
        for (int i = 1; i <= x; i++) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue());
        }
    }

    public static void main(String[] args) {
        fill(4);
        dump(4);
    }
}

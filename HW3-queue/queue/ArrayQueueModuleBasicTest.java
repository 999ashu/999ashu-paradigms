package queue;

import java.util.function.Predicate;

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
        ArrayQueueModule.enqueue(1);
        Predicate<Object> isOne = i -> (i.equals(1));
        System.out.print("Elements that match predicate: ");
        System.out.println(ArrayQueueModule.countIf(isOne));
        dump(5);
    }
}

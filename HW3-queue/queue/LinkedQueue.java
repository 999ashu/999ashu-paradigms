package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;


    @Override
    void enqueueImpl(Object o) {
        Node current = new Node(o);
        if (size == 0) {
            head = current;
            head.next = tail;
        } else {
            tail.next = current;
        }
        tail = current;
    }

    @Override
    Object elementImpl() {
        return head.value;
    }

    @Override
    void dequeueImpl() {
        head = head.next;
    }

    @Override
    void clearImp() {
        head = null;
        tail = null;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object o) {
            value = o;
        }
    }
}

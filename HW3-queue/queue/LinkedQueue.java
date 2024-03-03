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

    protected void dedupImp() {
        Node current = head;
        while (current.next != null) {
            if (current.value.equals(current.next.value)) {
                size--;
                if (current.next.next == null) {
                    current.next = null;
                    tail = current;
                    break;
                } else {
                    current.next = current.next.next;
                }
            } else {
                current = current.next;
            }
        }
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object o) {
            value = o;
        }
    }
}

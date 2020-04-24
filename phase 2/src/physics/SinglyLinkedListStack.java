package physics;

public class SinglyLinkedListStack<T> {

    private int size;
    private Node<T> head;

    public SinglyLinkedListStack() {
        head = null;
        size = 0;
    }

    public void push(T element) {
        if(head == null) {
            head = new Node(element);
        } else {
            Node<T> newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    public T pop() {
        if(head == null)
            return null;
        else {
            T topData = head.data;

            head = head.next;
            size--;

            return topData;
        }
    }

    public T top() {
        if(head != null)
            return head.data;
        else
            return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }

    }

}
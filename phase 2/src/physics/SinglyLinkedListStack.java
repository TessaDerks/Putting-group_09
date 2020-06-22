package physics;

public class SinglyLinkedListStack<T> {

    private int size;
    private Node<T> head;

    public SinglyLinkedListStack() {
        head = null;
        size = 0;
    }

    /**
     * push element to the stack
     * @param element
     */
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

    /**
     * pop element of the stack
     * @return topData
     */
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

    /**
     * size of the stack
     * @return size
     */
    public int size() {
        return size;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        /**
         * Constructor
         * @param data
         */
        public Node(T data) {
            this.data = data;
        }

    }

}
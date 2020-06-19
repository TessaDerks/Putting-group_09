package physics;

public class SinglyLinkedListStack<T> {

    private int size;
    private Node<T> head;

    public SinglyLinkedListStack() {
        head = null;
        size = 0;
    }

    /**
     *
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
     *
     * @return
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

    public int size() {
        return size;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        /**
         *
         * @param data
         */
        public Node(T data) {
            this.data = data;
        }

    }

}
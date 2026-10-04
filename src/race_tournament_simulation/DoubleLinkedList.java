package race_tournament_simulation;

public class DoubleLinkedList {
    private Node head;
    private Node tail;

    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    public void add(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public Node getNodeAt(int position) {
        Node temp = head;
        int count = 1;
        while (temp != null && count < position) {
            temp = temp.getNext();
            count++;
        }
        return temp;
    }

    public Node getHead() { return head; }
}


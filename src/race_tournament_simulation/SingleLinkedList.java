package race_tournament_simulation;

public class SingleLinkedList {
    Node head;

    public void add(Object data) {
        if (head == null) {
            head = new Node(data);
        } else {
            Node temp = head;
            while (temp.getLink() != null) {
                temp = temp.getLink();
            }
            temp.setLink(new Node(data));
        }
    }

    public void sortedAdd(Car newCar) 
    {
        if (head == null || ((Car)head.getData()).getScore() > newCar.getScore() || 
           (((Car)head.getData()).getScore() == newCar.getScore() && ((Car)head.getData()).getId() > newCar.getId()))
        {
            
            Node newNode = new Node(newCar);
            newNode.setLink(head);
            head = newNode;
            
        } else 
        {
            Node temp = head;
           
            while (temp.getLink() != null && 
                  (((Car)temp.getLink().getData()).getScore() < newCar.getScore() ||
                  (((Car)temp.getLink().getData()).getScore() == newCar.getScore() && 
                   ((Car)temp.getLink().getData()).getId() < newCar.getId()))) 
            {
                temp = temp.getLink();
            }
            
            Node newNode = new Node(newCar);
            newNode.setLink(temp.getLink());
            temp.setLink(newNode);
            
        }
    }

    
    public void removeTrack(int trackId) {
        if (head == null) return;
        
        if (((Track) head.getData()).id == trackId) {
            head = head.getLink();
            return;
        }
        
        Node temp = head;
        
        while (temp.getLink() != null && ((Track) temp.getLink().getData()).id != trackId) {
            temp = temp.getLink();
        }
        if (temp.getLink() != null) {
            temp.setLink(temp.getLink().getLink());
        }
    }

    
    public void removeCar(int id) {
        if (head == null) return;

        Car firstCar = (Car) head.getData();
        if (firstCar.getId() == id) {
            head = head.getLink();
            return;
        }

        Node temp = head;
        
        while (temp.getLink() != null) {
            Car c = (Car) temp.getLink().getData();
            if (c.getId() == id) {
                temp.setLink(temp.getLink().getLink());
                return;
            }
            temp = temp.getLink();
        }
    }
    
    
    public int size() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.getLink();
        }
        return count;
    }
}
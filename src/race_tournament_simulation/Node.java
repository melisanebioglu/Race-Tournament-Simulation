package race_tournament_simulation;

public class Node {
	   Object data;
	   Node link;  
	   Node prev; 

	   public Node(Object dataToAdd) {
	       data = dataToAdd;
	       link = null;
           prev = null;
	   }
	   
	   public Object getData() { return data; }
	   public void setData(Object data) { this.data = data; }
	   public Node getLink() { return link; } 
	   public void setLink(Node link) { this.link = link; }
	   

	   public Node getNext() { return link; } 
	   public void setNext(Node link) { this.link = link; }
	   public Node getPrev() { return prev; }
	   public void setPrev(Node prev) { this.prev = prev; }
	}

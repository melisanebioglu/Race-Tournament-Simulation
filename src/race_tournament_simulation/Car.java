package race_tournament_simulation;

public class Car {
	 int id;
     String name;
     int initialScore;
     int score; 
     String type;
     int totalPoint;
     int position; 

    public Car(int id, String name, int score, String type) {
        this.id = id;
        this.name = name;
        this.initialScore = score;
        this.score = score;
        this.type = type;
        this.totalPoint = 0;
        this.position = 1;
    }
    
    public void resetForNewRace() {
        this.score = this.initialScore;
        this.position = 1;
    }
    
    public int getId() 
    { return id; }
    
    public void setId(int id) 
    { this.id = id; }
    
    public String getName() 
    { return name; }
    
    public void setName(String name) 
    { this.name = name; }
    
    public int getScore() 
    { return score; }
    
    public void setScore(int score) 
    { this.score = score; }
    
    public String getType() 
    { return type; }
    
    public void setType(String type) 
    { this.type = type; }
    
    public int getTotalPoint() 
    { return totalPoint; }
    
    public void setTotalPoint(int point) 
    { this.totalPoint += point; }
    
    public int getPosition() 
    { return position; }
    
    public void setPosition(int position) 
    { this.position = position; }
    
    public int getInitialScore() 
    {
        return initialScore; }
    
}

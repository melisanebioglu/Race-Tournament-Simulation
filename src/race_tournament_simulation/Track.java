package race_tournament_simulation;

public class Track {
	int id;
	 String name;
	 String type;
	 int boost;
    DoubleLinkedList trackPath; 

    public Track(int id, String name, String type, int boost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.boost = boost;
        this.trackPath = new DoubleLinkedList();
        generateTrack(); 
    }

    private void generateTrack() 
    {
        for (int i = 1; i <= 50; i++) 
        {
            trackPath.add(0); 
        } 
    }

    public int getId() 
    { return id; }
    
    public void setId(int id) 
    { this.id = id; }
    
    public String getName()
    { return name; }
    
    public void setName(String name) 
    { this.name = name; }
    
    public String getType()
    { return type; }
    
    public void setType(String type) 
    { this.type = type; }
    
    public int getBoost()
    { return boost; }
    
    public void setBoost(int boost) 
    { this.boost = boost; }
    
    public DoubleLinkedList getTrackPath() 
    { return trackPath; }
 
    
    
    
    
    
    
    
    public void setSpecialUnits() {
        java.util.Random rnd = new java.util.Random();
        
        int teleportCount = 0;
        while (teleportCount < 10) 
        {
            int pos = rnd.nextInt(48) + 2; 
            
            Node target = trackPath.getNodeAt(pos);
            
            if ((int)target.getData() == 0) 
            { 
                target.setData(1);
                teleportCount++;
            }
        }
           
        boolean resetPlaced = false;
        while (!resetPlaced) 
        {
            int pos = rnd.nextInt(48) + 2;
            
            Node target = trackPath.getNodeAt(pos);
            
            if ((int)target.getData() == 0) 
            {
                target.setData(2);
                resetPlaced = true;
            }
        }
    }
    
    
    public int getUnitType(int position) 
    {
       
        Node target = trackPath.getNodeAt(position);
        
        if (target != null) 
        {
            return (int) target.getData();
        }
        
        return 0;
    }
    
    
}

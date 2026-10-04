package race_tournament_simulation;

import java.util.Random;

public class Logic {
	
	//kurallar kısmısı
	
	public static int getMatchupBonus(String typeA, String typeB) 
	{
		if (typeA.equals("Electric") && typeB.equals("Water")) 
			return 15;
        if (typeA.equals("Water") && typeB.equals("Fire")) 
        	return 15;
        if (typeA.equals("Fire") && typeB.equals("Earth")) 
        	return 15;
        if (typeA.equals("Earth") && typeB.equals("Electric")) 
        	return 15;
        if (typeA.equals("Air") && typeB.equals("Earth")) 
        	return 10;
        if (typeA.equals("Heavy") && typeB.equals("Air")) 
        	return 10;
        
        return 0; 
	}
	

	public static int calculateInitialScore(Car car, Car opponent, Track track) {
	    int bonus = 0;
	    
	    if (car.getType().equalsIgnoreCase(track.getType())) 
	    {
	        bonus = bonus + track.getBoost(); 
	    }
	  
	    int matchupBonus = getMatchupBonus(car.getType(), opponent.getType());
	    bonus = bonus + matchupBonus;

	    return bonus;
	}

	
    private static Random rnd = new Random();
	
	public static int getRandomMove() 
	{
        return rnd.nextInt(3) + 1;
    }
	
	
	public static void applyMovementPenalty(Car car, int distance) 
	{
	    int penalty = distance * 5;
	    car.setScore(car.getScore() - penalty);
	}
	
	    
	public static int handleSpecialUnit(Car car, int unitType) 
	{
	    int currentPos = car.getPosition(); 
	    
	    if (unitType == 1) 
	    {
	        int jump = rnd.nextInt(5) + 1;
	        int direction = rnd.nextInt(2); 
	        
	        int newPos;
	        if (direction == 1) 
	        {
	            newPos = currentPos + jump;
	        } else 
	        {
	            newPos = currentPos - jump;
	        }
	        	       
	        if (newPos < 1) 
	        {
	            newPos = 1;
	        }
	        if (newPos > 50) 
	        {
	            newPos = 50;
	        }
	        
	        car.setScore(car.getScore() - 5);
	        
	        return newPos;
	        
	    } 
	    else if (unitType == 2) 
	    {
	        car.setScore(car.getScore() - 5);
	        return 1; 
	    }
	    
	    return currentPos; 
	}
	
	
	public static Car getBestOpponent(Car playerCar, SingleLinkedList carsList, Track selectedTrack) 
	{
	    Car bestCar = null;
	    int maxScore = -1;
	    Node temp = carsList.head;
	    
	    while (temp != null) 
	    {
	        Car candidate = (Car)temp.getData();
	        
	        if (candidate.getId() != playerCar.getId()) 
	        {
	        	int totalPotentialScore = candidate.getInitialScore() + calculateInitialScore(candidate, playerCar, selectedTrack);
	            
	            if (totalPotentialScore > maxScore) 
	            {
	                maxScore = totalPotentialScore;
	                bestCar = candidate;
	            }
	        }
	        
	        temp = temp.getLink();
	    }
	    
	    return bestCar;
	}
	
	
	public static boolean isRaceOver(Car car, Car opponent) 
	{
		
	    if (car.getPosition() >= 50 || opponent.getPosition() >= 50) 
	    {
	        return true;
	    }
	    
	    if (car.getScore() <= 0 || opponent.getScore() <= 0) 
	    {
	        return true;
	    }
	    
	    return false;
	}
	
	
	public static String runRace(Car car1, Car car2, Track track) {
	    car1.resetForNewRace();
	    car2.resetForNewRace();
	    int step = 1;
	    int c1Boost = 0;
	    int c2Boost = 0;
	    StringBuilder table = new StringBuilder();

	    table.append("\n[RACE PROGRESS]\n");
	    table.append("+-------+---------------------------------------+---------------------------------------+\n");
	    
	    String head1 = car1.getName();
	    while (head1.length() < 37) head1 = head1 + " ";
	    
	    String head2 = car2.getName();
	    while (head2.length() < 37) head2 = head2 + " ";
	    
	    table.append("| Step  | ").append(head1).append(" | ").append(head2).append(" |\n");
	    table.append("+-------+---------------------------------------+---------------------------------------+\n");

	    while (car1.getPosition() < 50 && car2.getPosition() < 50 && car1.getScore() > 0 && car2.getScore() > 0) 
	    {
	        String log1 = processTurn(car1, track, c1Boost);
	        if (log1.contains("Boost")) {
	            c1Boost++;
	        }
	        
	        String log2 = "                                     ";
	        if (car1.getPosition() < 50 && car1.getScore() > 0) {
	            log2 = processTurn(car2, track, c2Boost);
	            if (log2.contains("Boost")) {
	                c2Boost++;
	            }
	        }

	        String stepStr;
	        if (step < 10) {
	            stepStr = "0" + step;
	        } else {
	            stepStr = "" + step;
	        }

	        table.append("| ").append(stepStr).append("    | ").append(log1).append(" | ").append(log2).append(" |\n");
	        step++;
	    }
	    table.append("+-------+---------------------------------------+---------------------------------------+\n");
	    
	    return table.toString();
	}

	
	private static String processTurn(Car car, Track track, int boostCount) {
		
	    int startPos = car.getPosition();
	    int move = getRandomMove();
	    int midPos = startPos + move;
	    
	    if (midPos > 50) {
	        midPos = 50;
	    }
	    
	    car.setPosition(midPos);
	    applyMovementPenalty(car, move);

	    String startStr;
	    if (startPos < 10) {
	        startStr = "0" + startPos;
	    } else {
	        startStr = "" + startPos;
	    }

	    String midStr;
	    if (midPos < 10) {
	        midStr = "0" + midPos;
	    } else {
	        midStr = "" + midPos;
	    }

	    String output = "Pos " + startStr + " > " + midStr;

	    int unit = track.getUnitType(car.getPosition());
	    if (unit != 0) {
	        int finalPos = handleSpecialUnit(car, unit);
	        car.setPosition(finalPos);
	        
	        String finalStr;
	        if (finalPos < 10) {
	            finalStr = "0" + finalPos;
	        } else {
	            finalStr = "" + finalPos;
	        }

	        if (unit == 1) {
	            output = output + " (Boost " + (boostCount + 1) + ") > " + finalStr;
	        } else if (unit == 2) {
	            output = output + " (Reset Trap) > " + finalStr;
	        }
	    }

	    if (car.getPosition() >= 50) {
	        output = output + " (Winner)";
	    }

	    while (output.length() < 37) {
	        output = output + " ";
	    }

	    return output;
	}
	
	
}

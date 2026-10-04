package race_tournament_simulation;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
        SingleLinkedList carsList = new SingleLinkedList();
        SingleLinkedList tracksList = new SingleLinkedList();
        SingleLinkedList historyList = new SingleLinkedList();

        readCars(carsList);
        readTracks(tracksList);
        
        printHeader(6, 6); 
        displayCars(carsList);
        displayTracks(tracksList);
        
        
        //Race1a kısmısı    
        
        Scanner input = new Scanner(System.in);
                
        Track selectedTrack = null;
        while (selectedTrack == null) 
        {
            System.out.print("\n\nSelect a track (ID): ");
            int trackId = input.nextInt();
            
            Node tNode = tracksList.head;
            while (tNode != null) {
                Track t = (Track) tNode.getData();
                if (t.getId() == trackId) {
                    selectedTrack = t;
                    break;
                }
                tNode = tNode.getLink();
            }
            
            if (selectedTrack == null) {
                System.out.println("Invalid Track ID! Please try again.");
            }
        }       
        
        Car playerCar = null;
        while (playerCar == null) 
        {
            System.out.print("Select a car (ID): ");
            int carId = input.nextInt();

            Node cNode = carsList.head;
            while (cNode != null) {
                Car c = (Car) cNode.getData();
                if (c.getId() == carId) {
                    playerCar = c;
                    break;
                }
                cNode = cNode.getLink();
            }

            if (playerCar == null) {
                System.out.println("Invalid Car ID! Please try again.");
            }
        }
            
        Car opponentCar = Logic.getBestOpponent(playerCar, carsList, selectedTrack);
        playerCar.resetForNewRace();
        opponentCar.resetForNewRace();

        Logic.calculateInitialScore(playerCar, opponentCar, selectedTrack);
        Logic.calculateInitialScore(opponentCar, playerCar, selectedTrack);

        System.out.println("\n------- Race 1a: Player vs Computer -------");
        System.out.println("[PRE-RACE SELECTION]");
        System.out.println("> Selected Track  : [" + selectedTrack.getId() + "] " + selectedTrack.getName() + 
                " (" + selectedTrack.getType() + " | Boost: +" + selectedTrack.getBoost() + ")");
        System.out.println("> Player Car      : [" + playerCar.getId() + "] " + playerCar.getName());
        System.out.println("> Computer Car    : [" + opponentCar.getId() + "] " + opponentCar.getName());

        String table1a = Logic.runRace(playerCar, opponentCar, selectedTrack);
        System.out.print(table1a);

        int steps1a = table1a.split("\n").length - 6;

        Car winner1a = null;
        
        if (playerCar.getPosition() >= 50 || opponentCar.getScore() <= 0) {
            winner1a = playerCar;} 
        else {
            winner1a = opponentCar;}

        System.out.println("\nResult: " + winner1a.getName() + " wins Race 1a in " + steps1a + " steps!");
        
        String record1a = "[ ENTRY 1 ] Race 1a: Player vs Computer\n" +
                "            Track: " + selectedTrack.getName() + "\n" +
                "            Matchup: " + playerCar.getName() + " vs " + opponentCar.getName() + "\n" +
                "            Winner: " + winner1a.getName() + " (" + steps1a + " steps)\n" +
                "      |\n      V\n" + table1a;

        historyList.add(record1a); 
        
        tracksList.removeTrack(selectedTrack.getId());
        carsList.removeCar(playerCar.getId());
        carsList.removeCar(opponentCar.getId());

        System.out.println("Status: [" + selectedTrack.getId() + "] " + selectedTrack.getName() + " is no longer available.");
        
        
        //Race1b kısmısı          
        
        System.out.println("\n\n------- Race 1b: Computer vs Computer -------");

        Random rand = new Random();
        int randomTrackIdx = rand.nextInt(5); 
        
        Track track1b = null;
        Node tempT = tracksList.head;
        
        for (int i = 0; i < randomTrackIdx; i++) {
            tempT = tempT.getLink();
        }
        track1b = (Track) tempT.getData();

        int carIdx1 = rand.nextInt(4);
        int carIdx2;
        
        do {
            carIdx2 = rand.nextInt(4);
        } while (carIdx1 == carIdx2);

        Car compCarA = null;
        Car compCarB = null;

        Node tempC = carsList.head;
        for (int i = 0; i < carIdx1; i++) tempC = tempC.getLink();
        compCarA = (Car) tempC.getData();

        tempC = carsList.head;
        for (int i = 0; i < carIdx2; i++) tempC = tempC.getLink();
        compCarB = (Car) tempC.getData();

        System.out.println("[PRE-RACE SELECTION]");
        System.out.println("> Selected Track  : [" + track1b.getId() + "] " + track1b.getName() + 
                " (" + track1b.getType() + " | Boost: +" + track1b.getBoost() + ")");
        System.out.println("> Computer Car A  : [" + compCarA.getId() + "] " + compCarA.getName());
        System.out.println("> Computer Car B  : [" + compCarB.getId() + "] " + compCarB.getName());

        String table1b = Logic.runRace(compCarA, compCarB, track1b);
        System.out.print(table1b);

        int steps1b = table1b.split("\n").length - 6;

        Car winner1b = null;
        if (compCarA.getPosition() >= 50 || compCarB.getScore() <= 0) {
            winner1b = compCarA;} 
        else {
            winner1b = compCarB;}
            
        System.out.println("\nResult: " + winner1b.getName() + " wins Race 1b in " + steps1b + " steps!");
        
        String record1b = "[ ENTRY 2 ] Race 1b: Computer vs Computer\n" +
                "            Track: " + track1b.getName() + "\n" +
                "            Matchup: " + compCarA.getName() + " vs " + compCarB.getName() + "\n" +
                "            Winner: " + winner1b.getName() + " (" + steps1b + " steps)\n" +
                "      |\n      V\n" + table1b;

        historyList.add(record1b);

        tracksList.removeTrack(track1b.getId());
        carsList.removeCar(compCarA.getId());
        carsList.removeCar(compCarB.getId());
        
        System.out.println("Status: [" + track1b.getId() + "] " + track1b.getName() + " is no longer available.");
        
        
        //Final race kısmısı   
        
        System.out.println("\n==================================================================");
        System.out.println("    FINAL RACE");
        System.out.println("==================================================================");
        
        System.out.println("\n-- " + winner1a.getName() + " vs " + winner1b.getName() + " ----------------------------");

        int finalTrackIdx = rand.nextInt(4); 
        Track finalTrack = null;
        Node tempFinalT = tracksList.head;

        for (int i = 0; i < finalTrackIdx; i++) 
        {
            if (tempFinalT.getLink() != null) {
                tempFinalT = tempFinalT.getLink();
            }
        }
        finalTrack = (Track) tempFinalT.getData();

        System.out.println("> Selected Track  : [" + finalTrack.getId() + "] " + finalTrack.getName() + 
                           " (" + finalTrack.getType() + " | Boost: +" + finalTrack.getBoost() + ")");

        winner1a.resetForNewRace();
        winner1b.resetForNewRace();

        String tableFinal = Logic.runRace(winner1a, winner1b, finalTrack);
        System.out.print(tableFinal);

        int stepsFinal = tableFinal.split("\n").length - 6;
        
        Car champion = null;
        
        if (winner1a.getPosition() >= 50 || winner1b.getScore() <= 0) {
            champion = winner1a;} 
        else {
            champion = winner1b;}

        System.out.println("\nResult: " + champion.getName() + " wins the Final in " + stepsFinal + " steps!");
      
        String recordFinal = "[ ENTRY 3 ] Final Race\n" +
                "            Track: " + finalTrack.getName() + "\n" +
                "            Matchup: " + winner1a.getName() + " vs " + winner1b.getName() + "\n" +
                "            Winner: " + champion.getName() + " (" + stepsFinal + " steps)\n" +
                "      |\n      V\n" + tableFinal;
        
        historyList.add(recordFinal);
        
        System.out.println("\n\n========================================");
        System.out.println("          TOURNAMENT HISTORY            ");
        System.out.println("========================================");

        Node hNode = historyList.head;
        
        while (hNode != null) 
        {
            System.out.println(hNode.getData());
            System.out.println("========================================\n");
            hNode = hNode.getLink();
        }
        
        
        input.close();
    }

    
    //Dosya okuma kısmısı
    
    public static void readCars(SingleLinkedList list) throws Exception 
    {
        File file = new File("cars.txt"); 
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()) scanner.nextLine(); 

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            int c1 = line.indexOf(',');
            int c2 = line.indexOf(',', c1 + 1);
            int c3 = line.indexOf(',', c2 + 1);
            int c4 = line.indexOf(',', c3 + 1);
            int c5 = line.indexOf(',', c4 + 1);

            int id = customParseInt(line.substring(0, c1));
            String name = line.substring(c1 + 1, c2).trim();
            int power = customParseInt(line.substring(c2 + 1, c3));
            int control = customParseInt(line.substring(c3 + 1, c4));
            int speed = customParseInt(line.substring(c4 + 1, c5));
            String type = line.substring(c5 + 1).trim();

            int performanceScore = power + control + speed;
            Car car = new Car(id, name, performanceScore, type);
            list.sortedAdd(car); 
        }
        scanner.close();
    }
    

    public static void readTracks(SingleLinkedList list) throws Exception 
    {
        File file = new File("tracks.txt");
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()) scanner.nextLine(); 

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            int c1 = line.indexOf(',');
            int c2_next = line.indexOf(',', c1 + 1); 
            int c3 = line.indexOf(',', c2_next + 1); 
            int c4 = line.indexOf(',', c3 + 1);     

            int id = customParseInt(line.substring(0, c1));
            String name = line.substring(c1 + 1, c2_next).trim();
            String type = line.substring(c2_next + 1, c3).trim();
            int boost = customParseInt(line.substring(c4 + 1));

            Track track = new Track(id, name, type, boost);
            track.setSpecialUnits(); 
            list.add(track); 
        }
        scanner.close();
    }
    
    
    public static int customParseInt(String s) 
    {
        s = s.trim();
        int result = 0;
        
        for (int i = 0; i < s.length(); i++) 
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                result = result * 10 + (c - '0');
            }
        }
        return result;
    }
    
    
    //Yazdırma kısmısı
    
    public static void printHeader(int carCount, int trackCount)
    {
        System.out.println("==================================================================");
        System.out.println("                    RACE TOURNAMENT SIMULATOR                     ");
        System.out.println("==================================================================");
        System.out.println("\n[1] SYSTEM INITIALIZATION");
        System.out.println(" • Loading Car Data   ................................ [ OK ] " + carCount + " found");
        System.out.println(" • Loading Track Data ................................ [ OK ] " + trackCount + " found");
    }

    
    public static void displayCars(SingleLinkedList cars) 
    {
        System.out.println("\n[2] REGISTERED CARS (Ranked by Performance Score)");
        System.out.println("+-----+--------------+-------+----------+");
        System.out.println("| ID  | NAME         | SCORE | TYPE     |");
        System.out.println("+-----+--------------+-------+----------+");
        Node temp = cars.head;
        while (temp != null) {
            Car c = (Car) temp.getData();
            String name = c.getName();
            while (name.length() < 12) name += " ";
            String type = c.getType();
            while (type.length() < 8) type += " ";
            System.out.print("| " + c.getId() + " | ");
            System.out.print(name + " | ");
            System.out.print(c.getScore() + "    | "); 
            System.out.println(type + " |");
            temp = temp.getLink();
        }
        System.out.println("+-----+--------------+-------+----------+");
    }
    
    
    public static void displayTracks(SingleLinkedList tracks) 
    {
        System.out.println("\n[3] CURRENT TRACK LIST");
        Node temp = tracks.head;
        
        while (temp != null) 
        {
            Track t = (Track) temp.getData();
            System.out.print("[" + t.getId() + "] " + t.getName());
            if (temp.getLink() != null) System.out.print(" -> ");
            else System.out.println(" -> [END]");
            temp = temp.getLink();
        }
    }
    
      
    
}
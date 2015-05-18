/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitypoject8.graphs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author slvai_000
 */
public class UniversityPoject8Graphs {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Road> roads = new ArrayList<>();
    static Stack visited = new Stack();
    static ArrayList<Road> roadsToDest = null;
    static double lowestCost = 0;
    static double currentCost = 0;
    static int discount = 0;
    static ArrayList<String> visitedNames = new ArrayList<>();

    private static void input() {
        Scanner inFile;
        try {
            inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                String city1 = inFile.next();
                String city2 = inFile.next();
                String owner = inFile.next();
                double cost = inFile.nextInt();
                addNewRoad(city1, city2, owner, cost);
                addNewRoad(city2, city1, owner, cost);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Road.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        input();
        String start = "d";
        String destination = "a";
        String owner = "Titas";
        for (Road road : roads) {
            if (road.name.equals(start)) {
                transverse(road, destination, owner);
                if(roadsToDest == null){
                    System.out.println("Route doesn't exist");
                }else{
                    System.out.println(roadsToDest + " cost: " + lowestCost);
                }
                break;
            }
        }

    }
    
    private static void transverse(Road road, String destination, String owner) {
        String parent= road.name;
        visitedNames.add(road.name);
        visited.push(road);
        if (road.name.equals(destination)) {
            Stack newStack = (Stack) visited.clone();
            double finalCost = (discount>0) ? currentCost*0.5 : currentCost;
            if (lowestCost > finalCost || roadsToDest == null) {
                roadsToDest = new ArrayList<>(newStack);
                lowestCost = finalCost;
            }
        } else {
            while (road.next != null) {
                road = road.next;
                if (!visitedNames.contains(road.name)) {
                    for (Road newRoad : roads) {
                        if (road.name.equals(newRoad.name)) {
                            //System.out.println(parent+" "+road.name + road.cost+road.owner);  
                            if(owner.toLowerCase().equals(road.owner.toLowerCase())){
                                discount++;
                            }
                            
                            currentCost += road.cost;
                            transverse(newRoad, destination, owner);
                            currentCost += -road.cost;
                            
                            if(owner.toLowerCase().equals(road.owner.toLowerCase())){
                                discount--;
                            }                            
                            break;
                        }
                    }
                }
            }
        }
        visitedNames.remove(road.name);
        visited.pop();
    }

    private static void addNewRoad(String city1, String city2, String owner, double cost) {
        boolean contains = false;
        for (Road road : roads) {
            if (road.name.equals(city1)) {
                contains = true;
                while (road.next != null) {
                    if (road.name.equals(city2)) {
                        break;
                    }
                    road = road.next;
                }
                road.next = new Road(city2, cost, owner);
                break;
            }
        }

        Road newRoad;
        Road newRoad2;
        if (!contains) {
            newRoad = new Road(city1, 0, "");
            newRoad2 = new Road(city2, cost, owner);
            newRoad.next = newRoad2;
            roads.add(newRoad);
        }
    }
}

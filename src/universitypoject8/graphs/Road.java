/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitypoject8.graphs;

/**
 *
 * @author slvai_000
 */
public class Road {
    Road next = null;
    double cost = 0;
    String name = "";
    String owner;
    
    @Override
    public String toString(){
      //  String nextString = next==null? "" : next.toString();
        return name;//+nextString;
    }

    public Road(String newName, double newCost, String newOwner) {
        cost=newCost;
        name=newName;
        owner=newOwner;
    }
    
}

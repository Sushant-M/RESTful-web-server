/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

import java.util.*;

public class Rover {
    /* The rover will have variables to keep track of certain things,
       It's name, the last time received and the Stack of times it's
       received since the communication began.
    */
    private int ID;
    private String name;
    private ArrayDeque<Integer> stack;

    //Constructor
    public Rover(int ID, String name){
        this.ID = ID;
        this.name = name;
        stack = new ArrayDeque<Integer>();
    }

    //Setters and getters
    public int getID(){return this.ID;}

    public String getName(){return this.name;}

    public void writeData(int time){
        stack.add(time);
    }

    public int getTime(){
        /*If the set stack is empty it means that this is the first message
          message that we have received, so send 0.
         */
        if(stack.isEmpty()){
            return 0;
        }else {
            //Send the last message in the stack.
            return stack.getLast();
        }
    }
}

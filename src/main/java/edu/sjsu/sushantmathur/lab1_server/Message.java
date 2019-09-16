/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

/* This is the message we receive from the Rover(client)
*  It is the same as Response Message as of now, could change
*  later in future assignments.
* */

public class Message {
    private int id;
    private int time;

    public Message(){}

    public Message(int id, int time){
        this.id = id;
        this.time = time;
    }

    public int getID(){return this.id;}
    public int getTime(){return this.time;}

    public void setID(int ID) {
        this.id = ID;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

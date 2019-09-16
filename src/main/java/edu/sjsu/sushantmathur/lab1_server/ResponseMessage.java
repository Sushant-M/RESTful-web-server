/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

/* The Response Message that will be sent to the
*  Rover
* */

public class ResponseMessage {
    private int id;
    private int time;
    ResponseMessage(int id, int time){
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

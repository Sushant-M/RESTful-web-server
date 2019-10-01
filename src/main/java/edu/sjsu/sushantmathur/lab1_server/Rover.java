/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

import java.util.*;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

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

    public void writeData(int time, int xray, int sunlight, int emr, MongoCollection db){
        Document document = new Document();
        document.put("roverid", this.ID);
        document.put("time", time);
        document.put("xray", xray);
        document.put("sunlight", sunlight);
        document.put("emr", emr);
        db.insertOne(document);
    }

    public void deleteData(int time, MongoCollection dbCollection){
        BasicDBObject object = new BasicDBObject();
        object.put("time",time);
        dbCollection.deleteOne(object);
    }

    public Message getForTime(MongoCollection dbCollection, int time){
        BasicDBObject object = new BasicDBObject();
        object.put("time", time);
        Document document = (Document) dbCollection.find(eq("time", time)).first();
        return getMessage(document);
    }

    public Message modifyForTime(MongoCollection dbCollection, int time, int sun){
        //BasicDBObject object = new BasicDBObject();
        Document document = (Document) dbCollection.find(eq("time", time)).first();
        document.put("sunlight", sun);
        dbCollection.replaceOne(Filters.eq("time", time), document);
        return getMessage(document);
    }

    private Message getMessage(Document document) {
        int rid = (int) document.get("roverid");
        int time_send = (int) document.get("time");
        int xray_send = (int)document.get("xray");
        int emr_send = (int)document.get("emr");
        int sunlight_send = (int)document.get("sunlight");
        return new Message(rid, time_send, xray_send,sunlight_send,emr_send);
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

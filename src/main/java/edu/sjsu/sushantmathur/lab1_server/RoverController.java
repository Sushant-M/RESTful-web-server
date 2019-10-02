/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoverController {
    /*Initialise 2 objects for each rover that we are
      communicating with and set their names.
     */
    public Rover rover1;
    public Rover rover2;
    MongoCollection<Document> rover1Col;
    MongoCollection<Document> rover2Col;
    RoverController(){
        rover1 = new Rover(1, "Spirit");
        rover2 = new Rover(2, "Opportunity");
        MongoClient mongo = MongoClients.create("mongodb+srv://admin:admin@sushant-lzxz7.mongodb.net/test?retryWrites=true&w=majority");
        MongoDatabase database = mongo.getDatabase("Server");
        rover1Col = database.getCollection("client1data");
        rover2Col = database.getCollection("client2data");
    }

    //Create
    @PostMapping(value = "/update" , consumes = "application/json")
    public ResponseEntity<ResponseMessage> replyRover(@RequestBody Message message){
        if(message.getID() == 1){
            System.out.println(message.getTime() +" " + message.getXray()+ " " + message.getSunlight());
            rover1.writeData(message.getTime(), message.getXray(), message.getSunlight(), message.getEmr(), rover1Col);
        }else if (message.getID() == 2){
            rover2.writeData(message.getTime(), message.getXray(), message.getSunlight(), message.getEmr(), rover2Col);
        }
        ResponseMessage responseMessage = new ResponseMessage(message.getID(), 1);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }


    //Delete
    @DeleteMapping(value = "delete/{id}/{time}")
    public ResponseEntity<Long> deleteEntry(@PathVariable int id, @PathVariable int time){
        if(id == 1){
            rover1.deleteData(time, rover1Col);
        }
        else if(id==2){
            rover2.deleteData(time, rover2Col);
        }
        return new ResponseEntity<Long>((long) id, HttpStatus.OK);
    }

    //Get
    @GetMapping(value = "get/{id}/{time}")
    public ResponseEntity<Message> getEntry(@PathVariable int id, @PathVariable int time){
        Message msg = null;
        if(id==1){
            msg = rover1.getForTime(rover1Col, time);
        }else if(id==2){
            msg = rover2.getForTime(rover2Col, time);
        }
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }

    //Modify
    @PutMapping(value = "modify/{id}/{time}/{sun}")
    public ResponseEntity<Message> putEntry(@PathVariable int id, @PathVariable int time, @PathVariable int sun){
        Message msg = null;
        if(id==1){
            msg = rover1.modifyForTime(rover1Col, time, sun);
        }else if(id==2){
            msg = rover2.modifyForTime(rover2Col, time, sun);
        }
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }
}

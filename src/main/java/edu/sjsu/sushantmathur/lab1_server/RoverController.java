/* @Author: Sushant Amit Mathur. SJSU ID: 014489865*/
package edu.sjsu.sushantmathur.lab1_server;

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
    RoverController(){
        rover1 = new Rover(1, "Spirit");
        rover2 = new Rover(2, "Opportunity");
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseMessage> replyRover(@RequestBody Message message){
        /*Initialise previous time variable, this will be sent back to the rover,
          this is the previous time we received. If it's the first message that
          we have received then zero will be sent back. The getTime() method of
          class Rover handles the same.
         */
        int prevTime=0;
        if(message.getID() == 1){
            //In case it's rover 1, get it's previous time and send it back
            prevTime = rover1.getTime();
            rover1.writeData(message.getTime());
        }else if (message.getID() == 2){
            //In case it's rover 2, get it's previous time and send it back
            prevTime = rover2.getTime();
            rover2.writeData(message.getTime());
        }
        /*Generate the response message of form ResponseMessage, this is equal to
          the RoverData message on the rover(client) end. Return the same.
        */
        ResponseMessage responseMessage = new ResponseMessage(message.getID(), prevTime);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
}

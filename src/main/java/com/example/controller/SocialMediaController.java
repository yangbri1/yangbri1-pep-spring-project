package com.example.controller;

// ref: Week 9, "Spring Rest Controller" Coding Lab
// import com.example.entity.*;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.*;

// import both 'Account.java' & 'Message.java' classes from 'entity' folder
// ref: Week 9, "Spring Response Entity" Coding Lab --- 'Controller.java'
import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

// import javax.validation.Valid;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 // ref: Week 9, "Spring Rest Controller" Coding Lab
 /**
 * A RESTController allows for the creation of endpoints that will, by default, allow the developer to easily follow
 * RESTful conventions, such as the descriptive use of HTTP verbs (get, post, put, patch, delete), and will also
 * automatically convert variables returned from the endpoint's handler to a JSON response body, which was previously
 * done by including the @ResponseBody annotation.
 */
@RestController     /* Aside: '@RestController' annotation introduced in v4 is a combination of '@RequestBody' & '@Controller' (@RequestBody by default) */
public class SocialMediaController {

    /* Spring Model-View-Controller (MVC)'s @RestController annotation allows mappings: 
    * @GetMapping, @PostMapping, @PutMapping, @PatchMapping & @DeleteMapping
    */
    
    // this step is not required --- just for quality of life -- Thunder-Client/Postman testing
    /* HTTP GET method request to localhost:8080/accounts will yield with a JSON response body listing all of the account entities in 'Account' DB table 
     * {
     *  ...
     * }
    */

    @Autowired
    private AccountRepository accountRepository;        // to be able to use built-in basic CRUD operations from JpaRepository class
    @Autowired
    private AccountService accountService;              // declare instance variable 'accountRepository' to use custom methods, fields, etc.

    // @GetMapping("/accounts")               
    // List<Account> getAllAccounts(){            // EXPERIMENTING!! --- This is not a constructor 
    //     // return new Account();               // This is NOT a constructor in 'Account.java' class -- rather a method in 'AccountService.java' class
    //     return accountRepository.findAll();
    // }

    @GetMapping("/accounts")
    /* generics <> used here as ResponseEntity is a raw type to reference to generic type */
    public ResponseEntity<List<Account>> getAllAccounts(){  // (@RequestBody Account account){
        List<Account> allAccounts = accountRepository.findAll();
        // return a ResponseEntity w/ custom response status code 200 & response body containing 'Account' obj
        // return ResponseEntity.status(200).body(account);    // .body("Extraction Complete");
        return ResponseEntity.ok(allAccounts); // .ok() == HTTP status code 200
    }

    // ## 1: Our API should be able to process new User registrations.
    // ref: Week 9 Day 4, Lecture: Restful Api Development With @RestController Annotation 
    /* Aside: 'ResponseEntity' is more flexible which allows custom HTTP status code & headers (content-type, etc.) ...
    * Meanwhile, '@ResponseBody' -- does NOT allow customized HTTP status code */
    @PostMapping("/register")
    /* generics <> used here as ResponseEntity is a raw type to reference to generic type */
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        // using try-catch block for exception handling ...
        try{
            // invoke .addAccount() method from 'AccountService' class in an attempt to create/register account entity to DB table
            Account addedAccount = accountService.addAccount(account);
            // if provided request body's JSON string is valid ...
            // display the account & an HTTP status of 'OK' (200)
            return new ResponseEntity<>(addedAccount, HttpStatus.OK);
        }
        // for any errors that were thrown ...
        catch(Exception e){
            // if it was a duplicate 'username' detected in 'Account' DB table exception ...
            if(e.getMessage().equals("Sorry for the inconvenience --- Username already claimed")){
                // return a HTTP response status of 'CONFLICT' (409) w/ an empty response body (req. to fit 'ResponseEntity<Account>' datatype mould)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            // return custom HTTP status code 400 (client error) for other unsuccessful attempts passing validation rules
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ## 2: Our API should be able to process User logins.
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccountGetAccId(@RequestBody Account account) throws Exception{
        // invoke .loginAccountGetAccId() method from 'accountService' class to evaluate whether or not login was successful
        // Account loginAccount = accountService.loginAccountGetAccId(account);
        // // under the impression that login failed ...
        // if(loginAccount == null){
        //     // return HTTP status code 401 (Unauthorized)
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        // }
        // // otw if login was a success, respond w/ an HTTP status code 200 (OK) & whole 'Account' obj info
        // return ResponseEntity.ok(loginAccount);
        /* use try-catch to handle Unhandled exceptions --- yielding HTTP response status code 500 that was overwriting the custom 401 (Unauthorized) code*/
        try {
            // call .loginAccountGetAccId() method from 'AccountService' class in an attempt to login
            Account loginAccount = accountService.loginAccountGetAccId(account);
            // return response body w/ status code of 200 (success/OK) along w/ logged in info JSON String
            return ResponseEntity.ok(loginAccount);
        } 
        // otw if login fails ...
        catch (Exception e) {
            // showcase custom unauthorized HTTP status code (401) w/ no body response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    // ## 3: Our API should be able to process the creation of new messages.
    // @PostMapping(value = "/messages")    // Note: Method wishes a return type of 'Message'
    // public Message createMessage(@RequestBody Message pojo){
    //     // you will need to change the method's parameters and return the extracted request body.
    //     return pojo;
    // }

    @Autowired
    private MessageService messageService;

    // ## 3: Our API should be able to process the creation of new messages.
    // @PostMapping("/messages")
    // /* generics <> used here as ResponseEntity is a raw type to reference to generic type */
    // public ResponseEntity<Message> createMessage(@RequestBody Message message){
    //     // using try-catch block for exception handling ...
    //     try{
    //         // invoke .addMessage() method from 'MessageService' class in an attempt to persist/create message entity to DB table
    //         Message addedMessage = messageService.addMessage(message);
    //         // if provided request body's JSON string is valid ...
    //         // display the 'addedMessage' & an HTTP status of 'OK' (200)
    //         return new ResponseEntity<>(addedMessage, HttpStatus.OK);
    //         // return ResponseEntity.ok(addedMessage);
    //     }
    //     // for any errors that were thrown ...
    //     catch(Exception e){
    //         // if it was a duplicate 'username' detected in 'Account' DB table exception ...
    //         if(e.getMessage().equals("Sorry for the inconvenience --- Username already claimed")){
    //             // return a HTTP response status of 'CONFLICT' (409) w/ an empty response body (req. to fit 'ResponseEntity<Account>' datatype mould)
    //             return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    //         }
    //         // return custom HTTP status code 'HttpStatus.BAD_REQUEST' (400 - client error) for unsuccessful attempts passing validation rules
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //         // return ResponseEntity.badRequest().body(null);
    //     }
    // }

    // @PostMapping("/messages")
    // public ResponseEntity<Message> newMessage(
    //     @RequestBody int posted_by, 
    //     @RequestBody String message_text,
    //     @RequestBody long time_posted_epoch){
    //     Optional<Message> messageOptional = messageService.newMessage(posted_by, message_text, time_posted_epoch);
    //     if(messageOptional.isPresent()){
    //         return ResponseEntity.status(HttpStatus.OK).body(messageOptional.get());
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }
    // }
    // @PostMapping("messages")
    // public ResponseEntity<Message> newMessage(@RequestBody Message message){
    //     Optional<Message> optionalMessage = messageService.newMessage(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());
    //     if(optionalMessage.isPresent()){
    //         return ResponseEntity.status(HttpStatus.OK).body(optionalMessage.get());
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }
    // }

    // @PostMapping("/messages")
    // public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
    //     try {
    //         Message createdMessage = messageService.addMessage(msg);
    //         return new ResponseEntity<>(createdMessage, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     }
    // }
    // @PostMapping("/messages")
    // public ResponseEntity<Message> newMessage(@RequestBody Message message) throws Exception{
    //     try {
    //         Message goodMessage = messageService.addMessage(message);
    //         return ResponseEntity.ok(goodMessage);
    //     } 
    //     catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     }
    // }
    // @PostMapping("/messages")
    // public ResponseEntity<Message> newMessage(@RequestBody Message message, BindingResult result) {
    //     if (result.hasErrors()) {
    //         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     }

    //     try {
    //         Message goodMessage = messageService.addMessage(message);
    //         return ResponseEntity.ok(goodMessage);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     }
    // }
    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message) {
        // try-catch block for error handling the Unhandled Exceptions (500) 
        try {
            // attempt to add new message via .addMessage() method
            Message msg = messageService.addMessage(message);
            // return newly added message w/ status code 200 (OK)
            return ResponseEntity.ok(msg);
            // catch any thrown Exceptions ...
        } catch (Exception e) {
            // If any exception occurs, return a 400 HTTP response code (Bad Request)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // @PostMapping("/messages")
    // public ResponseEntity<Message> createMessage(
    //     @RequestBody Message message) throws Exception{      // '@RequestBody' annotation allows custom datatypes; data to travel as JSON String, XML, instead of as Path Variable
    //     /* 'throws Exception' to combat the 'Unhandled exception type Exception' */
    //     try{
    //         Message addedMessage = messageService.addMessage(message);
    //         // return HTTP status code 200 to be idempotent regardless if an actual entity was removed or not from 'Message' DB table & result from this deletion operation
    //         return ResponseEntity.ok(addedMessage); 
    //         // another way would be to split this up into an if-else flow control statement where if condition 'entityNumchanged == 1' run above, otw if else run above w/ .build()[no body for response entity] concatenated afterwards
    //         // return new ResponseEntity<>(HttpStatus.OK);
    //     }
    //     catch(Exception e){
    //         // e.getMessage();
    //         // return new ResponseEntity.status(400).body(null);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }

    //     // // invoke .loginAccountGetAccId() method from 'accountService' class to evaluate whether or not login was successful
    //     // Message createMsg = messageService.addMessage(message);
    //     // // under the impression that login failed ...
    //     // if(createMsg == null){
    //     //     // return HTTP status code 400 (Client-side error)
    //     //     return ResponseEntity.status(401).body(null);
    //     // }
    //     // // otw if login was a success, respond w/ an HTTP status code 200 (OK) & whole 'Account' obj info
    //     // return ResponseEntity.ok(createMsg);
    // }


    @Autowired
    private MessageRepository messageRepository;

    // ## 4: Our API should be able to retrieve all messages.
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){ 
        // List<Message> messages = messageService.findAll();
        /* utilize built-in .findAll() method from 'MessageRepository.java' class 
        (which in hand 'extends' from Spring framework's JPARepository --- basic CRUD operations included) */
        List<Message> allMessages = messageRepository.findAll();
        // '.ok()' method always yields response status of 200
        return ResponseEntity.ok(allMessages);
    }

    // ## 5: Our API should be able to retrieve a message by its ID.
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByMsgId(@PathVariable Integer messageId){
        // Before use: arg need to be of 'Long' Complex object data type & change generic <> in JpaRepository<Message, Long> --- may NOT work
        // Message msgByMsgId = messageRepository.findById(msgId); 
        Message message = messageService.findBymessageId(messageId);
        return ResponseEntity.ok(message);
    }

    // ## 6: Our API should be able to delete a message identified by a message ID.
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMsgId(@PathVariable Integer messageId){
        /* Before use: arg need to be of 'Integer' Complex object datatype matching what was initialized in 'Message.java' entity for 'messageId' ('Message' @Id) JpaRepository to <Message, Integer> 
        -- otw different datatypes across the Entity -> Service -> Controller layers will cause extra complications! */
        // Message msgByMsgId = messageRepository.findById(msgId); 
        Integer entityNumChanged = messageService.deleteBymessageId(messageId);
        // return HTTP status code 200 to be idempotent regardless if an actual entity was removed or not from 'Message' DB table & result from this deletion operation
        return ResponseEntity.ok(entityNumChanged); 
        // another way would be to split this up into an if-else flow control statement where if condition 'entityNumchanged == 1' run above, otw if else run above w/ .build()[no body for response entity] concatenated afterwards
        // return new ResponseEntity<>(HttpStatus.OK);
    }

    // ## 7: Our API should be able to update a message text identified by a message ID.
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMsgId(
        @PathVariable Integer messageId,    // '@PathVariable' annotation will bind to simpler datatypes like String, Integer, Long, etc. from Path URL (not custom objects ex. 'Message', 'Account')
        @RequestBody Message message){      // '@RequestBody' annotation allows custom datatypes; data to travel as JSON String, XML, instead of as Path Variable
        try{
            Integer entityNumChanged = messageService.updateBymessageId(messageId, message);
            // return HTTP status code 200 to be idempotent regardless if an actual entity was removed or not from 'Message' DB table & result from this deletion operation
            return ResponseEntity.ok(entityNumChanged); 
            // another way would be to split this up into an if-else flow control statement where if condition 'entityNumchanged == 1' run above, otw if else run above w/ .build()[no body for response entity] concatenated afterwards
            // return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            // e.getMessage();
            // return new ResponseEntity.status(400).body(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ## 8: Our API should be able to retrieve all messages written by a particular user.
    // @GetMapping("/accounts/{accountId}/messages")
    // public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable Integer accountId){
    //     // List<Message> messages = messageService.findAll();
    //     /* utilize built-in .findAll() method from 'MessageRepository.java' class 
    //     (which in hand 'extends' from Spring framework's JPARepository --- basic CRUD operations included) */
    //     List<Message> allMessages = messageRepository.findAllMessagesByAccountId(accountId);
    //     // '.ok()' method always yields response status of 200
    //     return ResponseEntity.ok(allMessages);
    // }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserAccountId(@PathVariable("accountId") Integer accountId){
        // gather up all the messages tied to this user's 'accountId'
        List<Message> messageList = messageService.getAllMessagesByUserId(accountId);
        // output collected data w/ an response status code of 200 (OK)
        return ResponseEntity.ok(messageList);
    }
}

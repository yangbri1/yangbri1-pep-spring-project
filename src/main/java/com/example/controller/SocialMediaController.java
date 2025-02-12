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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private AccountRepository accountRepository;

    // @GetMapping("/accounts")               
    // List<Account> getAllAccounts(){        // EXPERIMENTING!! --- This is not a constructor 
    //     // return new Account();               // This is NOT a constructor in 'Account.java' class -- rather a method in 'AccountService.java' class
    //     return accountRepository.findAll();
    // }

    // @GetMapping("/accounts")
    // /* generics <> used here as ResponseEntity is a raw type to reference to generic type */
    // public ResponseEntity<List<Account>> getAllAccounts(){  // (@RequestBody Account account){
    //     // return a ResponseEntity w/ custom response status code 200 & response body containing 'Account' obj
    //     // return ResponseEntity.status(200).body(account);    // .body("Extraction Complete");
    //     return ResponseEntity.ok(accounts);
    // }

    
    // ## 3: Our API should be able to process the creation of new messages.
    // @PostMapping(value = "/messages")    // Note: Method wishes a return type of 'Message'
    // public Message createMessage(@RequestBody Message pojo){
    //     // you will need to change the method's parameters and return the extracted request body.
    //     return pojo;
    // }

    @Autowired
    private MessageService messageService;

    // ## 3: Our API should be able to process the creation of new messages.
    // ref: Week 9 Day 4, Lecture: Restful Api Development With @RestController Annotation 
    // @PostMapping(value = "/messages")
    // /* Aside: 'ResponseEntity' is more flexible which allows custom HTTP status code & headers (content-type, etc.) ...
    // * Meanwhile, '@ResponseBody' -- does NOT allow customized HTTP status code */
    // public ResponseEntity<Message> createMessage(@RequestBody Message msg){
    //     // try {
    //     //     Message createdMessage = messageService.createMessage(msg);
    //     //     return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    //     // } catch (Exception e) {
    //     //     return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     // }
    //     // populate 'message' object w/ JSON request body fields
    //     messageService.createMessage(msg);
    //     // if 'msg' fails ...
    //     if(msg.equals(null)){
    //         // return custom HTTP status code 400 (client error)
    //         return ResponseEntity.status(400).body(msg);
    //     }
    //     // otherwise if provided request body's JSON string is valid ...
    //     else{
    //         // return response status of 'HttpStatus' (200) alongside respective msg in response body
    //         return ResponseEntity.status(HttpStatus.OK).body(msg);
    //     }
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
    // @GetMapping("/messages/{messageId}")
    // public ResponseEntity<Message> getMessageByMsgId(int msgId){
    //     // Before use: arg need to be of 'Long' Complex object data type & change generic <> in JpaRepository<Message, Long> --- may NOT work
    //     // Message msgByMsgId = messageRepository.findById(msgId); 
    //     Message msgByMsgId = messageService.
    //     return ResponseEntity.
    // }

}

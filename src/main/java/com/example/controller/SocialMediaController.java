package com.example.controller;

// ref: Week 9, "Spring Rest Controller" Coding Lab
// import com.example.entity.*;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.*;

// import both 'Account.java' & 'Message.java' classes from 'entity' folder
// ref: Week 9, "Spring Response Entity" Coding Lab --- 'Controller.java'
import com.example.entity.*;
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
@RestController     
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
    @GetMapping("/accounts/")               
    public Account getAllAccounts(){        // EXPERIMENTING!! --- This is not a constructor 
        return new Account();               // This is NOT a constructor in 'Account.java' class -- rather a method in 'AccountService.java' class
    }

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

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
        try {
            Message createdMessage = messageService.createMessage(msg);
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // @GetMapping("accounts/{accountId}/messages")
    // public 
}

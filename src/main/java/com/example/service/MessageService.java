package com.example.service;

// import 'Message.java' & 'MessageRepository.java' classes
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

// import 'List' & 'Optional' classes from Java's Utility package to use built-in Java methods
import java.util.List;
// 
import java.util.Optional;

/* This 'MessageService' class contains method to retrieve & manipulate entities (records) related to 'Message' entity.
 * Give 'Message.java' & 'MessageRepository.java' a look for relationship
 */
// ref: Week 10, "Spring JPA Multiplicity" Coding Lab -- 'StudentService.java'

@Service
@Transactional
public class MessageService {
    // declare an instance variable 'messageRepository'
    MessageRepository messageRepository;
    
    /* 'MessageRepository' has been autowired into this class via "Constructor" injection ('@Autowired annotation')
     * Aside: Different types of (Spring) Dependency Injection ... 
     * 1) Constructor Injection (of mandatory dependencies), 2) Setter Injection (of optional), 3) Field Injection
     * ... rated by flexibility, efficiency, & popularity.
     */
    // @Autowired
    // // constructor instantiating above instance variable 'MessageRepository' by taking in an 'MessageRepository' obj
    // /* introduce 'MessageRepository' obj in 'MessageService' obj -- enables 'AccountService' class to use 'MessageRepository' functionalities */
    // public MessageService(MessageRepository messageRepository){
    //     /* reserved keyword 'this' points to earlier declared instance variable (outside this constructor) ...
    //     & assign given parameter 'messageRepository' value to it */
    //     this.messageRepository = messageRepository;
    // }

    // ## 3: Our API should be able to process the creation of new messages.
    /* The creation of the message will be successful if and only if the message_text is
    (1) is not blank, 
    (2) is not over 255 characters, 
    (3) and 'postedBy' refers to a real, existing user. */
    /* Side Note: Besides performing custom validation here in the Service Layer ('MessageService.java' class) ...
     *            Custom validation normally done within the entity class ('Message.java') but since told NOT to modify it ... here
     */
    // carry out custom validation on arg's 'msg' & if ALL mentioned conditions are fulfulled, .save() entity to 'Account' database
    // public Message createMessage(Message msg){
    //     // assign 'messageText' String value to String variable 'message'
    //     String message = msg.getMessageText();
    //     // assign 'postedBy' int value to int variable 'userId' ...
    //     // (as 'postedBy' is a FOREIGN KEY REFERENCES-ing to PRIMARY KEY 'accountId' in 'Account' table)
    //     int userId = msg.getPostedBy();

    //     // if 'messageText' field is empty ...
    //     /* double check if message String was empty using both built-in 
    //     1) String.isEmpty() & 2) equality operator (==) (probably should've used .equals()) */
    //     if(message.isEmpty() || message == ""){  
    //         // return falsy value
    //         return null;    // throw new [SomeCustomException("Message field is empty, please type something!")]
    //     }
    //     // else if message exceeds 255 char limit
    //     if(message.length() > 255){
    //         // return falsy value
    //         return null;    // throw new [SomeOtherCustomException(" ... ")]
    //     }
    //     // else if message's 'posted_by' is NOT FOUND as an 'accountId' in DB table ...
    //     if(!messageRepository.existsById((long) userId)){ // || message == null){
    //         // indicate so
    //         return null;    // throw new [AnotherException("Exception msg ...")]
    //     }
    //     // otw if all above verifications are abided, CREATE message
    //     // invoke .save() method from Spring framework 'CRUDRepository' package to persist (create) a 'msg' entity (record) in 'Message' DB table
    //     return messageRepository.save(msg); // .save() to save parameter's 'arg' to 'Message' entity    // .create()?
    // }

    // ## 4: Our API should be able to retrieve all messages.
    public List<Message> getAllMessages(){
        // employ built-in .findAll() method from CrudRepository (extends from JpaRepository) to find all 'Message' object entities/records in 'Message' DB table
        return messageRepository.findAll();
    }

    // ## 5: Our API should be able to retrieve a message by its 'messageId' ('msgId' is a placeholder --- can be any name).
    // public Message findMessageByMessageId(int msgId){
    //     // call .findById() method to retrieve 'Message' entity/record from DB fitting given parameter 'msgId'
    //     Optional<Message> messageOptional = messageRepository.findById((long) msgId);
    //     // if value assigned to 'messageOptional' container obj is of a non-null nature ...
    //     if(messageOptional.isPresent()){
    //         // grab the value via .get() method from 'java.util.Optional' package
    //         Message message = messageOptional.get();
    //         // return 'Message' entity
    //         return message;     // return message.getMessageText();
    //     }
    //     // otw if the value from 'messageOptional' is indeed NULL ... return default falsy value (null)
    //     return null;

    //     // List<Message> allMsgsByUserId =  messageRepository.findAllById((long) msgId);
    //     // return allMsgsByUserId;

    //     // call on .findMessageByMessageId() from 'MessageRepository.java' class --- Spring JPA "infers" (not really) SQL query from its method name back in 'MessageRepository.java' class
    //     // return messageRepository.findMessageByMessageId(msgId);
    // }

    // ## 6: Our API should be able to delete a message identified by a message ID.
}

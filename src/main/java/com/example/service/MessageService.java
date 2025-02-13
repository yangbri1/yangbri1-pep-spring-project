package com.example.service;

// import 'Message.java' & 'MessageRepository.java' classes
import com.example.entity.Account;
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

// import com.example.exception.ClientErrorException;

/* This 'MessageService' class contains method to retrieve & manipulate entities (records) related to 'Message' entity.
 * Give 'Message.java' & 'MessageRepository.java' a look for relationship
 */
// ref: Week 10, "Spring JPA Multiplicity" Coding Lab -- 'StudentService.java'

@Service
@Transactional
public class MessageService {
    // declare an instance variable 'messageRepository'
    MessageRepository messageRepository;

    // declare an instance variable 'accountRepository'
    @Autowired
    AccountRepository accountRepository;
    
    /* 'MessageRepository' has been autowired into this class via "Constructor" injection ('@Autowired annotation')
     * Aside: Different types of (Spring) Dependency Injection ... 
     * 1) Constructor Injection (of mandatory dependencies), 2) Setter Injection (of optional), 3) Field Injection
     * ... rated by flexibility, efficiency, & popularity.
     */
    @Autowired
    // constructor instantiating above instance variable 'MessageRepository' by taking in an 'MessageRepository' obj
    /* introduce 'MessageRepository' obj in 'MessageService' obj -- enables 'AccountService' class to use 'MessageRepository' functionalities */
    public MessageService(MessageRepository messageRepository){
        /* reserved keyword 'this' points to earlier declared instance variable (outside this constructor) ...
        & assign given parameter 'messageRepository' value to it */
        this.messageRepository = messageRepository;
    }

    // ## 3: Our API should be able to process the creation of new messages.
    /* The creation of the message will be successful if and only if the message_text is
    (1) is not blank, 
    (2) is not over 255 characters, 
    (3) and 'postedBy' refers to a real, existing user. */
    /* Side Note: Besides performing custom validation here in the Service Layer ('MessageService.java' class) ...
     *            Custom validation normally done within the entity class ('Message.java') but since told NOT to modify it ... here
     */
    // carry out custom validation on arg's 'msg' & if ALL mentioned conditions are fulfulled, .save() entity to 'Account' database
    // public Message addMessage(Message msg) throws Exception{
    //     // assign 'messageText' String value to String variable 'msgText'
    //     String msgText = msg.getMessageText();
    //     // assign 'postedBy' int value to int variable 'userId' ...
    //     // (as 'postedBy' is a FOREIGN KEY REFERENCES-ing to PRIMARY KEY 'accountId' in 'Account' table)
    //     int userId = msg.getPostedBy();

    //     /* --------- 'Message' entity constraints --------- */
    //     // if 'messageText' field is empty ...
    //     /* double check if message String was empty using both built-in 
    //     1) String.isEmpty() & 2) equality operator (==) (probably should've used .equals()) */
    //     if(msgText.isBlank()){  // message.isEmpty() || message == ""){  
    //         // prod user to type something for 'messageText'
    //         throw new Exception("Message field is blank, please leave a message");
    //     }
    //     // else if message exceeds 255 char limit
    //     else if(msgText.length() > 255){
    //         // notify user of 'messageText' field's constraint
    //         throw new Exception("Message exceeds 255 character limit ...");
    //     }
    //     // else if message's 'postedBy' was NOT FOUND as an 'accountId' in 'Account' DB table ...
    //     // Aside: Might be risky using built-in Spring framework's .existById() method on messageRepository b/c there's 'messageId' & 'postedBy' (FOREIGN KEY to 'accountId' in 'Account' table)
    //     else if(!accountRepository.existsById(userId)){ // || message == null){
    //     // else if(messageRepository.existsByPostedBy(userId) == false){    // this one DN work
    //         // indicate so
    //         throw new Exception(userId + " is invalid");
    //     }
    //     /* --------- End of 'Message' entity constraints / Start 'Account' entity check --------- */
    //     // implement Spring JpaRepository's built-in .findById() method to check if the 'Message' entity/record is tied back to a legit account
    //     Optional<Account> accountOptional = accountRepository.findById(msg.getPostedBy());
    //     if(accountOptional.isPresent()){
    //         // Account account = accountOptional.get();
    //         Message message = new Message();
    //         message.setPostedBy(msg.getPostedBy());
    //         message.setMessageText(msgText);
    //         // ref. https://stackoverflow.com/questions/2010284/how-to-get-the-current-date-and-time
    //         message.setTimePostedEpoch(System.currentTimeMillis());
    //     }
    //     // otw if all above verifications are abided, CREATE message
    //     // invoke .save() method from Spring framework 'CRUDRepository' package to persist (create) a 'msg' entity (record) in 'Message' DB table
    //     return messageRepository.save(msg); // .save() to save parameter's 'arg' to 'Message' entity    // .create()?
    // }
    
    public Message addMessage(Message msg) throws Exception{
        // initialize 'msgText' String variable w/ 'msg' messageText value
        String msgText = msg.getMessageText();
        // if 'msgText' field does not contain anything ...
        if(msgText == "" || msgText.isEmpty()){     // msgText.isBlank()
            throw new Exception("msgText is barren");
        }
        // else if 'msgText' is above the character limit ...
        if (msgText.length() > 255){
            throw new Exception("msgText waaaay toooo loooooong");
        }
        // else if 'messageId' DNE ...
        if(!messageRepository.existsById(msg.getPostedBy())){               /* EQUIVALENT to 'if(messageOptional.isEmpty()){...}' logic-wise, req. Optional to be declare earlier*/
            throw new Exception("postedBy comes out clutch --- linked w/ a real dude!");           
        }
        
        // call .findById() method to retrieve 'Message' record from DB fitting given parameter 'messageId'
        Optional<Message> messageOptional = messageRepository.findById(msg.getMessageId());
        // if value assigned to 'messageOptional' container obj's value is NON-null
        if(messageOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Message message = messageOptional.get();
            // return 'Message' entity
            // return message 
            // besides 'messageId' field which is remaining the same ...
            // ... use setter methods from entity 'Message.java' to re-assign rest of selected 'message' fields w/ given 'msg' fields
            // message.setPostedBy(msg.getPostedBy());     // return message.getMessageText();
            // message.setMessageText(msg.getMessageText());
            // message.setTimePostedEpoch(msg.getTimePostedEpoch());
            // persist changes to 'Message' DB table
            messageRepository.save(message);
            return message;
            // use JpaRepository's .deleteById() method to remove 'message' entity associated w/ passed in 'messageId'
            // messageRepository.deleteById(messageId);
            // return 1 as wished (represents number of entity changed from this deletion process)
            // return 1;
        }
        // otw if the value from 'messageOptional' is indeed NULL ... return default falsy value (null)
        return null;                                            /* aka 'if(!messageRepository.existsById(messageId)){...}' */
    }
    
    // The intial JSON will likely contain a message_text and a posted_by, I'm assuming, so that we can verify that posted by is an actual user in the system
    // public Message addMessage(Message message)
    // {
    //     // The initial constraints are already in annotaion on entity
    //     // Need to check the posted_by with a existsById
    //     if (message.getMessageText() == "") {
    //         throw new ClientErrorException("Message can't be blank");
    //     }
    //     if(message.getMessageText().length() > 255)
    //     {
    //         throw new ClientErrorException("Message can't be over 255 characters");
    //     }
    //     if(accountRepository.existsById((long) message.getPostedBy()) == true)
    //     {
    //         Message newMes = messageRepository.save(message);
    //         return newMes;
    //     }
    //     else
    //     {
    //         throw new ClientErrorException("Could not find associated postby id");
    //     }

    //     // We also need to, upon ensuring postedby is true, the message is persisted to db using save
    // }

    public Optional<Message> newMessage(int posted_by, String message_text, long time_posted_epoch){
        if(accountRepository.findById(posted_by).isPresent()){
            if(message_text==null || message_text.isBlank() || message_text.length()>254){
                return Optional.empty();
            }
            else{
                Message newMessage = new Message(posted_by, message_text, time_posted_epoch);
                return Optional.of(messageRepository.save(newMessage));
                /*
                 * The jpa repository .save() method is the preferred way of persisting information and
                 * obtaining the persisted record however in this case since the appliaction manually adds 
                 * records to the database as shown in the data.sql file on initialisaiton. The .save() method 
                 * causes a primary key violation since on the first call it attempts to add a message_id of 1 but this already exists in the 
                 * database. Removing these insertions from the data.sql file allows the application to work as expected.
                 */

                //messageRepository.newMessage(posted_by, message_text, time_posted_epoch);
                //return messageRepository.getMessageByTimeAndAccount(time_posted_epoch, posted_by);
            }
        } else{
            return Optional.empty();
        }
        
    }
    // ## 4: Our API should be able to retrieve all messages.
    public List<Message> getAllMessages(){
        // employ built-in .findAll() method from CrudRepository (extends from JpaRepository) to find all 'Message' object entities/records in 'Message' DB table
        return messageRepository.findAll();
    }

    // ## 5: Our API should be able to retrieve a message by its 'messageId' ('msgId' is a placeholder --- can be any name).
    public Message findBymessageId(Integer msgId){
        // call .findById() method to retrieve 'Message' entity/record from DB fitting given parameter 'msgId'
        Optional<Message> messageOptional = messageRepository.findById(msgId);
        // if value assigned to 'messageOptional' container obj is of a non-null nature ...
        if(messageOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Message message = messageOptional.get();
            // return 'Message' entity
            return message;     // return message.getMessageText();
        }
        // otw if the value from 'messageOptional' is indeed NULL ... return default falsy value (null)
        return null;

        // List<Message> allMsgsByUserId =  messageRepository.findAllById((long) msgId);
        // return allMsgsByUserId;

        // call on .findBymessageId() from 'MessageRepository.java' class --- Spring JPA "infers" (not really) SQL query from its method name back in 'MessageRepository.java' class
        // return messageRepository.findBymessageId(msgId);
    }

    // ## 6: Our API should be able to delete a message identified by a message ID.
    public Integer deleteBymessageId(int messageId){
        // call .findById() method to retrieve 'Message' entity/record from DB fitting given parameter 'messageId'
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        // if value assigned to 'messageOptional' container obj is of a non-null nature ...
        if(messageOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            // Message message = messageOptional.get();
            // return 'Message' entity
            // return message; 
            // use .setMessageId() setter method to set 'message' messageId field to null (remove from class)
            // message.setMessageId(null);     // return message.getMessageText();
            // use JpaRepository's .deleteById() method to remove 'message' entity associated w/ passed in 'messageId'
            messageRepository.deleteById(messageId);
            // return 1 as wished (represents number of entity changed from this deletion process)
            return 1;
        }
        // otw if the value from 'messageOptional' is indeed NULL ... return default falsy value (null)
        return null;
        // if(messageRepository.existsById(messageId)) {
        //     messageRepository.deleteById(messageId);
        // }
    }

    // ## 7: Our API should be able to update a message text identified by a message ID.
    public Integer updateBymessageId(int messageId, Message msg) throws Exception{
        // initialize 'msgText' String variable w/ 'msg' messageText value
        String msgText = msg.getMessageText();
        // if 'msgText' field does not contain anything ...
        if(msgText == "" || msgText.isEmpty()){     // msgText.isBlank()
            throw new Exception("msgText is empty, spotless");
        }
        // else if 'msgText' is above the character limit ...
        if (msgText.length() > 255){
            throw new Exception("msgText just a teeny bit too long");
        }
        // else if 'messageId' DNE ...
        if(!messageRepository.existsById(messageId)){               /* EQUIVALENT to 'if(messageOptional.isEmpty()){...}' logic-wise, req. Optional to be declare earlier*/
            throw new Exception("messageId is mia!");           
        }
        
        // call .findById() method to retrieve 'Message' record from DB fitting given parameter 'messageId'
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        // if value assigned to 'messageOptional' container obj's value is NON-null
        if(messageOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Message message = messageOptional.get();
            // return 'Message' entity
            // return message 
            // besides 'messageId' field which is remaining the same ...
            // ... use setter methods from entity 'Message.java' to re-assign rest of selected 'message' fields w/ given 'msg' fields
            // message.setPostedBy(msg.getPostedBy());     // return message.getMessageText();
            message.setMessageText(msg.getMessageText());
            // message.setTimePostedEpoch(msg.getTimePostedEpoch());
            // persist changes to 'Message' DB table
            messageRepository.save(message);
            // return message;
            // use JpaRepository's .deleteById() method to remove 'message' entity associated w/ passed in 'messageId'
            messageRepository.deleteById(messageId);
            // return 1 as wished (represents number of entity changed from this deletion process)
            return 1;   
        }
        // otw if the value from 'messageOptional' is indeed NULL ... return default falsy value (null)
        return null;                                            /* aka 'if(!messageRepository.existsById(messageId)){...}' */
    }

    // ## 8: Our API should be able to retrieve all messages written by a particular user.
    // public List<Message> getAllMessagesByUserId(int userId){
    //     // obtain messages to arg's 'userId' by calling getAllMessagesByUserId() method in 'MessageRepository ('MessageDAO') class on given arg's 'userId'
    //     return messageRepository.findAllMessagesByAccountId(userId);
    // }

    public List<Message> getAllMessagesByUserId(Integer accountId){
        // initialize 'messageList' List variable to gather up all 'Message' records associated to given 'accountId'
        List<Message> messageList = messageRepository.findByPostedBy(accountId);
        // return List of 'Message' objects 
        return messageList;
    }
}

package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/* JPARepository will be used to preform persistence operations on 'Message' objects */
// ref: Week 10, "Spring Transactional" Coding Lab -- 'ShipRepository.java'

// 'MessageRepository' interfaces 'extends' (inherits) from 'JpaRepository' class
public interface MessageRepository extends JpaRepository<Message, Long> {
    /* Spring JPA 'infers' query methods by taking a gander at method names ...
     * ex. Spring JPA would look at 'findAllMessagesByAccountId()' & generate SQL query/statement:
     * 'SELECT * FROM Message WHERE accountId = ?'
     * Aside: Changed method name from 'findAllMessagesByUserId()' -> 'findALlMessagesByAccountId()'
     *        since there is NOT an 'userId' field but an 'accountId' --- in hopes to reducing confusion for Spring JPA
     */

    // ## 3: Our API should be able to process the creation of new messages.
    /* OMMITTED: implemented built-in .save() method from Spring container's 'JPARepository' in 'MessageService.java' Service Layer 
     * ... to persist (save/create) a 'Message' object entity (record) into 'Message' database table
    */
    // public Message createMessage(Message msg);

    // ## 4: Our API should be able to retrieve all messages.
    /* OMMITTED: implemented built-in .findAll() method from Spring container's 'JPARepository' in 'MessageService.java' Service Layer
     * ... to retrieve all entities from 'Message' database table 
    */
    // public List<Message> getAllMessages(); 

    // ## 5: Our API should be able to retrieve a message by its ID.
    // public Message findMessageByMessageId(int messageId);   // CHECK: Change 'msgId' to 'messageId' (matching field in 'Message')?
    
    // ## 6: Our API should be able to delete a message identified by a message ID.
    // public Message deleteMessagebyMsgId(int messageId);
    
    // ## 7: Our API should be able to update a message text identified by a message ID.
    // public Message updateMessagebyMsgId(int messageId, Message message);
    
    // ## 8: Our API should be able to retrieve all messages written by a particular user.
    // public List<Message> findAllMessagesByAccountId(int accountId);

}

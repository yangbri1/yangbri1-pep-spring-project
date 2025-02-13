package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/* JPARepository will be used to preform persistence operations on 'Message' objects */
// ref: Week 10, "Spring Transactional" Coding Lab -- 'ShipRepository.java'

// 'MessageRepository' interfaces 'extends' (inherits) from 'JpaRepository' class
public interface MessageRepository extends JpaRepository<Message, Integer> {
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
    // public boolean existsByPostedBy(Integer postedBy);   // 'postedBy' is a FOREIGN KEY in 'Message' pointing to PRIMARY KEY 'accountId' in 'Account' DB table
    // 'public boolean existsById(int accountId)' in 'AccountRepository.java' works
    // equal to calling 'accountRepository.existsByAccountId(message.getPostedBy()) == true' in 'MessageService' when checking if accountId already exists in 'Account' table

    // ## 4: Our API should be able to retrieve all messages.
    /* OMMITTED: implemented built-in .findAll() method from Spring container's 'JPARepository' in 'MessageService.java' Service Layer
     * ... to retrieve all entities from 'Message' database table 
    */
    // public List<Message> getAllMessages(); 

    /* NO NEED TO define .findById() or .deleteById() as both methods are available via JpaRepository through Spring framework */
    // ## 5: Our API should be able to retrieve a message by its ID.
    // public Message findBymessageId(Integer messageId);   // CHECK: Change 'msgId' to 'messageId' (matching field in 'Message')?
    
    // ## 6: Our API should be able to delete a message identified by a message ID.
    // public Message deleteBymessageId(int messageId);
    
    // ## 7: Our API should be able to update a message text identified by a message ID.
    // public Message updateBymessageId(int messageId, Message message);
    
    // ## 8: Our API should be able to retrieve all messages written by a particular user.
    // @Query("SELECT a FROM message WHERE a.postedBy = :postedBy")
    // public List<Message> findAllMessagesByAccountId(int accountId);

    // @Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
    // List<Account> findByAccountId(@Param("accountId") Integer accountId)

    public List<Message> findByPostedBy(Integer accountId);
}

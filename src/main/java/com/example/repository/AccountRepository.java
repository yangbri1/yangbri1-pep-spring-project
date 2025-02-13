package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// import 'Query' & 'Param' from Spring framework to create a custom query using respective annotations
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import java.util.List;

/* JPARepository will be used to preform persistence operations on 'Account' objects */
// ref: Week 10, "Spring Relationships" Coding Lab -- 'AlbumRepository.java'

// 'AccountRepository' interfaces 'extends' (inherits) from 'JpaRepository' class --- in turn provides basic CRUD operations 
public interface AccountRepository extends JpaRepository<Account, Integer> {
    // List<Account> getAllAccounts();
    // ## 1: Our API should be able to process new User registrations.
    // ## 1a) Helper function to check if 'account' entity already exists in DB table
    /* Quick Note: 
     * 1) In order to allow Spring JPA to automatically generate an SQL query/statement for a repository method,
     * it's name does not have much flexibilility (in this case needs to be 'existsByUsername()' verbatim)
     */
    public boolean existsByUsername(String username);
    /* 2) Otherwise could explicitly write out SQL query using '@Query' annotation & extract wanted info from query parameters via '@Param' annotation:
     * @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Account a WHERE a.username = :username")
     * boolean existsByUsername(@Param("username") String username);
     * (equivalent to above)
     */

    // ## 2: Our API should be able to process User logins.
    // Spring JPA will auto-generate respective SQL query/String from this method's name
    public boolean existsByPassword(String password);
    // similar to PEP Project #1, although there's also .findByUsername() method from Spring Optional built-in method --- but encounter errors --- perhaps later refactor code
    public Account findByUsername(String username);     

    @Modifying
    @Query(
        value = "INSERT INTO message (postedBy, messageText, timePostedEpoch) VALUES (:postedBy, :messageText, :timePostedEpoch)",
        nativeQuery = true)
    void newMessage(
        @Param("postedBy") int posted_by, 
        @Param("messageText") String message_text, 
        @Param("timePostedEpoch") long time_posted_epoch);

    
    @Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
    List<Account> findByAccountId(@Param("accountId") Integer accountId);

    // public boolean existsByPostedBy(Integer postedBy);
}

package com.example.service;

// import 'Account.java' & 'AccountRepository.java' classes
import com.example.entity.Account;

import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

// import 'List' & 'Optional' classes from Java's Utility package to use built-in Java methods
import java.util.List;
import java.util.Optional;

/* 'AccountService.java' class will be used to demonstrate ORM functionality for related entities.
 * Leveraging the stereotype annotation @Service --- functionally same as @Component
 */
@Service
@Transactional
public class AccountService {
    // declare an instance variable 'accountRepository'
    @Autowired
    private AccountRepository accountRepository;
    
    // constructor instantiating above instance variable 'AccountRepository' by taking in an 'AccountRepository' obj
    /* introduce 'AccountRepository' obj in 'AccountService' obj -- enables 'AccountService' class to use 'AccountRepository' functionalities */
    @Autowired
    public AccountService(AccountRepository accountRepository){
        /* reserved keyword 'this' points to earlier declared instance variable (outside this constructor) ...
        & assign given parameter 'accountRepository' value to it */
        this.accountRepository = accountRepository;
    }

    /* Back in Social Media Blog Project #1, below method signatures would first be semi-defined in 'AccountDAO.java' before delving deeper into the logic here  */
    public List<Account> getAllAccounts(){
        // call .findAll() method from Spring container's JpaRepository to retireve all 'account' entities in 'Account' DB table
        return accountRepository.findAll();
    }

    // ## 1: Our API should be able to process new User registrations.
    public Account addAccount(Account account) throws Exception{
        // initialize variables to w/ respective values from their calling .getUsername() & .getPassword() methods
        String username = account.getUsername();
        String password = account.getPassword();

        /* sometimes there will be some form validation on the front-end --- this is on the backend ... */ 
        // if password DNE OR provided 'password' < 4 in length ...
        if(password == null || password.length() < 4){
            // throw Exception to CLI alongside a custom message informing user of the 4 or + character constraint for 'password' field
            throw new Exception("Password must consists of at least 4 characters ...");  // return null;
        }
        // else if nothing was detected in the 'username' field ...
        else if(username == null || username.isBlank()){    // .isBlank() method accounts for whitespaces and empty String // username.isEmpty()){
            // notify user of 'username' field must be filled in
            throw new Exception("Please provide a valid username [spaces disallowed]"); // return null;
        }
        // else if username already taken
        else if(accountRepository.existsByUsername(username)){
            // tell user duplicate username is not possible
            throw new Exception("Sorry for the inconvenience --- Username already claimed"); // return null;
        }
        // if both 'username' & 'password' fields pass the set constraints --- register/create the new account
        // return .createAccount() as it is since return Account type from that yields the newly INSERT-ed row 
        // return accountDAO.createAccount(account);

        // persist (save/add) given arg 'account' entity/record to 'Account' database table via .save() Spring framework method
        return accountRepository.save(account);
    }
    // ## 2: Our API should be able to process User logins.
    /* The login will be successful if and only if the username and password 
    provided in the request body JSON match a real account existing on the database. */
    public Account loginAccountGetAccId(Account account) throws Exception {
        // initialize variables to w/ respective values from their calling .getUsername() & .getPassword() methods
        String username = account.getUsername();
        String password = account.getPassword();
        // check if arg's 'account' username exists within 'Account' DB table ...
        if(!accountRepository.existsByUsername(username)){
            // under the guise that the username is missing ...
            throw new Exception("Invalid Credentials");     // hypothetically should be vague to reduce likelihood of giving unnecessary info to scammer
        }
        // if given 'account' username were to be detected, call on .findByUsername() method from 'accountRepository' class to retrieve the whole 'Account' obj from the backend
        Account returnAccount = accountRepository.findByUsername(username);
        // utilize the getter method .getPassword() to extract 'password' field from table & "loosely" compare it to the given 'account' username (value only) via .equals()
        if(returnAccount.getPassword().equals(password)){
            // under the premise of any matches, return the whole 'Account' obj
            return returnAccount;
        } 
        // otw if 'username' match but 'password' does NOT
        else {
            // throw another purposefully vague exception on CLI back to user notifying them
            throw new Exception("Invalid Credentials");
        }
        
        // Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
        // if(accountOptional.isPresent() && (accountOptional.get().getPassword()).equals(password)){
        //     Account acc = accountOptional.get();
        //     return ResponseEntity.ok(accountOptional.get());
        // }
        // else{
        //     throw new Exception("Account not detected");
        // }
    }
    
    

}

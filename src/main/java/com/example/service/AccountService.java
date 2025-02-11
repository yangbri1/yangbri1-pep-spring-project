package com.example.service;

// import 'Account.java' & 'AccountRepository.java' classes
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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
    AccountRepository accountRepository;
    
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
        return accountRepository.findAll();
    }

    // ## 1: Our API should be able to process new User registrations.
    public Account addAccount(Account account){
        // persist (save/add) given arg 'account' entity/record to 'Account' database table via .save() Spring framework method
        return accountRepository.save(account);
    }
    // ## 2: Our API should be able to process User logins.
    /* The login will be successful if and only if the username and password 
    provided in the request body JSON match a real account existing on the database. */
    // public Account loginAccountGetAccId(Account account){ 
    //     return accountRepository.
    // }

}

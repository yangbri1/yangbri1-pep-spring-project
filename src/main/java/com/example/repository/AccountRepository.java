package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* JPARepository will be used to preform persistence operations on 'Account' objects */
// ref: Week 10, "Spring Relationships" Coding Lab -- 'AlbumRepository.java'

// 'AccountRepository' interfaces 'extends' (inherits) from 'JpaRepository' class --- in turn provides basic CRUD operations 
public interface AccountRepository extends JpaRepository<Account, Long> {
    // List<Account> getAllAccounts();

}

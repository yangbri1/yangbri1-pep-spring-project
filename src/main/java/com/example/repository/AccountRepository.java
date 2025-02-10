package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/* JPARepository will be used to preform persistence operations on 'Account' objects */
// ref: Week 10, "Spring Relationships" Coding Lab -- 'AlbumRepository.java'

// 'AccountRepository' interfaces 'extends' (inherits) from 'JpaRepository' class
public interface AccountRepository extends JpaRepository<Account, Long> {
}

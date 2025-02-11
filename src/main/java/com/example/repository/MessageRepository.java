package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/* JPARepository will be used to preform persistence operations on 'Message' objects */
// ref: Week 10, "Spring Transactional" Coding Lab -- 'ShipRepository.java'

// 'MessageRepository' interfaces 'extends' (inherits) from 'JpaRepository' class

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllMessagesByUserId(int userId);

}

package com.flowwable.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flowwable.poc.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

package com.mb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.model.User;



public interface UserDAO extends JpaRepository<User, Long>{

}

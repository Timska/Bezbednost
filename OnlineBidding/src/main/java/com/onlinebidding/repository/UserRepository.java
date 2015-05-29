package com.onlinebidding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}

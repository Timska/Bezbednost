package com.onlinebidding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

}

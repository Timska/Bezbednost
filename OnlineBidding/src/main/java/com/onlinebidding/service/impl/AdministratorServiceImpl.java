package com.onlinebidding.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Administrator;
import com.onlinebidding.repository.AdministratorRepository;
import com.onlinebidding.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;
	
	public void create(Administrator admin) {
		administratorRepository.save(admin);
	}

	public Administrator findAdministrator(String adminUserName) {
		return administratorRepository.findOne(adminUserName);
	}

}

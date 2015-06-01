package com.onlinebidding.service;

import com.onlinebidding.model.Administrator;

public interface AdministratorService {

	public void create(Administrator admin);

	public Administrator findAdministrator(String adminUserName);

}

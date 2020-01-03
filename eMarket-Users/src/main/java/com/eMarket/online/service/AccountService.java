package com.eMarket.online.service;

import com.eMarket.online.model.EmarketRole;
import com.eMarket.online.model.EmarketUser;

public interface AccountService {

	public EmarketUser saveUser(String username, String firstName, String lastName, String password, String confirmedPassword);
	public EmarketRole saveRole(EmarketRole role);
	public EmarketUser loadUserByUsername(String username);
	public void addRoleToUser(String username, String roleName);
	
}

package com.eMarket.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eMarket.online.dto.EmarketUserRequest;
import com.eMarket.online.model.EmarketUser;
import com.eMarket.online.service.AccountService;

import lombok.Data;

@RestController
public class EmarketUserController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public EmarketUser register (@RequestBody EmarketUserRequest userForm) {
		return accountService.saveUser(userForm.getUsername(), userForm.getFirstName(), userForm.getLastName(), userForm.getPassword(), userForm.getConfirmedPassword());
	}
}

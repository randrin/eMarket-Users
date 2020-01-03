package com.eMarket.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eMarket.online.model.EmarketUser;
import com.eMarket.online.service.AccountService;

import lombok.Data;

@RestController
public class EmarketUserController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public EmarketUser register (@RequestBody UserForm userForm) {
		return accountService.saveUser(userForm.getUsername(), userForm.getFirstName(), userForm.getLastName(), userForm.getPassword(), userForm.getConfirmedPassword());
	}
}

@Data
class UserForm {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String confirmedPassword;
}
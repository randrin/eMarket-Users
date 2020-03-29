package com.eMarket.online.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EmarketUserRequest {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String confirmedPassword;
}

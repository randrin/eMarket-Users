package com.eMarket.online.utils;

public class EmarketConstants {

	// Globals Constants
	public static final String USER_ALREADY_EXIST = "User already exist in eMarket Online";
	public static final String PASSWORDS_NOT_MATCH = "Password and confirmed Password not match";
	public static final String USER_NOT_FOUND = "User not found in eMarket Online Store";
	
	// JWT Constants
	public static final String JWT_HEADER = "Authorization";
	public static final String JWT_TOKEN_PREFIX = "Bearer ";
	public static final String JWT_SECRET = "ophthacare-infos@gmail.com";
	public static final long JWT_EXPIRATION = 10*24*3600*1000;
	
}

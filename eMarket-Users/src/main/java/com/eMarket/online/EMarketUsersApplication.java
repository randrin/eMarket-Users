package com.eMarket.online;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eMarket.online.model.EmarketRole;
import com.eMarket.service.AccountService;

@SpringBootApplication
public class EMarketUsersApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EMarketUsersApplication.class, args);
	}

	@Bean
	CommandLineRunner start (AccountService accountService) {
		return args->{
			accountService.saveRole(new EmarketRole(null, "USER"));
			accountService.saveRole(new EmarketRole(null, "ADMIN"));
			accountService.saveRole(new EmarketRole(null, "SUPERVISOR"));
			
			Stream.of("randrino17", "vanessa07", "aurel10").forEach(user -> {
				accountService.saveUser(user, "1234", "1234");
			});
			accountService.addRoleToUser("randrino17","ADMIN");
		};
	}
	
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

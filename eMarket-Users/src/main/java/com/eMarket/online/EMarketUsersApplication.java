package com.eMarket.online;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eMarket.online.model.EmarketRole;
import com.eMarket.online.service.AccountService;

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
			
			Stream.of("randrino17 Nzeukang Nimpa Randrin", "vanessa07 Takou Tsapmene Vanessa", "aurel10 Nzeukang Tchoffo Aurel").forEach(user -> {
				accountService.saveUser(user.split(" ")[0], (user.split(" ")[1] + " " + user.split(" ")[2]), user.split(" ")[3], "1234", "1234");
			});
			accountService.addRoleToUser("randrino17","ADMIN");
		};
	}
	
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

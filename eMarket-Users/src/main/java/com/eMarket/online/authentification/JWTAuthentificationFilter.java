package com.eMarket.online.authentification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eMarket.online.model.EmarketUser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmarketUser emarketUser = new ObjectMapper().readValue(request.getInputStream(), EmarketUser.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(emarketUser.getUsername(), emarketUser.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getCause() + " - " + e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User userConnected = (User) authResult.getPrincipal();
		List<String> roles = new ArrayList<String>();
		userConnected.getAuthorities().forEach(role -> {
			roles.add(role.getAuthority());
		});
		String jwt = JWT.create()
				.withIssuer(request.getRequestURI())
				.withSubject(userConnected.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[roles.size()]))
				.withExpiresAt(new Date(System.currentTimeMillis() + 10*24*3600))
				.sign(Algorithm.HMAC256("ophthacare-infos@gmail.com"));
		response.addHeader("Authorization", jwt); 
	}
}

package com.eMarket.online.authentification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eMarket.online.utils.EmarketConstants;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
        
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
        	response.setStatus(HttpServletResponse.SC_OK);
        } else if (request.getRequestURI().equalsIgnoreCase("/login")) {
        	filterChain.doFilter(request, response);
        	return;
        } else {
        	String jwtToken = request.getHeader(EmarketConstants.JWT_HEADER);
        	System.out.println("Token="+jwtToken);
        	if (jwtToken == null || !jwtToken.startsWith(EmarketConstants.JWT_TOKEN_PREFIX)) {
        		filterChain.doFilter(request, response);
            	return;
        	}
        	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(EmarketConstants.JWT_SECRET)).build();
        	String jwt = jwtToken.substring(EmarketConstants.JWT_TOKEN_PREFIX.length());
        	System.out.println("JWT="+jwt);
        	DecodedJWT decodedJWT = verifier.verify(jwt);
        	String username = decodedJWT.getSubject();
        	List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
        	System.out.println("username="+username);
            System.out.println("roles="+roles);
        	Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        	roles.forEach(role -> {
        		authorities.add(new SimpleGrantedAuthority(role));
        	});
        	UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);
        	SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(request, response);
        }
	}
}

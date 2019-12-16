package com.eMarket.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eMarket.online.model.EmarketRole;
import com.eMarket.online.model.EmarketUser;
import com.eMarket.online.repository.EmarketRoleRepository;
import com.eMarket.online.repository.EmarketUserRepository;
import com.eMarket.service.AccountService;
import com.eMarket.service.utils.EmarketConstants;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private EmarketUserRepository emarketUserRepository;
	private EmarketRoleRepository emarketRoleRepository;
	private BCryptPasswordEncoder brBCryptPasswordEncoder;
	
	public AccountServiceImpl(EmarketUserRepository emarketUserRepository, EmarketRoleRepository emarketRoleRepository,
			BCryptPasswordEncoder brBCryptPasswordEncoder) {
		this.emarketUserRepository = emarketUserRepository;
		this.emarketRoleRepository = emarketRoleRepository;
		this.brBCryptPasswordEncoder = brBCryptPasswordEncoder;
	}
	
	@Override
	public EmarketUser saveUser(String username, String password, String confirmedPassword) {

		EmarketUser user = emarketUserRepository.findByUsername(username);
		if (user != null) {
			throw new RuntimeException(EmarketConstants.USER_ALREADY_EXIST);
		}
		if (!password.equals(confirmedPassword)) {
			throw new RuntimeException(EmarketConstants.PASSWORDS_NOT_MATCH);
		}
		EmarketUser emarketUser = new EmarketUser();
		emarketUser.setUsername(username);
		emarketUser.setActived(true);
		emarketUser.setPassword(brBCryptPasswordEncoder.encode(password));
		addRoleToUser(username, "USER");
		emarketUserRepository.save(emarketUser);
		return emarketUser;
	}

	@Override
	public EmarketRole saveRole(EmarketRole role) {
		return emarketRoleRepository.save(role);
	}

	@Override
	public EmarketUser loadUserByUsername(String username) {
		return emarketUserRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		EmarketUser user = emarketUserRepository.findByUsername(username);
		EmarketRole role = emarketRoleRepository.findByRoleName(roleName);
		user.getRoles().add(role);
	}
}

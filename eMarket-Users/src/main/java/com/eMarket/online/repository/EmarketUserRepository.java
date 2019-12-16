package com.eMarket.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eMarket.online.model.EmarketUser;

@RepositoryRestResource
public interface EmarketUserRepository extends JpaRepository<EmarketUser, Long>{
	public EmarketUser findByUsername(String username);

}

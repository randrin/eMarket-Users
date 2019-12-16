package com.eMarket.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eMarket.online.model.EmarketRole;

@RepositoryRestResource
public interface EmarketRoleRepository extends JpaRepository<EmarketRole, Long>{
	public EmarketRole findByRoleName(String roleName);

}
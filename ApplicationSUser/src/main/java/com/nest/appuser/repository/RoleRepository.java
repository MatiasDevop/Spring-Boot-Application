package com.nest.appuser.repository;

import org.springframework.data.repository.CrudRepository;

import com.nest.appuser.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	public Role findByName(String role);
}

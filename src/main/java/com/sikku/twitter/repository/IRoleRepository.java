package com.sikku.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sikku.twitter.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String roleName);

}

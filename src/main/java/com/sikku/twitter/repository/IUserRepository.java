package com.sikku.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sikku.twitter.model.User;


public interface IUserRepository extends JpaRepository<User, Long> {
//	Optional<User> findByEmail(String email);
	User findByEmail(String email);
	
	@Query("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
	List<User> searchUser(String query);
}

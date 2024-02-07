package com.sikku.twitter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sikku.twitter.model.User;
import com.sikku.twitter.repository.IUserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userByEmail = userRepo.findByEmail(username);

		if (userByEmail == null || userByEmail.isLogin_with_google()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(userByEmail.getEmail(), userByEmail.getPassword(),
				userByEmail.getRoles());
	}

}

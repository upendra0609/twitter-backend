package com.sikku.twitter.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sikku.twitter.exception.UserException;
import com.sikku.twitter.model.Role;
import com.sikku.twitter.model.User;
import com.sikku.twitter.model.Verification;
import com.sikku.twitter.model.jwtmodel.JwtResponse;
import com.sikku.twitter.repository.IRoleRepository;
import com.sikku.twitter.repository.IUserRepository;
import com.sikku.twitter.security.ISecurityService;
import com.sikku.twitter.security.jwt.JwtHelperService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ISecurityService securityService;
	@Autowired
	private JwtHelperService jwtHelperService;
	@Autowired
	private IRoleRepository roleRepository;

	private ResponseEntity<?> authenticate(@RequestParam String userName, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) throws UserException {
		Boolean login = securityService.login(userName, password, request, response);
		System.out.println("logged-in");
		if (login) {
			User findByEmail = userRepository.findByEmail(userName);
			String roleList = findByEmail.getRoles().stream().map(role -> role.getName().substring(5))
					.collect(Collectors.joining(", "));
			String token = jwtHelperService.generateToken(userName, roleList);
			System.out.println("token:  " + token);
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setStatus(true);
			jwtResponse.setToken(token);
			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		}

		return new ResponseEntity<>(new UserException("Invalid username or password"), HttpStatus.CONFLICT);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		User findByEmail = userRepository.findByEmail(email);
		if (findByEmail != null) {
			throw new UserException("User Already Present");
		}
		user.setVerification(new Verification());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Set<Role> set = user.getRoles().stream().map(role -> {
			Role newRole = roleRepository.findByName(role.getName());
			if (newRole == null) {
				newRole = new Role();
				newRole.setName(role.getName());
			}
			newRole.getUsers().add(user);
			return newRole;
		}).collect(Collectors.toSet());
		System.out.println("size: " + set.size());
		user.setRoles(set);

		User createdUser = userRepository.save(user);
		System.out.println(createdUser);

		return authenticate(email, password, request, response);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("login method called");
		return authenticate(userName, password, request, response);
	}

	@PostMapping("/test")
//	@PreAuthorize("hasRole('USER')")
	public String test() {
		System.out.println("Test method");
		return "test";
	}

}

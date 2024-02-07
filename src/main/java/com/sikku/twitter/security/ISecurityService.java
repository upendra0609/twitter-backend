package com.sikku.twitter.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ISecurityService {
	Boolean login(String userName, String password, HttpServletRequest request, HttpServletResponse response);

}

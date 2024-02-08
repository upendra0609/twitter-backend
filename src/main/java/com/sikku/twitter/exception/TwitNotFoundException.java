package com.sikku.twitter.exception;

public class TwitNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TwitNotFoundException(String message) {
		super(message);
	}

}

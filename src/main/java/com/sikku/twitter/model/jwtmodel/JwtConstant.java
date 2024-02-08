package com.sikku.twitter.model.jwtmodel;

public enum JwtConstant {
	INSTANCE;

	public static final String SECRET_KEY = "sjhfkjsdhgvjdghsektuiotu8yoieutc8mt8youtimrotuhmicwo8lsit";
	public static final String JWT_HEADER = "Authorization";
	public static final Long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 100L;
}

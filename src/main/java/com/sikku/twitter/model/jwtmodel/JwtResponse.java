package com.sikku.twitter.model.jwtmodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data	
@Getter
@Setter
public class JwtResponse {
	private String token;
	private Boolean status;

}

package com.sikku.twitter.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter	
public class ExceptionDetail {
	private String message;
	private String statusCode;
	private LocalDateTime dateTime;

}

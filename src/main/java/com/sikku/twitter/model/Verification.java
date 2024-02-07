package com.sikku.twitter.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Verification {
	private boolean status = false;
	private LocalDateTime startDate;
	private LocalDateTime endAt;
	private String planType;

}

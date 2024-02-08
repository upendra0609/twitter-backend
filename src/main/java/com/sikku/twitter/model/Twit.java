package com.sikku.twitter.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class Twit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User user;
	private String content;
	@OneToMany(mappedBy = "twit",cascade = CascadeType.ALL)
	private List<Like> like = new ArrayList<>();
	@OneToMany
	private List<Twit> replyTwits = new ArrayList<>();
	@ManyToMany
	private List<User> retwitUser = new ArrayList<>();
	@ManyToOne	
	private Twit replyFor;
	private String image;
	private String video;
	private boolean isReply;
	private boolean isTwit;
	private LocalDateTime createdAt;

}

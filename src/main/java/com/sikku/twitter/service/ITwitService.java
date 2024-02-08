package com.sikku.twitter.service;

import java.util.List;

import com.sikku.twitter.model.Twit;
import com.sikku.twitter.model.User;
import com.sikku.twitter.request.TwitReplyRequest;

public interface ITwitService {

	Twit createTwit(Twit twit, User user);

	List<Twit> findAll();

	Twit reTwit(Long twitId, User user);

	Twit findById(Long twitId);

	void deleteTwitById(Long twitId, Long userId);

	Twit removeFromreTwit(Long twitId, Long userId);
	
	Twit createReply(TwitReplyRequest req, User user);
	
	List<Twit> getUserTwit(User user);
	
	List<Twit> findByLikesContainsUser(User user);

}

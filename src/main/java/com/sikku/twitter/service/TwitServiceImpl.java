package com.sikku.twitter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sikku.twitter.exception.TwitNotFoundException;
import com.sikku.twitter.exception.UserException;
import com.sikku.twitter.model.Twit;
import com.sikku.twitter.model.User;
import com.sikku.twitter.repository.ITwitRepository;
import com.sikku.twitter.request.TwitReplyRequest;

@Service
public class TwitServiceImpl implements ITwitService {
	private ITwitRepository twitRepo;

	@Override
	public Twit createTwit(Twit twit, User user) {
		twit.setUser(user);
		twit.setTwit(true);
		twit.setReply(false);
		return twitRepo.save(twit);
	}

	@Override
	public List<Twit> findAll() {
		return twitRepo.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public Twit reTwit(Long twitId, User user) {
		Twit twit = twitRepo.findById(twitId)
				.orElseThrow(() -> new TwitNotFoundException("twit not found with id " + twitId));
		if (twit.getRetwitUser().contains(user)) {
			twit.getRetwitUser().remove(user);
		} else {
			twit.getRetwitUser().add(user);
		}
		return twitRepo.save(twit);
	}

	@Override
	public Twit findById(Long twitId) {
		return twitRepo.findById(twitId)
				.orElseThrow(() -> new TwitNotFoundException("twit not found with id " + twitId));
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) {
		Twit twit = twitRepo.findById(twitId)
				.orElseThrow(() -> new TwitNotFoundException("twit not found with id " + twitId));

		if (twit.getUser().getId().equals(userId)) {
			twitRepo.delete(twit);
		} else {
			throw new UserException("you can't delete another user's twit");
		}
	}

	@Override
	public Twit createReply(TwitReplyRequest req, User user) {
		return null;
	}

	@Override
	public List<Twit> getUserTwit(User user) {
		return null;
	}

	@Override
	public List<Twit> findByLikesContainsUser(User user) {
		return null;
	}

	@Override
	public Twit removeFromreTwit(Long twitId, Long userId) {
		return null;
	}

}

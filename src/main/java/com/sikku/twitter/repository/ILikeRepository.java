package com.sikku.twitter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sikku.twitter.model.Like;

public interface ILikeRepository extends JpaRepository<Like, Long> {
	
//	@Query("SELECT l FROM Like l WHERE l.user.id:userId AND l.twit.id = twitId")
//	public Optional<Like> isLikeExist(Long userId, Long twitId);
	
	Optional<Like> findByUserIdAndTwitId(Long userId, Long twitId);
	
	@Query("SELECT l FROM Like l WHERE l.twit.id=:twitId")
	List<Like> findByTwitId(Long twitId);

}

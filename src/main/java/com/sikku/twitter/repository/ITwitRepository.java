package com.sikku.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sikku.twitter.model.Twit;
import com.sikku.twitter.model.User;

public interface ITwitRepository extends JpaRepository<Twit, Long> {

	List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();

//	List<Twit> findAllByIsReplyTrueOrderByCreatedAtDesc();
	List<Twit> findByRetwitUserContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(User user, Long userId);

//	 @Query("SELECT t FROM Twit t JOIN t.likes l WHERE :user MEMBER OF l.user ORDER BY t.createdAt DESC")
//	List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);

	List<Twit> findByLikesUserOrderByCreatedAtDesc(User user);

	@Query("SELECT t FROM Twit t JOIN t.likes l where l.user.id=:userId")
	List<Twit> findByLikesUser_Id(Long userId);

}

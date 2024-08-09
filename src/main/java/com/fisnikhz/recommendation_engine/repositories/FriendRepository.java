package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friends, Long> {

}

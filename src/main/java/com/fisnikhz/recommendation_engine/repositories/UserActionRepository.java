package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.UserActions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserActionRepository extends JpaRepository<UserActions, Long> {

    @Query(value = "SELECT * FROM user_actions WHERE user_id = :userId", nativeQuery = true)
    List<UserActions> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_actions WHERE video_id = :videoId", nativeQuery = true)
    List<UserActions> findByVideoId(@Param("videoId") Long videoId);

    @Query(value = "SELECT * FROM user_actions WHERE user_id = :userId AND video_id = :videoId", nativeQuery = true)
    UserActions findByUserAndVideo(@Param("userId") Long userId, @Param("videoId") Long videoId);

    @Query(value = "SELECT * FROM user_actions WHERE action_type = :actionType", nativeQuery = true)
    List<UserActions> findByActionType(@Param("actionType") String actionType);
}

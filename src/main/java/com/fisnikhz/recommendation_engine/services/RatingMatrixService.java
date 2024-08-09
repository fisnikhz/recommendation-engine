package com.fisnikhz.recommendation_engine.services;

import com.fisnikhz.recommendation_engine.entities.UserActions;
import com.fisnikhz.recommendation_engine.repositories.UserActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingMatrixService {

    @Autowired
    private UserActionRepository userActionRepository;

    public Map<Long, Map<Long, Integer>> createRatingMatrix() {
        List<UserActions> actions = userActionRepository.findAll();
        Map<Long, Map<Long, Integer>> ratingMatrix = new HashMap<>();

        for (UserActions action : actions) {
            ratingMatrix
                    .computeIfAbsent(Long.valueOf(action.getUserId()), k -> new HashMap<>())
                    .put(Long.valueOf(action.getVideoId()), Integer.valueOf(action.getScore()));
        }
        System.out.println(ratingMatrix);
        return ratingMatrix;

    }

//    public List<UserActions> getUserActionsByUserId(Long userId) {
//        return userActionRepository.findByUserId(userId);
//    }
//
//    public List<UserActions> getUserActionsByPostId(Long postId) {
//        return userActionRepository.findByPostId(postId);
//    }
//
//    public Integer getRatingByUserIdAndPostId(Long userId, Long postId) {
//        return userActionRepository.findRatingByUserIdAndPostId(userId, postId);
//    }
}

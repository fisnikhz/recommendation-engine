package com.fisnikhz.recommendation_engine.services;

import com.fisnikhz.recommendation_engine.repositories.UserRepository;
import com.fisnikhz.recommendation_engine.repositories.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VideoTagService {

    @Autowired
    private VideoTagRepository videoTagRepository;

    @Autowired
    private UserRepository userRepository;

    public Set<String> getVideoTags(Long videoId) {
        return new HashSet<>(videoTagRepository.findTagsByVideoId(videoId));
    }


    public Set<String> getUserTags(Long userId) {
        List<Long> watchedVideoIds = userRepository.findRatedVideoIdsByUserId(userId);
        Set<String> userTags = new HashSet<>();

        for (Long videoId : watchedVideoIds) {
            userTags.addAll(getVideoTags(videoId));
        }

        return userTags;
    }
}

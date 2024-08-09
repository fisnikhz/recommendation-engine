package com.fisnikhz.recommendation_engine.services;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimilarityService {

    public Map<Long, Double> computeCosineSimilarity(Map<Long, Integer> userRatings, Map<Long, Integer> otherUserRatings) {
        double dotProduct = 0.0;
        double userMagnitude = 0.0;
        double otherUserMagnitude = 0.0;

        for (Long videoId : userRatings.keySet()) {
            if (otherUserRatings.containsKey(videoId)) {
                dotProduct += userRatings.get(videoId) * otherUserRatings.get(videoId);
                userMagnitude += Math.pow(userRatings.get(videoId), 2);
                otherUserMagnitude += Math.pow(otherUserRatings.get(videoId), 2);
            }
        }

        userMagnitude = Math.sqrt(userMagnitude);
        otherUserMagnitude = Math.sqrt(otherUserMagnitude);

        double similarity = dotProduct / (userMagnitude * otherUserMagnitude);
        if(dotProduct == 0){
            similarity = 0.0;
        }
        System.out.println("double: "+similarity);

        Map<Long, Double> similarityMap = new HashMap<>();
        similarityMap.put(0L, similarity); // Key 0L can be replaced with a user ID in actual use
        System.out.println(similarityMap);
        return similarityMap;
    }
}

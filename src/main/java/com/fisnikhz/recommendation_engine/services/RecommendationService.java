package com.fisnikhz.recommendation_engine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private RatingMatrixService ratingMatrixService;

    @Autowired
    private SimilarityService similarityService;

    public List<Map.Entry<Long, Double>> recommendVideos(Long userId) {
        Map<Long, Map<Long, Integer>> ratingMatrix = ratingMatrixService.createRatingMatrix();
        System.out.println(ratingMatrix);
        Map<Long, Integer> userRatings = ratingMatrix.get(userId);

        // Check if userRatings is retrieved properly
        if (userRatings == null) {
            System.out.println("No ratings found for userId: " + userId);
            return Collections.emptyList();
        }

        // Get the similarity scores for each user
        Map<Long, Double> similarityScores = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Integer>> entry : ratingMatrix.entrySet()) {
            Long otherUserId = entry.getKey();
            if (!otherUserId.equals(userId)) {
                Map<Long, Integer> otherUserRatings = entry.getValue();
                double similarity = similarityService.computeCosineSimilarity(userRatings, otherUserRatings).get(0L);
                similarityScores.put(otherUserId, similarity);
            }
        }

        // Debug similarity scores
        System.out.println("Similarity scores: " + similarityScores);

        // Predict ratings based on similarity scores
        Map<Long, Double> predictedRatings = new HashMap<>();
        for (Map.Entry<Long, Double> entry : similarityScores.entrySet()) {
            Long otherUserId = entry.getKey();
            double similarity = entry.getValue();
            Map<Long, Integer> otherUserRatings = ratingMatrix.get(otherUserId);

            for (Map.Entry<Long, Integer> videoEntry : otherUserRatings.entrySet()) {
                Long videoId = videoEntry.getKey();
                Integer rating = videoEntry.getValue();
                if (userRatings.containsKey(videoId)) {
                    predictedRatings.put(videoId,
                            predictedRatings.getOrDefault(videoId, 0.0) + similarity * rating);
                }
            }
        }

        // Debug predicted ratings
        System.out.println("Predicted ratings: " + predictedRatings);

        // Sort and get the top recommended videos
        return predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

}
package com.fisnikhz.recommendation_engine.services;

import com.fisnikhz.recommendation_engine.repositories.UserActionRepository;
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

    @Autowired
    private VideoTagService videoTagService;

    public List<Map.Entry<Long, Double>> recommendVideos(Long userId) {
        Map<Long, Map<Long, Integer>> ratingMatrix = ratingMatrixService.createRatingMatrix();
        System.out.println(ratingMatrix);
        Map<Long, Integer> userRatings = ratingMatrix.get(userId);

        if (userRatings == null) {
            System.out.println("No ratings found for userId: " + userId);
            return Collections.emptyList();
        }

        // Calculate similarity scores based on ratings
        Map<Long, Double> similarityScores = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Integer>> entry : ratingMatrix.entrySet()) {
            Long otherUserId = entry.getKey();
            if (!otherUserId.equals(userId)) {
                Map<Long, Integer> otherUserRatings = entry.getValue();
                double ratingSimilarity = similarityService.computeCosineSimilarity(userRatings, otherUserRatings).get(0L);

                // Adjust similarity using video tags
                Set<String> userTags = videoTagService.getUserTags(userId);
                Set<String> otherUserTags = videoTagService.getUserTags(otherUserId);
                double tagSimilarity = similarityService.computeJaccardSimilarity(userTags, otherUserTags);

                // Combine the two similarities (you can weight them as needed)
                double combinedSimilarity = (ratingSimilarity + tagSimilarity) / 2.0;
                similarityScores.put(otherUserId, combinedSimilarity);
            }
        }

        System.out.println("Similarity scores: " + similarityScores);

        // Predict ratings based on combined similarity scores
        Map<Long, Double> predictedRatings = new HashMap<>();
        for (Map.Entry<Long, Double> entry : similarityScores.entrySet()) {
            Long otherUserId = entry.getKey();
            double similarity = entry.getValue();
            Map<Long, Integer> otherUserRatings = ratingMatrix.get(otherUserId);

            for (Map.Entry<Long, Integer> videoEntry : otherUserRatings.entrySet()) {
                Long videoId = videoEntry.getKey();
                Integer rating = videoEntry.getValue();
                if (!userRatings.containsKey(videoId)) {  // Avoid already rated videos
                    predictedRatings.put(videoId,
                            predictedRatings.getOrDefault(videoId, 0.0) + similarity * rating);
                }
            }
        }

        System.out.println("Predicted ratings: " + predictedRatings);

        // Sort and get the top recommended videos
        return predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

}

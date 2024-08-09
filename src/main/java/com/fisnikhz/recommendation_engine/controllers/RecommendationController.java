package com.fisnikhz.recommendation_engine.controllers;

import com.fisnikhz.recommendation_engine.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<Map.Entry<Long, Double>> getRecommendations(@PathVariable Long userId) {
        return recommendationService.recommendVideos(userId);
    }
}

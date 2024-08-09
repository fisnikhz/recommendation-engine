package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Videos, Long> {

}

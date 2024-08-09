package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.VideoTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTags, Long> {

    @Query("SELECT v.videoId FROM VideoTags v WHERE v.tags LIKE %:tag% AND v.videoId != :videoId")
    List<Long> findRelatedVideosByTag(@Param("tag") String tag, @Param("videoId") Long videoId);

}

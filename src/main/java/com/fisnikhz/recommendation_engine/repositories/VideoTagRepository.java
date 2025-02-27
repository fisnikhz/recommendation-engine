package com.fisnikhz.recommendation_engine.repositories;

import com.fisnikhz.recommendation_engine.entities.VideoTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoTagRepository extends JpaRepository<VideoTags, Long> {

    @Query("SELECT vt.tags FROM VideoTags vt WHERE vt.videoId = :videoId")
    List<String> findTagsByVideoId(Long videoId);
}

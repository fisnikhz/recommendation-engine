package com.fisnikhz.recommendation_engine.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_tags")
public class VideoTags {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "video_id", nullable = false)
 private Long videoId;

 @Column(nullable = false)
 private String tags;

 @Column(name = "created_at", nullable = false)
 private LocalDateTime createdAt;

 @Column(name = "updated_at", nullable = false)
 private LocalDateTime updatedAt;


 // Getters and Setters

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getVideoId() {
  return videoId;
 }

 public void setVideoId(Long videoId) {
  this.videoId = videoId;
 }

 public String getTags() {
  return tags;
 }

 public void setTags(String tags) {
  this.tags = tags;
 }

 public LocalDateTime getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
 }

 public LocalDateTime getUpdatedAt() {
  return updatedAt;
 }

 public void setUpdatedAt(LocalDateTime updatedAt) {
  this.updatedAt = updatedAt;
 }
}

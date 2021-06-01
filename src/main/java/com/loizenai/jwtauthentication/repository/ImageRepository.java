package com.loizenai.jwtauthentication.repository;

import com.loizenai.jwtauthentication.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByName(String name);
    List<ImageModel> findAll();
    List<ImageModel> findByTitleContaining(String title);
    List<ImageModel> findByTagsContaining(String tags);
    List<ImageModel> findByDescriptionContaining(String description);
    List<ImageModel> findByDateGreaterThan(LocalDateTime date);
    List<ImageModel> findByDateLessThan(LocalDateTime date);

    @Query("select m from ImageModel m where " +
            "(?1 is null or upper(m.title) like concat('%', upper(?1), '%')) " +
            "and (?2 is null or upper(m.description) like concat('%', upper(?2), '%')) " +
            "and (?3 is null or upper(m.description) like concat('%', upper(?3), '%')) " +
            "and (?4 is null or m.date >= ?4) " +
            "and (?5 is null or m.date <= ?5)")
    List<ImageModel> searchBy(String title, String description, String tags, Timestamp from, Timestamp to);

    Optional<ImageModel> findTopByOrderByIdDesc();
}
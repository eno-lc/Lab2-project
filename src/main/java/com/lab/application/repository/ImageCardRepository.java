package com.lab.application.repository;

import com.lab.application.entity.ImageCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageCardRepository extends JpaRepository<ImageCard, Long> {

    @Query("select u from ImageCard u " +
            "where lower(u.description) like lower(concat('%', :searchTerm, '%')) ")
    List<ImageCard> search(@Param("searchTerm") String searchTerm);

}

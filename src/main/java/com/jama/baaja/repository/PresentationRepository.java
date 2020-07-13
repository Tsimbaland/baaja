package com.jama.baaja.repository;

import com.jama.baaja.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Presentation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    @Query("select presentation from Presentation presentation where presentation.author.login = ?#{principal.username}")
    List<Presentation> findByAuthorIsCurrentUser();

}

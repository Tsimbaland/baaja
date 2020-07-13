package com.jama.baaja.repository;

import com.jama.baaja.domain.ParagraphIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParagraphIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParagraphIsBodyRepository extends JpaRepository<ParagraphIsBody, Long> {

}

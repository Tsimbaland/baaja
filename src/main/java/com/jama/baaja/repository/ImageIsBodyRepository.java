package com.jama.baaja.repository;

import com.jama.baaja.domain.ImageIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImageIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageIsBodyRepository extends JpaRepository<ImageIsBody, Long> {

}

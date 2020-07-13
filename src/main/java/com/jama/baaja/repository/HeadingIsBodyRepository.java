package com.jama.baaja.repository;

import com.jama.baaja.domain.HeadingIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HeadingIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeadingIsBodyRepository extends JpaRepository<HeadingIsBody, Long> {

}

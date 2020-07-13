package com.jama.baaja.repository;

import com.jama.baaja.domain.Body;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Body entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyRepository extends JpaRepository<Body, Long> {

}
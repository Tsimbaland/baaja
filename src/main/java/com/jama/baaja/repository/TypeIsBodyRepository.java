package com.jama.baaja.repository;

import com.jama.baaja.domain.TypeIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIsBodyRepository extends JpaRepository<TypeIsBody, Long> {

}

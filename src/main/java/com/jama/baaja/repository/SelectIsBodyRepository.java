package com.jama.baaja.repository;

import com.jama.baaja.domain.SelectIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SelectIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectIsBodyRepository extends JpaRepository<SelectIsBody, Long> {

}

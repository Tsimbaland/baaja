package com.jama.baaja.repository;

import com.jama.baaja.domain.BulletsIsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BulletsIsBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulletsIsBodyRepository extends JpaRepository<BulletsIsBody, Long> {

}

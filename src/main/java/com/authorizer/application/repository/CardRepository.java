/*
 * Responsible for Operations of CRUD
 *
 */
package com.authorizer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.authorizer.application.domain.card.Card;

/**
 * 
 * @author Aline Divino
 *
 */
public interface CardRepository extends JpaRepository<Card, Long>{

  /**
   * @param valueOf
   * @return
   */
  Card findByNumbercard(String valueOf);

  
}

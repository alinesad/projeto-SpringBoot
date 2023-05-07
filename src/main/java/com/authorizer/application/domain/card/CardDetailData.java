/*
 * Record for Detail Card
 */
package com.authorizer.application.domain.card;

import java.math.BigDecimal;

/**
 * Record for Detail Card
 * @author Aline
 *
 */
public record CardDetailData(Long id, String numberCard, BigDecimal balance ) {
  
  public CardDetailData(Card card) {

    this(card.getId(), card.getNumbercard(),  card.getBalance());
    
}

}

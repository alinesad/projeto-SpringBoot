/**
 * 
 */
package com.authorizer.application.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.authorizer.application.domain.card.Card;
import com.authorizer.application.domain.security.AuthorizeTransactionData;
import com.authorizer.application.infra.validators.CardValidator;
import com.authorizer.application.repository.CardRepository;

/**
 * Responsible Encapsulate the business rules
 * @author Aline Divino
 *
 */
@Service
public class AuthorizeTransactionService {

  @Autowired
  private CardRepository cardRepository;
  
  @Autowired
  private List<CardValidator> validators;

  public Card debitTransaction(AuthorizeTransactionData json) {

    validators.forEach(v -> v.validator(json));
    
    Card card = (Card) cardRepository.findByNumbercard(json.numbercard());
    
    BigDecimal newBalance = card.getBalance().subtract(json.debitAmount());
    card.setBalance(newBalance);
    
    return card;

  }

}

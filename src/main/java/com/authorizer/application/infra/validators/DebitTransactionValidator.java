/**
 * 
 */
package com.authorizer.application.infra.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.authorizer.application.domain.card.Card;
import com.authorizer.application.domain.security.AuthorizeTransactionData;
import com.authorizer.application.infra.exception.ValidationException;
import com.authorizer.application.repository.CardRepository;

/**
 * @author Aline Divino
 *
 */
@Component
public class DebitTransactionValidator implements CardValidator {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public void validator(AuthorizeTransactionData json) {

    Card card = (Card) cardRepository.findByNumbercard(json.numbercard());

    if (card == null) {

      throw new ValidationException("Card Number not found!");
    }


    if (!bCryptPasswordEncoder.matches(json.password(), card.getPassword())) {

      throw new ValidationException("Password invalid!");
    }

    if (json.debitAmount() != null) {

      if (json.debitAmount().compareTo(card.getBalance()) == 1) {

        throw new ValidationException("Insufficient funds! ");

      }

    }

  }
}

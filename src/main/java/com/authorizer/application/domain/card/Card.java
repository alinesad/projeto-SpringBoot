/*
 * DTO for Card
 */
package com.authorizer.application.domain.card;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for Card
 * 
 * @author Aline
 *
 */
@Table(name = "cards")
@Entity(name = "Card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String numbercard;
  private String password;
  private BigDecimal balance;



  /**
   * @param json
   */
  public Card(@Valid CardData json) {
    this.numbercard = json.numbercard();
    this.password = json.password();
    this.balance = new BigDecimal(500.00);
  }


}

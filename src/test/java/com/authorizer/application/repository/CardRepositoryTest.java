/**
 * 
 */
package com.authorizer.application.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.authorizer.application.domain.card.Card;
import com.authorizer.application.domain.card.CardData;

/**
 * @author Aline
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CardRepositoryTest {
  
  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private TestEntityManager em;
  
 

  /**
   * Test method for {@link com.authorizer.application.repository.CardRepository#findByNumbercard(java.lang.String)}.
   */
  @Test
  @DisplayName("Test Find By Numbercard")
  void testFindByNumbercard() {
    
    String numbercard = "12345";
    String pass = "Pass123";
    
    createCard(numbercard,pass);
    
    Card card = (Card)cardRepository.findByNumbercard(numbercard);
    
    assertEquals(numbercard, card.getNumbercard());
  }
  
  
  private void createCard(String numbercard, String password) {

    CardData cardData = new CardData(numbercard,password);
    em.persist(new Card(cardData));
  }

}

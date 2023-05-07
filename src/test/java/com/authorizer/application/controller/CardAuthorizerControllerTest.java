/**
 * 
 */
package com.authorizer.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.authorizer.application.domain.card.CardData;
import com.authorizer.application.domain.security.AuthorizeTransactionData;

/**
 * @author Aline
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CardAuthorizerControllerTest {
   
  
  @Autowired
  private JacksonTester<CardData> cardDataJson;
  
  
  @Autowired
  private JacksonTester<AuthorizeTransactionData> authorizeTransactionDataJson;
  
  @Autowired
  private MockMvc mockMvc;
  


  /**
   * Test method for {@link com.authorizer.application.controller.CardAuthorizerController#AuthorizeTransaction(com.authorizer.application.domain.security.AuthorizeTransactionData)}.
   * @throws Exception 
   * @throws IOException 
   */
  @Test
  @DisplayName("Test Authorize Transaction")
  @WithMockUser
  void testAuthorizeTransaction() throws IOException, Exception {
    
    
    double doubleRandomNumber = Math.random() * 5;
    int randomNumber = (int)doubleRandomNumber;
    
    String numberCard = "23432"+randomNumber;
    
    String pass ="565745";
    
    //Create New Card Credit
    MockHttpServletResponse response = mockMvc
        .perform(post("/card").contentType(MediaType.APPLICATION_JSON)
            .content(cardDataJson
                .write(new CardData(numberCard, pass)).getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    
    
    //Insufficient balance test
    BigDecimal amount = new BigDecimal(1500);
   
    
    response = mockMvc
        .perform(post("/card/debit").contentType(MediaType.APPLICATION_JSON)
            .content(authorizeTransactionDataJson
                .write(new AuthorizeTransactionData(numberCard, pass,amount)).getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    
    //Test debit with sufficient balance
    amount = new BigDecimal(50);
    
    response = mockMvc
        .perform(post("/card/debit").contentType(MediaType.APPLICATION_JSON)
            .content(authorizeTransactionDataJson
                .write(new AuthorizeTransactionData(numberCard, pass,amount)).getJson()))
        .andReturn().getResponse();
    
    
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    
    
    
  }

}

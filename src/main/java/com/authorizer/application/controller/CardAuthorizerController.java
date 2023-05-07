/*
 * Controller responsible for authorizing cards
 * 
 * * Data Base:  use Cards_api; 
 *   show tables;
 * 
 * https://springdoc.org/v2/
 * 
 * http://localhost:8080/swagger-ui.html 
 * http://localhost:8080/v3/api-docs
 *
 */
package com.authorizer.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.authorizer.application.domain.card.Card;
import com.authorizer.application.domain.card.CardData;
import com.authorizer.application.domain.card.CardDetailData;
import com.authorizer.application.domain.security.AuthorizeTransactionData;
import com.authorizer.application.repository.CardRepository;
import com.authorizer.application.service.AuthorizeTransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


/**
 * Controller responsible for authorizing cards
 * 
 * @author Aline Divino
 * 
 */
@RestController
@RequestMapping("/card")
public class CardAuthorizerController {


  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AuthorizeTransactionService authorizeTransactionService;

  private UriComponentsBuilder uriComponentsBuilder;



  @PostMapping()
  @Transactional
  public ResponseEntity<CardDetailData> cadastrar(@RequestBody @Valid CardData json,
      UriComponentsBuilder uriBuilder) {

    Card card = new Card(json);

    String encriptedPasswd = bCryptPasswordEncoder.encode(json.password());

    card.setPassword(encriptedPasswd);

    cardRepository.save(card);

    uriComponentsBuilder = uriBuilder.path("/view/{id}");
    UriComponents uriComponents = uriComponentsBuilder.buildAndExpand(card.getNumbercard());

    return ResponseEntity.created(uriComponents.toUri()).body(new CardDetailData(card));

  }


  @GetMapping("/view/{id}")
  @Transactional
  public ResponseEntity<CardDetailData> detalhar(@PathVariable String numbercard) {

    Card card = (Card) cardRepository.findByNumbercard(numbercard);

    return ResponseEntity.ok(new CardDetailData(card));

  }


  @PostMapping("/debit")
  @Transactional
  public ResponseEntity<CardDetailData> AuthorizeTransaction(
      @RequestBody @Valid AuthorizeTransactionData json) {

    Card card = authorizeTransactionService.debitTransaction(json);
    
    return ResponseEntity.ok(new CardDetailData(card));
  }


}

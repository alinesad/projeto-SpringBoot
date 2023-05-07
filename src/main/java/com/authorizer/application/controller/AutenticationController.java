/**
 * * Data Base:  use Cards_api; 
 *   show tables;
 * 
 * https://springdoc.org/v2/
 * 
 * http://localhost:8080/swagger-ui.html 
 * http://localhost:8080/v3/api-docs
 */
package com.authorizer.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.authorizer.application.domain.security.JWTTokenData;
import com.authorizer.application.domain.user.DetaildeUserData;
import com.authorizer.application.domain.user.User;
import com.authorizer.application.domain.user.UserAuthenticationData;
import com.authorizer.application.infra.security.TokenService;
import com.authorizer.application.repository.UserRepository;
import com.authorizer.application.service.UserTransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

/**
 * @author Aline Divino
 *
 */
@RestController
@RequestMapping("/user")
public class AutenticationController {

  /*
   * Inject Spring Class that triggers the Authentication process
   */
  @Autowired
  private AuthenticationManager authenticationManager;


  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserTransactionService userTransactionService;
  

  private UriComponentsBuilder uriComponentsBuilder;


  @PostMapping("/login")
  public ResponseEntity<JWTTokenData> loginIn(@RequestBody @Valid UserAuthenticationData userData) {

    Authentication authenticationToken =
        new UsernamePasswordAuthenticationToken(userData.login(), userData.pass());
    Authentication authenticate = authenticationManager.authenticate(authenticationToken);

    String tokenJwt = tokenService.generateToken((User) authenticate.getPrincipal());
    return ResponseEntity.ok(new JWTTokenData(tokenJwt));

  }


  @GetMapping("/list")
  public ResponseEntity<Page<DetaildeUserData>> userList(
      @PageableDefault(size = 10, sort = {"login"}) Pageable pageable) {

    Page<DetaildeUserData> page = userRepository.findAll(pageable).map(DetaildeUserData::new);

    return ResponseEntity.ok(page);

  }

  @GetMapping("/{id}")
  @Transactional
  public ResponseEntity<DetaildeUserData> detalhar(@PathVariable Long id) {
    User user = userRepository.getReferenceById(id);

    return ResponseEntity.ok(new DetaildeUserData(user));

  }

  @PostMapping("/new")
  @Transactional
  public ResponseEntity<?> newUser(@RequestBody @Valid UserAuthenticationData json,
      UriComponentsBuilder uriBuilder) {
   
    User user = userTransactionService.newUSerTransaction(json);

    uriComponentsBuilder = uriBuilder.path("/login/{id}");

    UriComponents uriComponents = uriComponentsBuilder.buildAndExpand(user.getId());

    // HTTP Code 201 -
    return ResponseEntity.created(uriComponents.toUri()).body(new DetaildeUserData(user));
   
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> excluir(@PathVariable Long id) {

    userRepository.deleteById(id);

    return ResponseEntity.noContent().build();

  }

}

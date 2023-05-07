/**
 * 
 */
package com.authorizer.application.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.authorizer.application.domain.user.User;
import com.authorizer.application.domain.user.UserAuthenticationData;
import com.authorizer.application.infra.validators.UserValidator;
import com.authorizer.application.repository.UserRepository;

/**
 * Responsible Encapsulate the business rules 
 * @author Aline Divino
 *
 */
@Service
public class UserTransactionService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private List<UserValidator> validators;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  
  public User newUSerTransaction(UserAuthenticationData json) {

    validators.forEach(v -> v.validator(json));
    
    User user = new User();

    String encriptedPasswd = bCryptPasswordEncoder.encode(json.pass());

    user.setLogin(json.login());
    user.setPass(encriptedPasswd);

    userRepository.save(user);
    
    return user;

  }
  
  

}

/**
 * 
 */
package com.authorizer.application.infra.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.authorizer.application.domain.user.User;
import com.authorizer.application.domain.user.UserAuthenticationData;
import com.authorizer.application.infra.exception.ValidationException;
import com.authorizer.application.repository.UserRepository;

/**
 * @author Aline Divino
 *
 */
@Component
public class NewUserValidator  implements UserValidator{
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public void validator(UserAuthenticationData json) {
    
    Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{6,8}$");
    
    Matcher matcher = pattern.matcher(json.pass());
    
    if (!matcher.find()) {
      throw new ValidationException("Password must have at least one uppercase character, one lowercase character and at least one digit");
    }
    
    User user = (User) userRepository.findByLogin(json.login());

    if(user !=null) {
      throw new ValidationException("User login already exists!");
    }

  }

}

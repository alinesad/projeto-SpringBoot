/**
 * 
 */
package com.authorizer.application.infra.validators;

import com.authorizer.application.domain.user.UserAuthenticationData;

/**
 * @author Aline
 *
 */
public interface UserValidator {
  
  void validator(UserAuthenticationData json);

}

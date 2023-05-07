/**
 * 
 */
package com.authorizer.application.infra.validators;

import com.authorizer.application.domain.security.AuthorizeTransactionData;

/**
 * @author Aline Divino
 *
 */
public interface CardValidator {
  
  void validator(AuthorizeTransactionData json);

}

/**
 * 
 */
package com.authorizer.application.domain.user;

/**
 * @author Aline
 *
 */
public record DetaildeUserData(Long id,String login) {
  
  public DetaildeUserData(User user) {

    this(user.getId(),  user.getLogin());
    
}

}

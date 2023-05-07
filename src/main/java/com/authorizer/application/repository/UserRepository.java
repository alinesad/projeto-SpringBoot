/**
 * 
 */
package com.authorizer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.authorizer.application.domain.user.User;

/**
 * @author Aline
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{
  
  UserDetails findByLogin(String login);

}

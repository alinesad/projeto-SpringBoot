/*
 * @author Aline
 *
 */
package com.authorizer.application.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.authorizer.application.repository.UserRepository;

/**
 * 
 * @author Aline Divino
 *
 */
@Service
public class AuthenticationService implements UserDetailsService{
  
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return repository.findByLogin(username);
  }

}

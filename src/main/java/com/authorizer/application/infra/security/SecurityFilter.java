/*
 * Responsible for making doFilterInternal be executed once per request
 * 
 *
 */
package com.authorizer.application.infra.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.authorizer.application.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Responsible for making doFilterInternal be executed once per request
 * @author Aline Divino
 *
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{
  
  @Autowired
  private TokenService tokenService;
  
  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

      String tokenJwt = recuperarToken(request);
      
      if (tokenJwt != null) {
          
          String subject = tokenService.getSubject(tokenJwt);
          
          UserDetails userDetails = userRepository.findByLogin(subject);
          
          Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication );
          
      }
      filterChain.doFilter(request, response);

  }

  private String recuperarToken(HttpServletRequest request) {

      String header = request.getHeader("Authorization");
      if (header != null) {
          return header.replace("Bearer ", "");
      }

      return null;
  }

}

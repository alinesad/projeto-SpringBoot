/**
 * 
 */
package com.authorizer.application.domain.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Record for User Authentication Data
 * @author Aline
 *
 */
public record UserAuthenticationData(
    @NotBlank(message = "{login.requered}") 
    String login, 
    @NotBlank(message = "{pass.requered}") 
    String pass) {

}

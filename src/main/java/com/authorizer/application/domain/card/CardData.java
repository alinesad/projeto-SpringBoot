/*
 * Record for USer Authentication Data
 */
package com.authorizer.application.domain.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Record for Card
 * @author Aline Divino
 *
 */
public record CardData( 
    @NotBlank(message = "{numbercard.requered}") 
    @Pattern(regexp = "\\d{6}", message = "{numbercard.lenght}")
    String numbercard, 
    @NotBlank(message = "{pass.requered}") 
    @Pattern(regexp = "\\d{6}", message = "{pass.lenght}")
    String password) {

}

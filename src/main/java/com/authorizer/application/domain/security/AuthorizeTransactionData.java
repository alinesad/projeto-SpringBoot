/**
 * 
 */
package com.authorizer.application.domain.security;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Aline Divino
 *
 */
public record AuthorizeTransactionData(
    @NotBlank(message = "{numbercard.requered}") 
    String numbercard, 
    @NotBlank(message = "{pass.requered}") 
    String password,
    BigDecimal debitAmount) {

}

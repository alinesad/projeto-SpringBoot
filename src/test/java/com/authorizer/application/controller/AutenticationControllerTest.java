/**
 * 
 */
package com.authorizer.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.authorizer.application.domain.user.UserAuthenticationData;

/**
 * @author Aline
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AutenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JacksonTester<UserAuthenticationData> userAuthenticationDataJson;


  /**
   * Test method for
   * {@link com.authorizer.application.controller.AutenticationController#loginIn(com.authorizer.application.domain.user.UserAuthenticationData)}.
   * 
   * @throws Exception
   */
  @Test
  @DisplayName("test LoginIn without Parameters")
  @WithMockUser
  void testLoginInwithoutParameters() throws Exception {

    MockHttpServletResponse response =
        mockMvc.perform(post("/user/login")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

  }



  /**
   * Test method for
   * {@link com.authorizer.application.controller.AutenticationController#newUser(com.authorizer.application.domain.user.UserAuthenticationData, org.springframework.web.util.UriComponentsBuilder)}.
   * 
   * @throws Exception
   */
  @Test
  @WithMockUser
  void testNewUser() throws Exception {


    MockHttpServletResponse response = mockMvc
        .perform(post("/user/new").contentType(MediaType.APPLICATION_JSON)
            .content(userAuthenticationDataJson
                .write(new UserAuthenticationData("userNew05", "Power345")).getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());


  }

}

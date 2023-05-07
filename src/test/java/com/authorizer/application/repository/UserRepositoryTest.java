/**
 * 
 */
package com.authorizer.application.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.authorizer.application.domain.user.User;

/**
 * @author Aline
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  
  @Autowired
  private TestEntityManager em;
  

  /**
   * Test method for
   * {@link com.authorizer.application.repository.UserRepository#findByLogin(java.lang.String)}.
   */
  @Test
  @DisplayName("Test Find By Login")
  void testFindByLogin() {
    
    String login = "userTester";
    String pass = "User123";
    
    createUser(login,pass);
    
    User user = (User) userRepository.findByLogin(login);
    
    assertEquals(login, user.getLogin());

  }


  private void createUser(String login, String pass) {

    em.persist(new User(null, login, pass));
  }

}

package com.example.SocialMediaJava;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("rohan890@gmail.com");
        user.setPassword("rohan123");
        user.setName("Rohan");

        User saveuser = repo.save(user);

        entityManager.find(User.class,saveuser.getId());


    }

    @Test
    public void testFindUserByEmail() {

        String email = "abc@abc.net";
        User user = repo.findByEmail(email);

        // assertThat(user).isNotNull();
    }
}

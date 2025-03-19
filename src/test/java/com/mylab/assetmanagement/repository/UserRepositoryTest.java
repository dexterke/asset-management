package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * These tests are completely pointless
 */
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        testUserEntity = new UserEntity();
        testUserEntity.setName("name");
        testUserEntity.setEmail("email");
        testUserEntity.setPassword("password");
        testUserEntity.setPhone("00");
    }

    @Test
    void findByEmailAndPassword() {
        Optional<UserEntity> result = userRepository.findByEmailAndPassword(
                "email", "password");
        assertThat(result).isEmpty();

        // when exists
        userRepository.save(testUserEntity);
        result = userRepository.findByEmailAndPassword("email", "password");
        assertThat(result).isPresent();
        assertThat(result.get().getPassword()).isEqualTo("password");
    }

    @Test
    void findByEmail() {
        Optional<UserEntity> result = userRepository.findByEmail("email");
        assertThat(result).isEmpty();

        // when exists
        userRepository.save(testUserEntity);
        result = userRepository.findByEmail("email");
        assertThat(result).isPresent();
    }

    @Test
    void findByIdTest() {
        Optional<UserEntity> result = userRepository.findById(0L);
        assertThat(result).isEmpty();
    }

}
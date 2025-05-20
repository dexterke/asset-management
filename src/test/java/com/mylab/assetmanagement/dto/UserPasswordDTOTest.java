package com.mylab.assetmanagement.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserPasswordDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void login_shouldPass() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("Valid123");
        dto.setUsername("user");

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
        assertFalse(dto.getUsername().isEmpty());
        assertFalse(dto.getPassword().isEmpty());
    }

    @Test
    public void nullPassword_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword(null);

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Expected validation error for null password");

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().equals("Password is mandatory")));
    }

    @Test
    public void shortPassword_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("Ab1");

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Expected validation error for short password");

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("Password length")));
    }

    @Test
    public void longPassword_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("A1" + "x".repeat(20));

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Expected validation error for long password");

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("Password length")));
    }

    @Test
    public void passwordMissingDigit_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("PasswordOnly");

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("must contain at least")));

    }

    @Test
    public void passwordMissingUppercase_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("password123");

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("must contain at least")));
    }

    @Test
    public void passwordMissingLowercase_shouldFail() {
        UserPasswordDTO dto = new UserPasswordDTO();
        dto.setPassword("PASSWORD123");

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("must contain at least")));
    }
}
package com.mylab.assetmanagement.service;

import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.dto.UserRegistrationDTO;

import java.util.List;

/*
 Factory design pattern: one interface can have multiple implementations.
 Depending on which implementing class creates the object
        ==>> that particular class functionality gets executed.
 */
public interface UserService {

    UserDTO register(UserRegistrationDTO userDTO);

    UserDTO login(String username, String password);

    List<UserDTO> getAllUsers();

    Long deleteUser(Long id);

    UserDTO getUser(Long id);
}

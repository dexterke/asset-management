package com.mylab.assetmanagement.controller;

import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.dto.UserLoginDTO;
import com.mylab.assetmanagement.dto.UserRegistrationDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;
import com.mylab.assetmanagement.service.RoleService;
import com.mylab.assetmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC (Model View Controller) design pattern
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String DB_URL_LOG = "DB_URL: '{}'";

    /**
     * Read value from config file
     */
    @Value("${spring.datasource.url:}")
    private String dbUrl;

    /**
     * Dependency injection, controller-class level variable of the userService interface
     */
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Swagger-UI input fields, set to @Validated instead of @RequestBody
     *
     * @Parameter(name = "email", description = "User email", required = true, example = "user@email.com")
     * @Parameter(name = "password", description = "User password", required = true, example = "password")
     */
    @Operation(summary = "login", description = "User login")
    @PostMapping(value = "/login", path = "/login", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDTO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        UserDTO userDTO = userLoginDTO;
        userDTO = userService.login(userDTO.getUsername(), userDTO.getPassword());
        log.info(DB_URL_LOG, dbUrl);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "getUser", description = "List a user")
    @GetMapping(value = "/getUser/{id}", path = "/getUser/{id}", produces = {"application/json"})
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        log.info(DB_URL_LOG, dbUrl);
        return new ResponseEntity<>(userDTO, userDTO == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @Operation(summary = "getAllUsers", description = "This endpoint is used for listing of all users")
    @GetMapping(value = "/getAllUsers", path = "/getAllUsers", produces = {"application/json"})
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        log.info(DB_URL_LOG, dbUrl);
        ResponseEntity<List<UserDTO>> responseEntity =
                new ResponseEntity<>(userList, userList.isEmpty() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        return responseEntity;
    }

    @Operation(summary = "register", description = "User registration")
    @PostMapping(value = "/register", path = "/register", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDTO> register(@RequestBody @Validated UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = userService.register(userRegistrationDTO);
        log.info(DB_URL_LOG, dbUrl);
        ResponseEntity<UserDTO> responseEntity = new ResponseEntity<>(userDTO, userDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED);
        return responseEntity;
    }

    @Operation(summary = "deleteUser", description = "Delete a user")
    @DeleteMapping(value = "/deleteUser/{userId}", path = "/deleteUser/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        log.info(DB_URL_LOG, dbUrl);
        ResponseEntity<UserDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    @Operation(summary = "getUserRoles", description = "List roles for a user")
    @GetMapping(value = "/{userId}/roles", path = "/{userId}/roles", produces = {"application/json"})
    public ResponseEntity<List<UserRoleDTO>> getUserRoles(@PathVariable Long userId) {
        List<UserRoleDTO> userRoleDTOList = roleService.getRolesForUserId(userId);
        log.info(DB_URL_LOG, dbUrl);
        return new ResponseEntity<>(userRoleDTOList, userRoleDTOList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @Operation(summary = "assignRole", description = "Assign role to a user")
    @PostMapping(value = "/{userId}/roles/{roleId}", path = "/{userId}/roles/{roleId}", produces = {"application/json"})
    public ResponseEntity<UserRoleDTO> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        UserRoleDTO newRoleForUserId = roleService.setRoleForUserDto(userId, roleId);
        log.info(DB_URL_LOG, dbUrl);
        return new ResponseEntity<>(newRoleForUserId, newRoleForUserId == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @Operation(summary = "deleteUserRole", description = "Delete role for a user")
    @DeleteMapping(value = "/{userId}/roles/{roleId}", path = "/{userId}/roles/{roleId}", produces = {"application/json"})
    public ResponseEntity<UserRoleDTO> deleteUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
        roleService.deleteUserRole(userId, roleId);
        log.info(DB_URL_LOG, dbUrl);
        ResponseEntity<UserRoleDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}

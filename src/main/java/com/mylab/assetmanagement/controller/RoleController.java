package com.mylab.assetmanagement.controller;


import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.service.RoleService;
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


@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);
    private static final String DB_URL_LOG = "DB_URL: '{}'";

    /**
     * Read value from config file
     */
    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Autowired
    private RoleService roleService;


    @Operation(summary = "getRole", description = "List a role")
    @GetMapping(value = "/getRole/{id}", path = "/getRole/{id}", produces = {"application/json"})
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        RoleDTO roleDTO = roleService.getRole(id);
        log.debug(DB_URL_LOG, dbUrl);
        return new ResponseEntity<>(roleDTO, roleDTO == null ?
                                             HttpStatus.NOT_FOUND :
                                             HttpStatus.OK);
    }

    @Operation(summary = "getAllRoles", description = "List all roles")
    @GetMapping(value = "/getAllRoles", path = "/getAllRoles", produces = {"application/json"})
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roleList = roleService.getAllRoles();
        log.debug(DB_URL_LOG, dbUrl);
        ResponseEntity<List<RoleDTO>> responseEntity =
                new ResponseEntity<>(roleList, roleList == null ?
                                               HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        return responseEntity;
    }

    @Operation(summary = "addRole", description = "Role registration")
    @PostMapping(value = "/addRole", path = "/addRole", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<RoleDTO> register(@RequestBody @Validated RoleDTO roleDTO) {
        roleDTO = roleService.addRole(roleDTO);
        log.debug(DB_URL_LOG, dbUrl);
        ResponseEntity<RoleDTO> responseEntity = new ResponseEntity<>(roleDTO
                , roleDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR :
                  HttpStatus.CREATED);
        return responseEntity;
    }

    @Operation(summary = "deleteRole", description = "Delete a role")
    @DeleteMapping(value = "/deleteRole/{id}", path = "/deleteRole/{id}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        log.debug(DB_URL_LOG, dbUrl);
        ResponseEntity<RoleDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

}

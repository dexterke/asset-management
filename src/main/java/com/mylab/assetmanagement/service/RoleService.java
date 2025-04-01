package com.mylab.assetmanagement.service;

import java.util.List;
import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;

public interface RoleService {

    RoleDTO addRole(RoleDTO roleDTO);

    RoleDTO getRole(Long id);

    List<RoleDTO> getAllRoles();

    Long deleteRole(Long id);

    List<String> getRolesNamesForUserId(Long id);

    List<UserRoleDTO> getRolesForUserId(Long id);

    UserRoleDTO setRoleForUserDto(Long userId, Long rolesIDForUser);

    void deleteUserRole(Long userId, Long roleId);
}

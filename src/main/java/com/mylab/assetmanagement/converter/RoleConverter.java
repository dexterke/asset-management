package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;
import com.mylab.assetmanagement.entity.RoleEntity;

import com.mylab.assetmanagement.entity.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public RoleEntity convertRoleDTOtoEntity(RoleDTO roleDTO) {
        RoleEntity entity = new RoleEntity();
        entity.setName(roleDTO.getName());
        entity.setDescription(roleDTO.getDescription());
        return entity;
    }

    public RoleDTO convertRoleEntityToDTO(RoleEntity roleEntity) {
        RoleDTO dto = new RoleDTO();
        dto.setId(roleEntity.getId());
        dto.setName(roleEntity.getName());
        dto.setDescription(roleEntity.getDescription());
        return dto;
    }

    public UserRoleDTO convertUserRoleEntityToDTO(UserRoleEntity entity) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(entity.getId());
        dto.setInfo(entity.getInfo());
        dto.setRoleId(entity.getRoleEntity().getId());
        dto.setUserId(entity.getUserEntity().getId());
        dto.setUserName(entity.getUserEntity().getUsername());
        dto.setRoleName(entity.getRoleEntity().getName());
        return dto;
    }

}



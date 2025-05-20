package com.mylab.assetmanagement.converter;
import static org.assertj.core.api.Assertions.assertThat;

import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;
import com.mylab.assetmanagement.entity.RoleEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleConverterTest {

    RoleConverter roleConverter;
    RoleDTO roleDTO;
    RoleEntity roleEntity;

    UserEntity userEntity;
    UserRoleEntity userRoleEntity;

    @BeforeEach
    void setUp() {
        roleConverter = new RoleConverter();
        roleDTO = new RoleDTO();

        roleEntity = new RoleEntity();
        roleEntity.setId(999L);
        roleEntity.setName("testRole");
        roleEntity.setDescription("test");

        userEntity = new UserEntity();
        userEntity.setId(999L);
        userEntity.setName("test");

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(999L);
        userRoleEntity.setUserEntity(userEntity);
        userRoleEntity.setRoleEntity(roleEntity);

    }

    @Test
    void convertRoleDTOtoEntity() {
        roleDTO = roleConverter.convertRoleEntityToDTO(roleEntity);
        RoleEntity entity = roleConverter.convertRoleDTOtoEntity(roleDTO);

        assertThat(entity).isNotNull()
                           .usingRecursiveComparison().ignoringFields("id")
                           .isEqualTo(roleDTO);
    }

    @Test
    void convertRoleEntityToDTO() {
        roleDTO = roleConverter.convertRoleEntityToDTO(roleEntity);
        assertThat(roleDTO).isNotNull()
                               .usingRecursiveComparison()
                               .isEqualTo(roleEntity);
    }

    @Test
    void convertUserRoleEntityToDTO() {
        UserRoleDTO userRoleDTO = roleConverter.convertUserRoleEntityToDTO(userRoleEntity);
        assertThat(userRoleDTO).isNotNull()
                           .usingRecursiveComparison()
                               .ignoringFields("roleId", "roleName", "userName", "userId")
                           .isEqualTo(userRoleEntity);

    }
}
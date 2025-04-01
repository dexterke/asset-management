package com.mylab.assetmanagement.service;

import com.mylab.assetmanagement.converter.RoleConverter;
import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;
import com.mylab.assetmanagement.entity.RoleEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.entity.UserRoleEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.repository.RoleRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.repository.UserRoleRepository;
import com.mylab.assetmanagement.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleConverter roleConverter;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    private UserEntity testUserEntity;

    private RoleEntity roleEntity;

    private RoleDTO roleDTO;

    private UserRoleEntity userRoleEntity;

    private UserRoleDTO userRoleDTO;


    static void setPrivateFieldAccessible(Object target, String fieldName,
                                          Object value) {
        try {
            Field privateField = target.getClass().getDeclaredField(fieldName);
            privateField.setAccessible(true);
            privateField.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        testUserEntity = new UserEntity();
        testUserEntity.setId(100L);
        testUserEntity.setName("name");
        testUserEntity.setPassword("1234567890");
        testUserEntity.setPhone("+00");
        testUserEntity.setEmail("email@mail");

        roleDTO = new RoleDTO();
        roleDTO.setName("test");
        roleDTO.setDescription("test");

        roleEntity = new RoleEntity();
        roleEntity.setDescription("test");
        roleEntity.setName("test");
        roleEntity.setId(999L);

        userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserName("test");
        userRoleDTO.setInfo("test");
        userRoleDTO.setRoleName("test");

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setInfo("test");

        setPrivateFieldAccessible(roleService, "roleRepository", roleRepository);
        setPrivateFieldAccessible(roleService, "userRoleRepository", userRoleRepository);
        setPrivateFieldAccessible(roleService, "userRepository", userRepository);
        setPrivateFieldAccessible(roleService, "roleConverter", roleConverter);

    }

    @Test
    void addRoleTest() {
        // When role exists
        given(roleRepository.findRoleByName(ArgumentMatchers.any())).willReturn(Optional.of(roleEntity));
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class,
                                    () -> {
                                            roleService.addRole(roleDTO);
                                        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertThat(errMsG).contains("Role already exists");

        // When not found
        given(roleRepository.findRoleByName(ArgumentMatchers.any())).willReturn(Optional.empty());
        RoleDTO testRoleDTO = roleService.addRole(roleDTO);
        assertThat(testRoleDTO).isNotNull().usingRecursiveComparison().isEqualTo(roleDTO);
    }

    @Test
    void getRoleTest() {
        Optional<RoleEntity> byId =
                roleRepository.findRoleById(ArgumentMatchers.anyLong());
        assertThat(byId).isEmpty();

        // When not found
        given(roleRepository.findRoleById(ArgumentMatchers.any())).willReturn(Optional.empty());
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
                    roleService.getRole(ArgumentMatchers.anyLong());
        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertThat(errMsG).contains("Role not found");

        // When exists
        given(roleRepository.findRoleById(ArgumentMatchers.any())).willReturn(Optional.of(roleEntity));
        RoleDTO testRoleDTO = roleService.getRole(ArgumentMatchers.anyLong());
        assertThat(testRoleDTO).isNotNull().usingRecursiveComparison().ignoringFields("id").isEqualTo(roleDTO);

    }

    @Test
    void getAllRolesTest() {
        given(roleRepository.findAllRoles()).willReturn(new ArrayList<>());
        List<RoleDTO> roles = roleService.getAllRoles();
        assertThat(roles).isNotNull().isInstanceOf(List.class);
    }

    @Test
    void deleteRoleTest() {
        // when exists
        given(roleRepository.findRoleById(ArgumentMatchers.anyLong())).willReturn(Optional.of(roleEntity));
        Long roleId = roleService.deleteRole(roleEntity.getId());
        assertThat(roleId).isNotNull().isEqualTo(roleEntity.getId());

        //when not exist
        given(roleRepository.findRoleById(ArgumentMatchers.anyLong())).willReturn(Optional.empty());
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
                    roleService.deleteRole(1L);
                });
        String msg = thrown.getErrors().get(0).getMessage();
        assertThat(msg).contains("Role does not exists");
    }

    @Test
    void getRolesNamesForUserIdTest() {
        // when not found
        given(userRoleRepository.findAllByUserEntityId(ArgumentMatchers.anyLong())).willReturn(new ArrayList<>());
        List<String> userRoleList = roleService.getRolesNamesForUserId(1l);
        assertThat(userRoleList.isEmpty());

        // when found
        List<UserRoleEntity> users = new ArrayList<>();
        userRoleEntity.setUserEntity(testUserEntity);
        userRoleEntity.setRoleEntity(roleEntity);
        userRoleEntity.setId(100L);
        users.add(userRoleEntity);
        given(userRoleRepository.findAllByUserEntityId(ArgumentMatchers.anyLong())).willReturn(users);
        userRoleList = roleService.getRolesNamesForUserId(1l);
        assertThat(userRoleList.get(0)).isNotNull().contains(userRoleEntity.getInfo());

    }

    @Test
    void getRolesForUserIdTest() {
        // When not found
        given(userRoleRepository.findAllByUserEntityId(ArgumentMatchers.anyLong())).willReturn(new ArrayList<>());
        List<UserRoleDTO> roles = roleService.getRolesForUserId(999L);
        assertThat(roles.isEmpty());

        // when found
        List<UserRoleEntity> users = new ArrayList<>();
        userRoleEntity.setUserEntity(testUserEntity);
        userRoleEntity.setRoleEntity(roleEntity);
        userRoleEntity.setId(100L);
        users.add(userRoleEntity);
        given(userRoleRepository.findAllByUserEntityId(ArgumentMatchers.anyLong())).willReturn(users);
        roles = roleService.getRolesForUserId(100L);
        assertThat(roles.get(0)).isInstanceOf(UserRoleDTO.class);

    }

    @Test
    void setRoleForUserDtoTest() {
        // when user and role do not exist
        UserRoleDTO roleDTO = roleService.setRoleForUserDto(100L, 999L);
        assertThat(roleDTO).isNull();

    }

    @Test
    void deleteUserRoleTest() {
        // Not found
        given(userRepository.findById(ArgumentMatchers.any())).willReturn(Optional.empty());
        given(userRoleRepository.findById(ArgumentMatchers.any())).willReturn(Optional.empty());

        BusinessException thrown = assertThrowsExactly(
                BusinessException.class, () -> {
                    roleService.deleteUserRole(100L, 999L);
                });
        String  msg = thrown.getErrors().get(0).getMessage();
        assertThat(msg.contains("User role does not exists"));

        userRoleEntity.setUserEntity(testUserEntity);
        userRoleEntity.setRoleEntity(roleEntity);
        userRoleEntity.setId(999L);
        given(userRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(testUserEntity));
        given(userRoleRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(userRoleEntity));
        Optional<UserEntity> userEnt = userRepository.findById(100L);
        Optional<UserRoleEntity> userRoleEnt = userRoleRepository.findById(999L);
        roleService.deleteUserRole(100L, 999L);
        assertThat(userEnt).isPresent();
        assertThat(userRoleEnt).isPresent();

    }
}
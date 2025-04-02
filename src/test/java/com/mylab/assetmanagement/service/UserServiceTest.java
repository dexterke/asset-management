package com.mylab.assetmanagement.service;

import com.mylab.assetmanagement.converter.RoleConverter;
import com.mylab.assetmanagement.converter.UserConverter;
import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.dto.UserRegistrationDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.repository.AddressRepository;
import com.mylab.assetmanagement.repository.RoleRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.repository.UserRoleRepository;
import com.mylab.assetmanagement.service.impl.RoleServiceImpl;
import com.mylab.assetmanagement.service.impl.UserServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @InjectMocks
    private RoleConverter roleConverter;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    private UserDTO testUserDto;
    private UserEntity testUserEntity;
    private UserRegistrationDTO testRegisterUserDTO;

    private final List<UserEntity> testEntityList = new ArrayList<>();

    static void setPrivateField(Object target, String fieldName, Object value) {
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
        testUserDto = new UserDTO();
        testUserDto.setName("name");
        testUserDto.setPassword("1234567890");
        testUserDto.setEmail("email@mail");
        testUserDto.setUsername("username");
        testUserDto.setCity("city");
        testUserDto.setHouseNo("no");
        testUserDto.setPhone("+00");
        testUserDto.setCountry("country");
        testUserDto.setStreet("street");
        testUserDto.setPostalCode("code");
        testUserDto.setRoles(new ArrayList<>());

        testRegisterUserDTO = new UserRegistrationDTO();
        testRegisterUserDTO.setName("name");
        testRegisterUserDTO.setPassword("1234567890");
        testRegisterUserDTO.setEmail("email@mail");
        testRegisterUserDTO.setUsername("username");
        testRegisterUserDTO.setCity("city");
        testRegisterUserDTO.setHouseNo("no");
        testRegisterUserDTO.setPhone("+00");
        testRegisterUserDTO.setCountry("country");
        testRegisterUserDTO.setStreet("street");
        testRegisterUserDTO.setPostalCode("code");

        testUserEntity = new UserEntity();
        testUserEntity.setId(1L);
        testUserEntity.setName("name");
        testUserEntity.setPassword("1234567890");
        testUserEntity.setPhone("+00");
        testUserEntity.setEmail("email@mail");
        testUserEntity.setUsername("username");
        testEntityList.add(testUserEntity);

        setPrivateField(userService, "userConverter", userConverter);
        setPrivateField(userService, "roleService", roleService);

        setPrivateField(roleService, "userRoleRepository", userRoleRepository);
        setPrivateField(roleService, "roleRepository", roleRepository);
        setPrivateField(roleService, "userRepository", userRepository);
        setPrivateField(roleService, "roleConverter", roleConverter);

    }

    @Test
    void registerFailedTest() {
        given(userRepository.findOneByUsername(ArgumentMatchers.any())).willReturn(Optional.of(testUserEntity));
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
            UserDTO registerUser = userService.register(testRegisterUserDTO);
        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertTrue(errMsG.contains("Username 'username' already exists"));
    }

    @Test
    void registerTest() {
        Optional<UserEntity> optionalUserEntity = Optional.empty();
        given(userRepository.findOneByUsername(ArgumentMatchers.any())).willReturn(optionalUserEntity);
        UserDTO registerUser = userService.register(testRegisterUserDTO);
        assertThat(registerUser).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "password", "country", "city", "street", "postalCode", "houseNo", "roles")
                .isEqualTo(testUserEntity);
        assertThat(registerUser.getPassword()).isNull();
    }

    @Test
    void loginFailedTest() {
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
            UserDTO loginUser = userService.login("", "");
        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertTrue(errMsG.contains("Incorrect username or password"));
    }

    @Test
    void loginTest() {
        given(userRepository.findOneByUsernameAndPassword(ArgumentMatchers.any(),
                                                          ArgumentMatchers.any())).willReturn(Optional.of(testUserEntity));
        UserDTO loginUser = userService.login("", "");
        assertThat(loginUser).isNotNull().usingRecursiveComparison()
                .ignoringFields("password", "country", "city", "street", "postalCode", "houseNo", "roles")
                .isEqualTo(testUserEntity);
        assertThat(loginUser.getPassword()).isNull();
    }

    @Test
    void getAllUsersTest() {
        // when not found
        List<UserDTO> userDTOlist = userService.getAllUsers();
        assertThat(userDTOlist).isNotNull().asList().isEmpty();

        // when found
        given(userRepository.findAll()).willReturn(testEntityList);
        userDTOlist = userService.getAllUsers();
        assertThat(userDTOlist.get(0).getPassword()).isNull();
        assertThat(userDTOlist.get(0)).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("password", "country", "city", "street", "postalCode", "houseNo", "roles")
                .isEqualTo(testUserEntity);
    }

    @Test
    void deleteUser() {
        Long toBeDeleted = userService.deleteUser(0L);
        assertThat(toBeDeleted).isNotNull();

        given(userRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testUserEntity));
        given(addressRepository.findPrimaryTypeByUserEntityId(ArgumentMatchers.any())).willReturn(Optional.of(new AddressEntity()));

        toBeDeleted = userService.deleteUser(testUserEntity.getId());
        assertThat(toBeDeleted).isEqualTo(testUserEntity.getId());

    }

    @Test
    void getUser() {
        // when not found
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
            UserDTO user = userService.getUser(ArgumentMatchers.anyLong());
        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertTrue(errMsG.contains("User not found"));

        // when found
        given(userRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testUserEntity));
        UserDTO userDTO = userService.getUser(ArgumentMatchers.anyLong());
        assertThat(userDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "password", "country", "city", "street", "postalCode", "houseNo")
                .isEqualTo(testUserDto);
    }
}
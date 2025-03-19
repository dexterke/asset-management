package com.mylab.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.dto.UserLoginDTO;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.exception.CustomExceptionHandler;
import com.mylab.assetmanagement.exception.ErrorModel;
import com.mylab.assetmanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private List<UserDTO> userDtoList;

    private UserDTO testUserDto;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        testUserDto = new UserDTO();
        testUserDto.setId(1L);
        testUserDto.setName("name");
        testUserDto.setPassword("1234567890");
        testUserDto.setEmail("email@mail");
        testUserDto.setCity("city");
        testUserDto.setHouseNo("no");
        testUserDto.setCountry("country");
        testUserDto.setStreet("street");
        testUserDto.setPhone("+00");
        testUserDto.setPostalCode("code");

        userDtoList = new ArrayList<>();
        userDtoList.add(testUserDto);

        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    void getUserTest() throws Exception {
        // when exists
        given(userService.getUser(1L)).willReturn(testUserDto);
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getUser/1")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
        String jsonResponse = mockResponse.getContentAsString();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testUserDto));

        // when not exists
        given(userService.getUser(1L)).willReturn(null);
        mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getUser/1")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(mockResponse).isNotNull();
    }

    @Test
    void getAllUsersTest() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(testUserDto);
        // when exists
        given(userService.getAllUsers()).willReturn(userList);
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getAllUsers")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
        String jsonResponse = mockResponse.getContentAsString();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(userDtoList));

        // when not exists
        given(userService.getAllUsers()).willReturn(null);
        mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getAllUsers")
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(mockResponse).isNotNull();
    }

    @Test
    void loginTest() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(testUserDto.getEmail());
        userLoginDTO.setPassword(testUserDto.getPassword());
        // successful login
        given(userService.login(testUserDto.getEmail(), testUserDto.getPassword())).willReturn(userLoginDTO);
        MvcResult mockResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login/")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(asJsonString(userLoginDTO).getBytes(StandardCharsets.UTF_8))
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String jsonResponse = mockResult.getResponse().getContentAsString();
        assertThat(mockResult).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(userLoginDTO));
    }

    @Test
    void loginFailTest() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(testUserDto.getEmail());
        userLoginDTO.setPassword(testUserDto.getPassword());

        List<ErrorModel> errorModelList = new ArrayList<>();
        errorModelList.add(new ErrorModel());

        // failed login
        given(userService.login(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willThrow(new BusinessException(errorModelList));
        MvcResult mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login/")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(asJsonString(userLoginDTO).getBytes(StandardCharsets.UTF_8))
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        String jsonResult = mockResponse.getResponse().getContentAsString();
        assertThat(mockResponse).isNotNull();
        assertThat(jsonResult).isNotNull();
    }

    @Test
    void registerTest() throws Exception {
        // successful register
        given(userService.register((UserDTO) ArgumentMatchers.<HttpServletRequest>any())).willReturn(testUserDto);
        MvcResult mockResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(asJsonString(testUserDto).getBytes(StandardCharsets.UTF_8))
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String jsonResponse = mockResult.getResponse().getContentAsString();
        assertThat(mockResult).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testUserDto));

        // fail
        given(userService.register((UserDTO) ArgumentMatchers.<HttpServletRequest>any())).willReturn(null);
        mockResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(asJsonString(testUserDto).getBytes(StandardCharsets.UTF_8))
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        jsonResponse = mockResult.getResponse().getContentAsString();
        assertThat(mockResult).isNotNull();
        assertThat(jsonResponse).isEmpty();
    }

    @Test
    void deleteUser() throws Exception {
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/deleteUser/1")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
        assertEquals(mockResponse.getStatus(), HttpStatus.NO_CONTENT.value());
        assertThat(mockResponse).isNotNull();
        assertThat(mockResponse.getContentLength()).isZero();
    }

}
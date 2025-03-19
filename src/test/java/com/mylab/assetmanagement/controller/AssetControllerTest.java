package com.mylab.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.exception.CustomExceptionHandler;
import com.mylab.assetmanagement.service.AssetService;
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
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AssetController assetController;

    @Mock
    private AssetService assetService;

    private List<AssetDTO> assetDtoList;

    private AssetDTO testAssetDto;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    void setUp() {
        testAssetDto = new AssetDTO();
        testAssetDto.setTitle("title");
        testAssetDto.setPrice(0D);
        testAssetDto.setAddress("address");
        testAssetDto.setUserId(1000L);
        testAssetDto.setDescription("description");

        assetDtoList = new ArrayList<>();
        assetDtoList.add(testAssetDto);

        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc =
                MockMvcBuilders.standaloneSetup(assetController).setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    void getAssetTest() throws Exception {
        // when exists
        given(assetService.getAsset(1L)).willReturn(testAssetDto);
        MockHttpServletResponse mockResponseOk =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/getAsset/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        String jsonResponse = mockResponseOk.getContentAsString();
        assertThat(mockResponseOk.getStatus()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));

        // when not exists
        given(assetService.getAsset(1L)).willReturn(null);
        MockHttpServletResponse mockResponse
                = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/getAsset/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(mockResponse).isNotNull();
    }

    @Test
    void getAllAssetsTest() throws Exception {
        // when exists
        given(assetService.getAllAssets()).willReturn(assetDtoList);
        MockHttpServletResponse mockResponseOk =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/getAllAssets/")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        String jsonResponse = mockResponseOk.getContentAsString();
        assertThat(mockResponseOk.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(assetDtoList));

        //when not exists
        given(assetService.getAllAssets()).willReturn(null);
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/getAllAssets/")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(mockResponse).isNotNull();
    }

    @Test
    void getAssetsForUserTest() throws Exception {
        // when exists
        given(assetService.getAllAssetsOfUser(ArgumentMatchers.anyLong())).willReturn(assetDtoList);
        MockHttpServletResponse mockResponseOk =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/users/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        String jsonResponse = mockResponseOk.getContentAsString();
        assertThat(mockResponseOk.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(assetDtoList));

        //when not exists
        assetDtoList.remove(0);
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/getAssetsForUser/1")
                                               .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(mockResponse).isNotNull();
    }

    @Test
    void addAssetTest() throws Exception {
        testAssetDto.setTitle(null);
        MvcResult mockResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/addAsset")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto)
                                                         .getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        String jsonResponse = mockResult.getResponse().getContentAsString();
        assertThat(mockResult).isNotNull();
        assertEquals(400, mockResult.getResponse().getStatus());

        testAssetDto.setTitle("title");
        given(assetService.addAsset((AssetDTO) ArgumentMatchers.<HttpServletRequest>any())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/addAsset")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto)
                                                         .getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/asset/updateAsset/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAsset(ArgumentMatchers.any(),
                                       ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/asset/updateAsset/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetUserIdTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-userid/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAssetUserId(ArgumentMatchers.any(),
                                             ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-userid/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetTitleTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-title/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAssetTitle(ArgumentMatchers.any(),
                                            ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-title/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetPriceTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-price/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAssetPrice(ArgumentMatchers.any(),
                                            ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-price/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetDescriptionTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-description/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAssetDescription(ArgumentMatchers.any(),
                                                  ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-description/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void updateAssetAddressTest() throws Exception {
        MvcResult mockFailResult =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-address/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
        String jsonResponse = mockFailResult.getResponse().getContentAsString();
        assertThat(mockFailResult).isNotNull();
        assertThat(jsonResponse).isEmpty();

        given(assetService.updateAssetAddress(ArgumentMatchers.any(),
                                              ArgumentMatchers.anyLong())).willReturn(testAssetDto);
        MvcResult mockResultOk =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/asset/updateAsset/update-address/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJsonString(testAssetDto).getBytes(StandardCharsets.UTF_8))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        jsonResponse = mockResultOk.getResponse().getContentAsString();
        assertThat(mockResultOk).isNotNull();
        assertThat(jsonResponse).isNotEmpty();
        assertThat(jsonResponse).isEqualToIgnoringCase(asJsonString(testAssetDto));
    }

    @Test
    void deleteAssetTest() throws Exception {
        MockHttpServletResponse mockResponse =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/asset/deleteAsset/100")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertEquals(mockResponse.getStatus(), HttpStatus.NO_CONTENT.value());
        assertThat(mockResponse).isNotNull();
        assertThat(mockResponse.getContentLength()).isZero();
    }
}
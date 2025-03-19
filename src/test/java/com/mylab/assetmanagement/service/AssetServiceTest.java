package com.mylab.assetmanagement.service;

import com.mylab.assetmanagement.converter.AssetConverter;
import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.entity.AssetEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.repository.AssetRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.service.impl.AssetServiceImpl;
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
class AssetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetConverter assetConverter;

    @InjectMocks
    private AssetService assetService = new AssetServiceImpl();

    private UserEntity testUserEntity;

    private final List<AssetEntity> assetEntityList = new ArrayList<>();

    private AssetDTO testAssetDto;

    private AssetEntity testAssetEntity;

    static void setPrivateFieldAccessible(Object target, String fieldName, Object value) {
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
        testAssetDto = new AssetDTO();
        testAssetDto.setTitle("testTitle");
        testAssetDto.setDescription("testDescription");
        testAssetDto.setPrice(0D);
        testAssetDto.setAddress("testAddress");
        testAssetDto.setUserId(100L);

        testUserEntity = new UserEntity();
        testUserEntity.setId(100L);
        testUserEntity.setName("name");
        testUserEntity.setPassword("1234567890");
        testUserEntity.setPhone("+00");
        testUserEntity.setEmail("email@mail");

        testAssetEntity = new AssetEntity();
        testAssetEntity.setTitle("testTitle");
        testAssetEntity.setDescription("testDescription");
        testAssetEntity.setPrice(0D);
        testAssetEntity.setAddress("testAddress");
        testAssetEntity.setUserEntity(testUserEntity);

        assetEntityList.add(testAssetEntity);

        setPrivateFieldAccessible(assetService, "assetConverter", assetConverter);
    }

    @Test
    void getAssetTest() {
        //when not found
        Optional<AssetEntity> byId =
                assetRepository.findById(ArgumentMatchers.anyLong());
        assertThat(byId).isEmpty();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        AssetDTO dto =
                assetService.getAsset(ArgumentMatchers.anyLong());
        assertThat(dto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void addAssetTest() {
        //when not found
        BusinessException thrown =
                assertThrowsExactly(BusinessException.class, () -> {
            AssetDTO assetDTO =
                    assetService.addAsset(testAssetDto);
        });
        String errMsG = thrown.getErrors().get(0).getMessage();
        assertThat(errMsG).contains("User does not exist");
        // when exists
        given(userRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testUserEntity));
        AssetDTO assetDTO = assetService.addAsset(testAssetDto);
        assertThat(assetDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void getAllAssetsTest() {
        // when not found
        List<AssetDTO> dtoList = assetService.getAllAssets();
        assertThat(dtoList).isNotNull().asList().isEmpty();
        // when exists
        given(assetRepository.findAll()).willReturn(assetEntityList);
        dtoList = assetService.getAllAssets();
        AssetDTO assetDTO = dtoList.get(0);
        assertThat(assetDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void getAllAssetsOfUserTest() {
        // when not found
        List<AssetDTO> dtoList =
                assetService.getAllAssetsOfUser(ArgumentMatchers.anyLong());
        assertThat(dtoList).isNotNull().asList().isEmpty();
        // when exists
        given(assetRepository.findAllByUserEntityId(ArgumentMatchers.anyLong())).willReturn(assetEntityList);
        dtoList = assetService.getAllAssetsOfUser(0L);
        AssetDTO assetDTO = dtoList.get(0);
        assertThat(assetDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetTest() {
        // when not exist
        AssetDTO assetDto =
                assetService.updateAsset(testAssetDto,
                                         ArgumentMatchers.anyLong());
        assertThat(assetDto).isNull();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        assetDto = assetService.updateAsset(testAssetDto,
                                            ArgumentMatchers.anyLong());
        assertThat(assetDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetDescriptionTest() {
        // when not exist
        AssetDTO assetDto =
                assetService.updateAssetDescription(testAssetDto,
                                                    ArgumentMatchers.anyLong());
        assertThat(assetDto).isNull();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        assetDto =
                assetService.updateAssetDescription(testAssetDto,
                                                    ArgumentMatchers.anyLong());
        assertThat(assetDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetPriceTest() {
        // when not exist
        AssetDTO assetDto =
                assetService.updateAssetPrice(testAssetDto,
                                              ArgumentMatchers.anyLong());
        assertThat(assetDto).isNull();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        assetDto = assetService.updateAssetPrice(testAssetDto,
                                                 ArgumentMatchers.anyLong());
        assertThat(assetDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetTitleTest() {
        // when not exist
        AssetDTO assetDto =
                assetService.updateAssetTitle(testAssetDto,
                                              ArgumentMatchers.anyLong());
        assertThat(assetDto).isNull();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        assetDto = assetService.updateAssetTitle(testAssetDto,
                                                 ArgumentMatchers.anyLong());
        assertThat(assetDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetAddressTest() {
        // when not exist
        AssetDTO assetDto =
                assetService.updateAssetAddress(testAssetDto,
                                                ArgumentMatchers.anyLong());
        assertThat(assetDto).isNull();

        // when exists
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        assetDto = assetService.updateAssetAddress(testAssetDto,
                                                   ArgumentMatchers.anyLong());
        assertThat(assetDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void updateAssetUserIdTest() {
        // when not found
        AssetDTO assetDTO =
                assetService.updateAssetUserId(new AssetDTO(),
                                               ArgumentMatchers.anyLong());
        assertThat(assetDTO).isNull();

        // when found
        testAssetEntity.setId(1L);
        given(assetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testAssetEntity));
        given(userRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(testUserEntity));
        assetDTO = assetService.updateAssetUserId(testAssetDto,
                                                  ArgumentMatchers.anyLong());
        assertThat(assetDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testAssetDto);
    }

    @Test
    void deleteAssetTest() {
        Long deletedId = assetService.deleteAsset(0L);
        assertThat(deletedId).isNotNull().isEqualTo(0l);
    }
}
package com.mylab.assetmanagement.service.impl;

import com.mylab.assetmanagement.converter.AssetConverter;
import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.AssetEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.exception.ErrorModel;
import com.mylab.assetmanagement.repository.AddressRepository;
import com.mylab.assetmanagement.repository.AssetRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
@Service: singleton bean, used with classes that provide some business functionalities.
Spring context will autodetect these classes when annotation-based configuration and
classpath scanning is used.
 */
@Service
public class AssetServiceImpl implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetConverter assetConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AssetDTO getAsset(Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            dto = assetConverter.convertEntityToDTO(entity);
        }
        return dto;
    }

    @Override
    public List<AssetDTO> getAllAssets() {
        List<AssetDTO> dtoList = new ArrayList<>();
        List<AssetEntity> entityList = (List<AssetEntity>) assetRepository.findAll();
        for (AssetEntity entity : entityList) {
            AssetDTO dto = assetConverter.convertEntityToDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<AssetDTO> getAllAssetsOfUser(Long userId) {
        List<AssetDTO> dtoList = new ArrayList<>();
        List<AssetEntity> entityList = assetRepository.findAllByUserEntityId(userId);
        for (AssetEntity entity : entityList) {
            AssetDTO dto = assetConverter.convertEntityToDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public AssetDTO addAsset(AssetDTO assetDTO) {
        Optional<UserEntity> optUser = userRepository.findById(assetDTO.getUserId());
        if (optUser.isPresent()) {
            AssetEntity entity = assetConverter.convertDTOtoEntity(assetDTO);
            entity.setUserEntity(optUser.get());
            assetRepository.save(entity);
            assetDTO = assetConverter.convertEntityToDTO(entity);
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ID_NOT_EXIST");
            String errMessage = "User does not exist";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        return assetDTO;
    }

    @Override
    public AssetDTO updateAsset(AssetDTO assetDTO, Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO assetDto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            entity.setTitle(assetDTO.getTitle());
            entity.setPrice(assetDTO.getPrice());
            entity.setDescription(assetDTO.getDescription());

            Optional<AddressEntity> optionalAddressEntity = addressRepository.findAssetTypeByUserEntityId(entity.getId());
            if (optionalAddressEntity.isPresent()) {
                AddressEntity addressEntity = optionalAddressEntity.get();
                addressEntity.setHouseNo(assetDTO.getHouseNo());
                addressEntity.setStreet(assetDTO.getStreet());
                addressEntity.setCity(assetDTO.getCity());
                addressEntity.setPostalCode(assetDTO.getPostalCode());
                addressEntity.setCountry(assetDTO.getCountry());
                addressEntity.setType(AddressEntity.ADDRESS_TYPE.ASSET.ordinal());
                entity.setAddressEntity(addressEntity);
                addressRepository.save(addressEntity);
            } else {
                List<ErrorModel> errorModelList = new ArrayList<>();
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCode("ADDRESS_DOES_NOT_EXIST");
                String errMessage = "Address does not exist";
                errorModel.setMessage(errMessage);
                log.error(errMessage);
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }

            assetRepository.save(entity);
            assetDto = assetConverter.convertEntityToDTO(entity);
        }
        return assetDto;
    }

    @Override
    public AssetDTO updateAssetDescription(AssetDTO assetDTO, Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            entity.setDescription(assetDTO.getDescription());
            assetRepository.save(entity);
            dto = assetConverter.convertEntityToDTO(entity);
        }
        return dto;
    }

    @Override
    public AssetDTO updateAssetPrice(AssetDTO assetDTO, Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            entity.setPrice(assetDTO.getPrice());
            assetRepository.save(entity);
            dto = assetConverter.convertEntityToDTO(entity);
        }
        return dto;
    }

    @Override
    public AssetDTO updateAssetTitle(AssetDTO assetDTO, Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            entity.setTitle(assetDTO.getTitle());
            assetRepository.save(entity);
            dto = assetConverter.convertEntityToDTO(entity);
        }
        return dto;
    }

    @Override
    public AssetDTO updateAssetAddress(AssetDTO assetDTO, Long assetId) {
        Optional<AssetEntity> byId = assetRepository.findById(assetId);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity entity = byId.get();
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findAssetTypeByUserEntityId(entity.getId());
            if (optionalAddressEntity.isPresent()) {
                AddressEntity addressEntity = optionalAddressEntity.get();
                addressEntity.setHouseNo(assetDTO.getHouseNo());
                addressEntity.setStreet(assetDTO.getStreet());
                addressEntity.setCity(assetDTO.getCity());
                addressEntity.setPostalCode(assetDTO.getPostalCode());
                addressEntity.setCountry(assetDTO.getCountry());
                addressEntity.setType(AddressEntity.ADDRESS_TYPE.ASSET.ordinal());
                entity.setAddressEntity(addressEntity);
                addressRepository.save(addressEntity);
            } else {
                List<ErrorModel> errorModelList = new ArrayList<>();
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCode("ADDRESS_DOES_NOT_EXIST");
                String errMessage = "Address does not exist";
                errorModel.setMessage(errMessage);
                log.error(errMessage);
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }
            dto = assetConverter.convertEntityToDTO(entity);
        }
        return dto;
    }

    @Override
    public AssetDTO updateAssetUserId(AssetDTO assetDTO, Long id) {
        Optional<AssetEntity> byId = assetRepository.findById(id);
        AssetDTO dto = null;
        if (byId.isPresent()) {
            AssetEntity assetEntity = byId.get();
            Long userId = assetDTO.getUserId();
            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (userEntity.isPresent()) {
                assetEntity.setUserEntity(userEntity.get());
                assetRepository.save(assetEntity);
                dto = assetConverter.convertEntityToDTO(assetEntity);
            }
        }
        return dto;
    }

    @Override
    public Long deleteAsset(Long id) {
        assetRepository.deleteById(id);
        return id;
    }

}

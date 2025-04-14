package com.mylab.assetmanagement.service.impl;

import com.mylab.assetmanagement.converter.UserConverter;
import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.dto.UserRegistrationDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.exception.ErrorModel;
import com.mylab.assetmanagement.repository.AddressRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.service.PasswordEncriptionService;
import com.mylab.assetmanagement.service.RoleService;
import com.mylab.assetmanagement.service.UserService;
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
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncriptionService passwordEncriptionService;

    @Override
    public UserDTO register(UserRegistrationDTO userRegistrationDTO) {
        UserEntity userEntity;
        UserDTO userDTO;
        Optional<UserEntity> optionalUserEntity = userRepository.findOneByUsername(userRegistrationDTO.getUsername());
        if (optionalUserEntity.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ALREADY_EXISTS");
            String errMessage = "Username '" + userRegistrationDTO.getUsername() + "' already exists";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        } else {
            userEntity = userConverter.convertDTOtoEntity(userRegistrationDTO);
            String passwd = passwordEncriptionService.encode(userRegistrationDTO.getPassword());
            userEntity.setPassword(passwd);
            userRepository.save(userEntity);

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setCity(userRegistrationDTO.getCity());
            addressEntity.setStreet(userRegistrationDTO.getStreet());
            addressEntity.setCountry(userRegistrationDTO.getCountry());
            addressEntity.setHouseNo(userRegistrationDTO.getHouseNo());
            addressEntity.setPostalCode(userRegistrationDTO.getPostalCode());
            addressEntity.setUserEntity(userEntity);

            addressEntity.setType(AddressEntity.ADDRESS_TYPE.PRIMARY.ordinal());
            addressRepository.save(addressEntity);

            userDTO = userConverter.convertEntityToDTO(userEntity);
            userConverter.setUserDTOaddress(userDTO, addressEntity);
            userDTO.setPassword(null);
        }
        return userDTO;
    }

    @Override
    public UserDTO login(String username, String password) {
        UserDTO userDTO;
        UserEntity userEntity;
        Optional<UserEntity> optionalUserEntity = userRepository.findOneByUsernameAndPassword(username, password);
        if (optionalUserEntity.isPresent()) {
            userEntity = optionalUserEntity.get();
            userDTO = userConverter.convertEntityToDTO(userEntity);
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findPrimaryTypeByUserEntityId(userEntity.getId());
            optionalAddressEntity.ifPresent(addressEntity -> userConverter.setUserDTOaddress(userDTO, addressEntity));
            userDTO.setRoles(roleService.getRolesNamesForUserId(userEntity.getId()));
            userDTO.setPassword(null);
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_LOGIN");
            String errMessage = "Incorrect username or password";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOlist = new ArrayList<>();
        List<UserEntity> entityList = (List<UserEntity>) userRepository.findAll();
        for (UserEntity userEntity : entityList) {
            UserDTO userDTO = userConverter.convertEntityToDTO(userEntity);
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findPrimaryTypeByUserEntityId(userEntity.getId());
            optionalAddressEntity.ifPresent(addressEntity -> userConverter.setUserDTOaddress(userDTO, addressEntity));
            userDTO.setRoles(roleService.getRolesNamesForUserId(userEntity.getId()));
            userDTO.setPassword(null);
            userDTOlist.add(userDTO);
        }
        return userDTOlist;
    }

    @Override
    public Long deleteUser(Long id) {
        Long userId;
        UserEntity userEntity;
        AddressEntity addressEntity;

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            userEntity = optionalUserEntity.get();
            userId = userEntity.getId();
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findPrimaryTypeByUserEntityId(userEntity.getId());
            if (optionalAddressEntity.isPresent()) {
                addressEntity = optionalAddressEntity.get();
                addressEntity.setUserEntity(userEntity);
                addressRepository.deleteById(addressEntity.getId());
            }
            userRepository.deleteById(userId);
        }
        return id;
    }

    @Override
    public UserDTO getUser(Long id) {
        UserDTO userDTO;

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userDTO = userConverter.convertEntityToDTO(userEntity);
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findPrimaryTypeByUserEntityId(userEntity.getId());
            optionalAddressEntity.ifPresent(addressEntity -> userConverter.setUserDTOaddress(userDTO, addressEntity));
            userDTO.setRoles(roleService.getRolesNamesForUserId(id));
            userDTO.setPassword(null);
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NOT_FOUND");
            String errMessage = "User not found";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        return userDTO;
    }

}

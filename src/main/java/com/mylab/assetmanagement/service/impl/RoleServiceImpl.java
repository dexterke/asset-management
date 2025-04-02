package com.mylab.assetmanagement.service.impl;

import com.mylab.assetmanagement.converter.RoleConverter;
import com.mylab.assetmanagement.dto.RoleDTO;
import com.mylab.assetmanagement.dto.UserRoleDTO;
import com.mylab.assetmanagement.entity.RoleEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.entity.UserRoleEntity;
import com.mylab.assetmanagement.exception.BusinessException;
import com.mylab.assetmanagement.exception.ErrorModel;
import com.mylab.assetmanagement.repository.RoleRepository;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.repository.UserRoleRepository;
import com.mylab.assetmanagement.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleConverter roleConverter;

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public RoleDTO addRole(RoleDTO roleDTO) {
        Optional <RoleEntity> optionalRoleEntity = roleRepository.findOneRoleByName(roleDTO.getName());
        if(optionalRoleEntity.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("ROLE_ALREADY_EXISTS");
            String errMessage = "Role already exists";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        } else {
            RoleEntity roleEntity = roleConverter.convertRoleDTOtoEntity(roleDTO);
            roleRepository.save(roleEntity);
            roleDTO.setId(roleEntity.getId());
            return roleDTO;
        }
    }

    @Override
    public RoleDTO getRole(Long id) {
        RoleEntity entity;
        RoleDTO dto = new RoleDTO();
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findOneRoleById(id);
        if (optionalRoleEntity.isPresent()) {
            entity = optionalRoleEntity.get();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NOT_FOUND");
            String errMessage = "Role not found";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        return dto;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> roleList = new ArrayList<>();
        List<RoleEntity> entityList = roleRepository.findAllRoles();
        for (RoleEntity entity : entityList) {
            RoleDTO roleDTO = roleConverter.convertRoleEntityToDTO(entity);
            roleList.add(roleDTO);
        }
        return roleList;
    }

    @Override
    public Long deleteRole(Long id) {
        Optional <RoleEntity> optionalRoleEntity = roleRepository.findOneRoleById(id);
        if(optionalRoleEntity.isPresent()) {
            roleRepository.deleteById(id);
            return id;
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NOT_FOUND");
            String errMessage = "Role does not exists";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
    }

    @Override
    public List<String> getRolesNamesForUserId(Long userId) {
        List<String> roleNames = new ArrayList<>();
        List<UserRoleEntity> roleList = userRoleRepository.findAllByUserEntityId(userId);
        for (UserRoleEntity role : roleList) {
            roleNames.add(role.getRoleEntity().getName());
        }
        return roleNames;
    }

    @Override
    public List<UserRoleDTO> getRolesForUserId(Long userId) {
        List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
        List<UserRoleEntity> roleList = userRoleRepository.findAllByUserEntityId(userId);
        for (UserRoleEntity role : roleList) {
            UserRoleDTO roleDto = roleConverter.convertUserRoleEntityToDTO(role);
            userRoleDTOList.add(roleDto);
        }
        return userRoleDTOList;
    }

    public UserRoleDTO setRoleForUserDto(Long userId, Long rolesIDForUser) {
        UserRoleDTO newUserRoleDTO = null;
        UserRoleEntity userRoleEntity = null;

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if (optionalUserEntity.isPresent()) {
            Optional<RoleEntity> optionalRoleEntity = roleRepository.findOneRoleById(rolesIDForUser);

            if (optionalRoleEntity.isPresent()) {
                RoleEntity roleEntity = optionalRoleEntity.get();

                List<UserRoleEntity> existingUserRoles = userRoleRepository.findAllByUserEntityId(userId);
                for (UserRoleEntity existingRole : existingUserRoles) {
                    // Role already assigned
                    if (Objects.equals(existingRole.getRoleEntity().getId(), roleEntity.getId())) {
                        userRoleEntity = existingRole;
                        break;
                    }
                }

                if (userRoleEntity == null) {
                    userRoleEntity = new UserRoleEntity();
                    userRoleEntity.setUserEntity(optionalUserEntity.get());
                    userRoleEntity.setRoleEntity(roleEntity);
                    userRoleEntity.setInfo(roleEntity.getDescription());
                }

                UserRoleEntity newUserRole = userRoleRepository.save(userRoleEntity);
                newUserRoleDTO = roleConverter.convertUserRoleEntityToDTO(newUserRole);
            }
        }
        return newUserRoleDTO;
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        Optional<UserRoleEntity> optionalUserRoleEntity = userRoleRepository.findById(roleId);
        if (optionalUserRoleEntity.isPresent() && optionalUserEntity.isPresent()) {
            if (optionalUserRoleEntity.get().getUserEntity().getId()
                                            == optionalUserEntity.get().getId())
                userRoleRepository.deleteById(roleId);
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NOT_FOUND");
            String errMessage = "User role does not exists";
            errorModel.setMessage(errMessage);
            log.error(errMessage);
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
    }

}


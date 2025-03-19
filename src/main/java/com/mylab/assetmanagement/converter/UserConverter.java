package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Structural adapter design pattern: convert different types of data,
 * for compatibility conversion.
 * e.g save functions accept PropertyDTO, but repositories expect PropertyEntity
 *
 * @Component allows Spring to detect custom beans automatically.
 *   -  Scans the application for classes annotated with @Component
 *   -  Instantiate them and inject any specified dependencies into them
 *   -  Inject them wherever needed
 *   -  @Component == singleton instance, so it can be autowired
 */

@Component
public class UserConverter {

    public UserEntity convertDTOtoEntity(UserDTO userDTO) {
        UserEntity entity = new UserEntity();
        entity.setEmail(userDTO.getEmail());
        entity.setName(userDTO.getName());
        entity.setPassword(userDTO.getPassword());
        entity.setPhone(userDTO.getPhone());

        return entity;
    }

    public UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setEmail(userEntity.getEmail());
        dto.setName(userEntity.getName());
        dto.setPassword(userEntity.getPassword());
        dto.setPhone(userEntity.getPhone());

        return dto;
    }

    public UserDTO setUserDTOaddress(UserDTO userDTO, AddressEntity addressEntity) {
        userDTO.setHouseNo(addressEntity.getHouseNo());
        userDTO.setStreet(addressEntity.getStreet());
        userDTO.setCity(addressEntity.getCity());
        userDTO.setPostalCode(addressEntity.getPostalCode());
        userDTO.setCountry(addressEntity.getCountry());

        return userDTO;
    }
}

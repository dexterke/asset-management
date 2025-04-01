package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.AssetEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 *  Structural adapter design pattern: convert different types of data,
 *  for compatibility conversion.
 *  e.g save functions accept PropertyDTO, but repositories expect PropertyEntity
 *
 * @Component allows Spring to detect custom beans automatically.
 *   -  Scans the application for classes annotated with @Component
 *   -  Instantiate them and inject any specified dependencies into them
 *   -  Inject them wherever needed
 *   -  @Component == singleton instance, so it can be autowired
 */
@Component
public class AssetConverter {

    public AssetEntity convertDTOtoEntity(AssetDTO assetDTO) {
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setTitle(assetDTO.getTitle());

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(assetDTO.getCity());
        addressEntity.setHouseNo(assetDTO.getHouseNo());
        addressEntity.setStreet(assetDTO.getStreet());
        addressEntity.setPostalCode(assetDTO.getPostalCode());
        addressEntity.setCity(assetDTO.getCountry());
        addressEntity.setType(AddressEntity.ADDRESS_TYPE.ASSET.ordinal());

        assetEntity.setAddressEntity(addressEntity);
        assetEntity.setPrice(assetDTO.getPrice());
        assetEntity.setDescription(assetDTO.getDescription());
        return assetEntity;
    }

    public AssetDTO convertEntityToDTO(AssetEntity assetEntity) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(assetEntity.getId());
        assetDTO.setTitle(assetEntity.getTitle());

        assetDTO.setCity(assetEntity.getAddressEntity().getCity());
        assetDTO.setHouseNo(assetEntity.getAddressEntity().getHouseNo());
        assetDTO.setStreet(assetEntity.getAddressEntity().getStreet());
        assetDTO.setPostalCode(assetEntity.getAddressEntity().getPostalCode());
        assetDTO.setCountry(assetEntity.getAddressEntity().getCountry());

        assetDTO.setPrice(assetEntity.getPrice());
        assetDTO.setDescription(assetEntity.getDescription());
        assetDTO.setUserId(assetEntity.getUserEntity().getId());
        return assetDTO;
    }

}

package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.entity.AssetEntity;
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
        assetEntity.setAddress(assetDTO.getAddress());
        assetEntity.setPrice(assetDTO.getPrice());
        assetEntity.setDescription(assetDTO.getDescription());
        return assetEntity;
    }

    public AssetDTO convertEntityToDTO(AssetEntity assetEntity) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(assetEntity.getId());
        assetDTO.setTitle(assetEntity.getTitle());
        assetDTO.setAddress(assetEntity.getAddress());
        assetDTO.setPrice(assetEntity.getPrice());
        assetDTO.setDescription(assetEntity.getDescription());
        assetDTO.setUserId(assetEntity.getUserEntity().getId());
        return assetDTO;
    }

}

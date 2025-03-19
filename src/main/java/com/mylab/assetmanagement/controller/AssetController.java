package com.mylab.assetmanagement.controller;

import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC (Model View Controller) design pattern
 */
@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {
    private static final Logger log = LoggerFactory.getLogger(AssetController.class);
    private static final String DB_URL_LOG = "DB_URL: '{}'";

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    // Dependency injection, assetService = controller class level variable of assetService interface
    @Autowired
    private AssetService assetService;

    @Operation(summary = "getAsset", description = "Listing of one asset")
    @GetMapping(value = "/getAsset/{id}", path = "/getAsset/{id}", produces = {"application/json"})
    public ResponseEntity<AssetDTO> getAsset(@PathVariable Long id) {
        AssetDTO assetDTO = assetService.getAsset(id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.NOT_FOUND : HttpStatus.FOUND);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "getAllAssets", description = "Listing of all assets")
    @GetMapping(value = "/getAllAssets", path = "/getAllAssets", produces = {"application/json"})
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assetList = assetService.getAllAssets();
        ResponseEntity<List<AssetDTO>> responseEntity = new ResponseEntity<>(assetList, assetList == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "getAssetsForUser", description = "Listing of a user assets")
    @GetMapping(value = "/users/{userId}", path = "/users/{userId}", produces = {"application/json"})
    public ResponseEntity<List<AssetDTO>> getAssetsForUser(@PathVariable Long userId) {
        List<AssetDTO> assetList = assetService.getAllAssetsOfUser(userId);
        ResponseEntity<List<AssetDTO>> responseEntity = new ResponseEntity<>(assetList, assetList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    /*
     * Swagger-UI input fields, set to @Validated instead of @RequestBody
     */
    @Operation(summary = "addAsset", description = "Add new asset")
    @Parameter(name = "title", description = "Title", required = true, example = "Title")
    @Parameter(name = "description", description = "Description", required = true, example = "Description")
    @Parameter(name = "address", description = "Address", required = true, example = "Address")
    @Parameter(name = "price", description = "Price", required = true, example = "0")
    @Parameter(name = "userId", description = "Owner's userId", required = true)
    @PostMapping(value = "/addAsset", path = "/addAsset", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> addAsset(@RequestBody @Validated AssetDTO assetDTO) {
        assetDTO = assetService.addAsset(assetDTO);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "updateAsset", description = "Update asset")
    @PutMapping(value = "/updateAsset/{id}", path = "/updateAsset/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAsset(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAsset(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "update-userid/{id}", description = "Update asset owner userId")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"userId\": \"0\" }")))
    @ApiResponse(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"userId\": \"0\" }")))
    @PatchMapping(value = "/updateAsset/update-userid/{id}", path = "/updateAsset/update-userid/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAssetUserId(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAssetUserId(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "update-title/{id}", description = "Update asset title")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"title\": \"string\" }")))
    @ApiResponse(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"title\": \"string\" }")))
    @PatchMapping(value = "/updateAsset/update-title/{id}", path = "/updateAsset/update-title/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAssetTitle(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAssetTitle(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "update-price/{id}", description = "Update asset price")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"price\": \"0\" }")))
    @ApiResponse(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"price\": \"0\" }")))
    @PatchMapping(value = "/updateAsset/update-price/{id}", path = "/updateAsset/update-price/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAssetPrice(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAssetPrice(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "update-description/{id}", description = "Update asset description")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"description\": \"string\" }")))
    @ApiResponse(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"description\": \"string\" }")))
    @PatchMapping(value = "/updateAsset/update-description/{id}", path = "/updateAsset/update-description/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAssetDescription(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAssetDescription(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "update-address/{id}", description = "Update asset address")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"address\": \"string\" }")))
    @ApiResponse(content = @Content(mediaType = "application/json", examples = @ExampleObject("{ \"address\": \"string\" }")))
    @PatchMapping(value = "/updateAsset/update-address/{id}", path = "/updateAsset/update-address/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<AssetDTO> updateAssetAddress(@RequestBody AssetDTO assetDTO, @PathVariable Long id) {
        assetDTO = assetService.updateAssetAddress(assetDTO, id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(assetDTO, assetDTO == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

    @Operation(summary = "deleteAsset", description = "Delete an asset")
    @DeleteMapping(value = "/deleteAsset/{id}")
    public ResponseEntity<AssetDTO> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        ResponseEntity<AssetDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        log.info(DB_URL_LOG, dbUrl);
        return responseEntity;
    }

}

package lk.ijse.backend.controller;

import lk.ijse.backend.dto.impl.CropDto;
import lk.ijse.backend.dto.impl.FieldDto;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.exception.FiledNotFoundException;
import lk.ijse.backend.service.CropService;
import lk.ijse.backend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/crop")
@RequiredArgsConstructor
public class CropController {

    @Autowired
    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropCode")String  cropCode,
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestPart("category")String category,
            @RequestPart("cropSeason")String cropSeason,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart("fieldCode")String fieldCode
    ){
        try {
            byte [] image1 = cropImage.getBytes();
           String base64fieldImage1 = AppUtil.toBase64(image1);

            CropDto build = new CropDto();
            build.setCropCode(cropCode);
            build.setCropCommonName(cropCommonName);
            build.setCropScientificName(cropScientificName);
            build.setCategory(category);
            build.setCropSeason(cropSeason);
            build.setCropImage(base64fieldImage1);
            build.setFieldCode(fieldCode);

            cropService.saveCrop(build);
            return  new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/{cropcode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropcode") String cropcode) {
        try {
            cropService.deleteCrop(cropcode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FiledNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDto> getAllCrop(){
        return cropService.getAllCrop();
    }

    @PatchMapping(value = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable("cropCode")String  cropCode,
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestPart("category")String category,
            @RequestPart("cropSeason")String cropSeason,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart("fieldCode")String fieldCode
    ) {
        try {
            byte[] image1 = cropImage.getBytes();
            String base64fieldImage1 = AppUtil.toBase64(image1);

            CropDto build = new CropDto();
            build.setCropCode(cropCode);
            build.setCropCommonName(cropCommonName);
            build.setCropScientificName(cropScientificName);
            build.setCategory(category);
            build.setCropSeason(cropSeason);
            build.setCropImage(base64fieldImage1);
            build.setFieldCode(fieldCode);

            cropService.updateCrop(build);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

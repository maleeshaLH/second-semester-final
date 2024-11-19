package lk.ijse.backend.controller;

import lk.ijse.backend.dto.impl.FieldDto;
import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.exception.FiledNotFoundException;
import lk.ijse.backend.service.FieldService;
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
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FieldController {

    @Autowired
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFiled(
            @RequestParam("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String  fieldLocation,
            @RequestParam("extentSize")Double extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2")MultipartFile fieldImage2
    ){
        try {
           byte [] image1 = fieldImage1.getBytes();
           String base64fieldImage1 =AppUtil.toBase64(image1);
           byte [] image2 = fieldImage2.getBytes();
           String base64fieldImage2 =AppUtil.toBase64(image2);

            FieldDto build = new FieldDto();
            build.setFieldCode(fieldCode);
            build.setFieldName(fieldName);
            build.setFieldLocation(fieldLocation);
            build.setExtentSize(extentSize);
            build.setFieldImage1(base64fieldImage1);
            build.setFieldImage2(base64fieldImage2);

            fieldService.saveFiled(build);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{fieldcode}")
    public ResponseEntity<Void> deleteFiled(@PathVariable ("fieldcode") String fieldcode) {
        try {
            fieldService.deleteField(fieldcode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FiledNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDto> getAllFiled(){
        return fieldService.getAllFiled();
    }


    @PatchMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFiled(
            @PathVariable("fieldCode") String fieldCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String  fieldLocation,
            @RequestParam("extentSize")Double extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2")MultipartFile fieldImage2
    ){
        try {
            byte [] image1 = fieldImage1.getBytes();
            String base64fieldImage1 =AppUtil.toBase64(image1);
            byte [] image2 = fieldImage2.getBytes();
            String base64fieldImage2 =AppUtil.toBase64(image2);

            FieldDto build = new FieldDto();
            build.setFieldCode(fieldCode);
            build.setFieldName(fieldName);
            build.setFieldLocation(fieldLocation);
            build.setExtentSize(extentSize);
            build.setFieldImage1(base64fieldImage1);
            build.setFieldImage2(base64fieldImage2);

            fieldService.updateFiled(build);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}

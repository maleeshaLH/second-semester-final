package lk.ijse.backend.controller;

import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveVehicle(
            @RequestPart("vehicleCode") String vehicleCode,
            @RequestPart("licensePlateNumber") String licensePlateNumber,
            @RequestPart("category") String category,
            @RequestPart("fuelType") String fuelType,
            @RequestPart("status") String status,
            @RequestPart("remarks") String remarks
    ){
        try {
            VehicleDto build = new VehicleDto();
            build.setVehicleCode(vehicleCode);
            build.setLicensePlateNumber(licensePlateNumber);
            build.setCategory(category);
            build.setFuelType(fuelType);
            build.setStatus(status);
            build.setRemarks(remarks);

            vehicleService.saveVehicle(build);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDto> getAllVehicle(){
        return vehicleService.getAllUsers();
    }

    @PatchMapping(value = "/{vehicleCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateVehicle(
            @PathVariable("vehicleCode") String vehicleCode,
            @RequestPart("licensePlateNumber") String licensePlateNumber,
            @RequestPart("category") String category,
            @RequestPart("fuelType") String fuelType,
            @RequestPart("status") String status,
            @RequestPart("remarks") String remarks
    ){
        try {
            VehicleDto build = new VehicleDto();
            build.setVehicleCode(vehicleCode);
            build.setLicensePlateNumber(licensePlateNumber);
            build.setCategory(category);
            build.setFuelType(fuelType);
            build.setStatus(status);
            build.setRemarks(remarks);

            vehicleService.updateVehicle(build);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("code") String code){
        try {
            vehicleService.deleteVehicle(code);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

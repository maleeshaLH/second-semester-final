package lk.ijse.backend.controller;

import lk.ijse.backend.dto.impl.CropDto;
import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.entity.impl.CropEntity;
import lk.ijse.backend.exception.CropNotFoundException;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5502")
public class VehicleController {

    @Autowired
    private final VehicleService vehicleService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
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

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
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

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
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


    @GetMapping("/{vehicleCode}")
    public ResponseEntity<VehicleDto> getCrops(@PathVariable("vehicleCode") String vehicleCode) {
        try {
            VehicleDto vehicleDto = vehicleService.getVehicleByCode(vehicleCode);
            if (vehicleDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(vehicleDto, HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.VehicleDto;

import java.util.List;

public interface VehicleService {

    void saveVehicle(VehicleDto build);

    List<VehicleDto> getAllUsers();

    void updateVehicle(VehicleDto build);

    void deleteVehicle(String code);

    VehicleDto getVehicleByCode(String vehicleCode);
}

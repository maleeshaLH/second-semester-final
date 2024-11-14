package lk.ijse.backend.dto.impl;

import lk.ijse.backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDto implements SuperDto {
    private String vehicleCode;
    private String licensePlateNumber;
    private String category;
    private String fuelType;
    private String status;
    private String remarks;
}

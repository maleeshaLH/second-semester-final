package lk.ijse.backend.util;

import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.entity.VehicleEntity;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
   @Autowired
    private ModelMapper modelMapper;

    //matters of NoteEntity and DTO
    public VehicleDto convertToVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDto.class);
    }
    public VehicleEntity convertToEntity(VehicleDto dto) {
        return modelMapper.map(dto, VehicleEntity.class);
    }
    public List<VehicleDto> convertToDTO(List<VehicleEntity> notes) {
        return modelMapper.map(notes, new TypeToken<List<VehicleDto>>(){}.getType());
    }



}

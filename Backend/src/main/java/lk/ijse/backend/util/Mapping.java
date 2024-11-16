package lk.ijse.backend.util;

import lk.ijse.backend.dto.impl.FieldDto;
import lk.ijse.backend.dto.impl.StaffDto;
import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.entity.impl.FiledEntity;
import lk.ijse.backend.entity.impl.StaffEntity;
import lk.ijse.backend.entity.impl.VehicleEntity;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
   @Autowired
    private ModelMapper modelMapper;

    //matters of VehicleEntity and DTO
    public VehicleDto convertToVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDto.class);
    }
    public VehicleEntity convertToEntity(VehicleDto dto) {
        return modelMapper.map(dto, VehicleEntity.class);
    }
    public List<VehicleDto> convertToDTO(List<VehicleEntity> notes) {
        return modelMapper.map(notes, new TypeToken<List<VehicleDto>>(){}.getType());
    }

    //matters of StaffEntity and DTO
    public StaffDto convertToStaffDTO(StaffEntity Entity) {
        return modelMapper.map(Entity, StaffDto.class);
    }

    public List<StaffDto> convertToStaffDTO(List<StaffEntity> notes) {
        return modelMapper.map(notes, new TypeToken<List<StaffDto>>(){}.getType());
    }
    public StaffEntity convertToStaffEntity(StaffDto dto) {
        return modelMapper.map(dto, StaffEntity.class);
    }

    //matters of FiledEntity and DTO
    public FieldDto convertToFiledDTO(FiledEntity Entity) {
        return modelMapper.map(Entity, FieldDto.class);
    }

    public List<FieldDto> convertToFiledDTO(List<FiledEntity> notes) {
        return modelMapper.map(notes, new TypeToken<List<FieldDto>>(){}.getType());
    }
    public FiledEntity convertToFiledEntity(FieldDto dto) {
        return modelMapper.map(dto, FiledEntity.class);
    }


}

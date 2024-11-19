package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.EquipmentDto;

import java.util.List;

public interface EquipmentService {
    List<EquipmentDto> getAllEquipment();

    void deleteEquipment(String id);

    void updateEquipment(String equipmentId, EquipmentDto equipmentDto);

    void saveEquipment(EquipmentDto equipmentDto);
}

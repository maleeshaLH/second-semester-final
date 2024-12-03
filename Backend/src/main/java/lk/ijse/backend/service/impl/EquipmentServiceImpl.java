package lk.ijse.backend.service.impl;

import lk.ijse.backend.dao.EquipmentDao;
import lk.ijse.backend.dao.FieldDao;
import lk.ijse.backend.dao.StaffDao;
import lk.ijse.backend.dto.impl.EquipmentDto;
import lk.ijse.backend.entity.impl.EquipmentEntity;
import lk.ijse.backend.entity.impl.FiledEntity;
import lk.ijse.backend.entity.impl.StaffEntity;
import lk.ijse.backend.entity.impl.VehicleEntity;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.EquipmentService;
import lk.ijse.backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private final Mapping mapping;
    @Autowired
    private final EquipmentDao equipmentDao;
    private final StaffDao staffDao;
    private final FieldDao fieldDao;
    @Override
    public List<EquipmentDto> getAllEquipment() {
        List<EquipmentEntity> getAllEquipment = equipmentDao.findAll();
        return mapping.convertToEquipmentDTO(getAllEquipment);
    }


    @Override
    public void deleteEquipment(String id) {

        Optional<EquipmentEntity> selectedEquipmentId = equipmentDao.findById(id);
        if(selectedEquipmentId.isEmpty()){
            throw new DataPersistFailedException("Equipment not found");
        }
        equipmentDao.deleteById(id);
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {

        Optional<EquipmentEntity> tmp = equipmentDao.findById(equipmentId);
        if(tmp.isEmpty()){
            throw new DataPersistFailedException("Equipment not found");

        }
           // EquipmentEntity equipmentEntity = tmp.get();

        tmp.get().setEquipmentName(equipmentDto.getEquipmentName());
        tmp.get().setEquipmentType(equipmentDto.getEquipmentType());
        tmp.get().setEquipmentStatus(equipmentDto.getEquipmentStatus());








    }

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {

        EquipmentEntity equipmentEntity =
                equipmentDao.save(mapping.convertToEquipmentEntity(equipmentDto));
        System.out.printf("ser"+equipmentEntity.getFields());
        if (equipmentEntity == null) {
            throw new DataPersistFailedException("Cannot save staff");
        }
    }

    @Override
    public EquipmentDto geteQuipmentByCode(String equipmentCode) {
        Optional<EquipmentEntity> equipmentEntity = equipmentDao.findById(equipmentCode);
        if (equipmentEntity.isPresent()) {
            return mapping.convertToEquipmentDTO(equipmentEntity.get()); // Convert to DTO and return
        } else {
            throw new DataPersistFailedException("Equipment not found"); // Or a custom exception if needed
        }
    }
}

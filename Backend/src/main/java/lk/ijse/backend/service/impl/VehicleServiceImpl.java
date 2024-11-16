package lk.ijse.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.backend.dao.VehicleDao;
import lk.ijse.backend.dto.impl.VehicleDto;
import lk.ijse.backend.entity.VehicleEntity;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.VehicleService;
import lk.ijse.backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private final VehicleDao vehicleDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveVehicle(VehicleDto build) {
        VehicleEntity vehicleEntity =
                vehicleDao.save(mapping.convertToEntity(build));

        if (vehicleEntity == null) {
            throw new DataPersistFailedException("Cannot save vehicle");
        }
    }

    @Override
    public List<VehicleDto> getAllUsers() {
        List<VehicleEntity> getAllVehicle = vehicleDao.findAll();
        return mapping.convertToDTO(getAllVehicle);
    }

    @Override
    public void updateVehicle(VehicleDto build) {

        Optional<VehicleEntity> tmp = vehicleDao.findById(build.getVehicleCode());
        if(!tmp.isPresent()){
            throw new DataPersistFailedException("User not found");
        }else {
            tmp.get().setLicensePlateNumber(build.getLicensePlateNumber());
            tmp.get().setCategory(build.getCategory());
            tmp.get().setFuelType(build.getFuelType());
            tmp.get().setStatus(build.getStatus());
            tmp.get().setRemarks(build.getRemarks());

        }
    }

    @Override
    public void deleteVehicle(String code) {

        Optional<VehicleEntity> selectedUserId = vehicleDao.findById(code);
        if(selectedUserId.isEmpty()){
            throw new DataPersistFailedException("Vehicle not found");
        }
            vehicleDao.deleteById(code);

    }
}

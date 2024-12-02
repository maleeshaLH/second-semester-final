package lk.ijse.backend.service.impl;

import lk.ijse.backend.dao.StaffDao;
import lk.ijse.backend.dto.impl.StaffDto;
import lk.ijse.backend.entity.impl.CropEntity;
import lk.ijse.backend.entity.impl.StaffEntity;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.StaffService;
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
public class StaffServiceImpl implements StaffService {

    @Autowired
    private final StaffDao staffDao;

    @Autowired
    private final Mapping mapping;
    @Override
    public void saveStaff(StaffDto build) {
        StaffEntity staffEntity =
               staffDao.save(mapping.convertToStaffEntity(build));

        if (staffEntity == null) {
            throw new DataPersistFailedException("Cannot save staff");
        }
    }

    @Override
    public void updateStaff(String id, StaffDto staffDto) {
        Optional<StaffEntity> tmp = staffDao.findById(id);
        if(!tmp.isPresent()){
            throw new DataPersistFailedException("Staff not found");
        }else {
            tmp.get().setFirstName(staffDto.getFirstName());
            tmp.get().setLastName(staffDto.getLastName());
            tmp.get().setDesignation(staffDto.getDesignation());
            tmp.get().setGender(staffDto.getGender());
            tmp.get().setJoinedDate(staffDto.getJoinedDate());
            tmp.get().setDob(staffDto.getDob());
            tmp.get().setAddressLine01(staffDto.getAddressLine01());
            tmp.get().setAddressLine02(staffDto.getAddressLine02());
            tmp.get().setAddressLine03(staffDto.getAddressLine03());
            tmp.get().setAddressLine04(staffDto.getAddressLine04());
            tmp.get().setAddressLine05(staffDto.getAddressLine05());
            tmp.get().setContactNo(staffDto.getContactNo());
            tmp.get().setEmail(staffDto.getEmail());
            tmp.get().setVehicleCode(staffDto.getVehicleCode());


        }
    }

    @Override
    public void deleteStaff(String id) {
//        staffDao.deleteById(id);
        Optional<StaffEntity> selectedUserId = staffDao.findById(id);
        if(selectedUserId.isEmpty()){
            throw new DataPersistFailedException("Staff not found");
        }
        staffDao.deleteById(id);
    }



    @Override
    public List<StaffDto> getAllStaff() {
        List<StaffEntity> getAllStaff = staffDao.findAll();
        return mapping.convertToStaffDTO(getAllStaff);
    }
}

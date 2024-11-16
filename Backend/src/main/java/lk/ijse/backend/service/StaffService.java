package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.StaffDto;

import java.util.List;

public interface StaffService  {
    void saveStaff(StaffDto build);

    void updateStaff(String id, StaffDto staffDto);

    void deleteStaff(String id);

    List<StaffDto> getAllStaff();

}

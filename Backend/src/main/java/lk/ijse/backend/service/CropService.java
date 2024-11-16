package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.CropDto;

import java.util.List;

public interface CropService {
    void updateCrop(CropDto build);

    List<CropDto> getAllCrop();


    void deleteCrop(String cropcode);

    void saveCrop(CropDto build);
}

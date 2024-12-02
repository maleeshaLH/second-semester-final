package lk.ijse.backend.service.impl;

import lk.ijse.backend.dao.CropDao;
import lk.ijse.backend.dto.impl.CropDto;
import lk.ijse.backend.entity.impl.CropEntity;
import lk.ijse.backend.entity.impl.FiledEntity;
import lk.ijse.backend.exception.CropNotFoundException;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.CropService;
import lk.ijse.backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    @Autowired
    private final CropDao cropDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void updateCrop(CropDto build) {
        Optional<CropEntity> tmp = cropDao.findById(build.getCropCode());
        if(!tmp.isPresent()){
            throw new CropNotFoundException("Crop not found");
        }else {
            tmp.get().setCropCode(build.getCropCode());
            tmp.get().setCropCommonName(build.getCropCommonName());
            tmp.get().setCropScientificName(build.getCropScientificName());
            tmp.get().setCategory(build.getCategory());
            tmp.get().setCropSeason(build.getCropSeason());
            tmp.get().setCropImage(build.getCropImage());
            tmp.get().setFieldCode(build.getFieldCode());

        }
    }

    @Override
    public List<CropDto> getAllCrop() {
        List<CropEntity> getAllCrop = cropDao.findAll();
        return mapping.convertToCropDTO(getAllCrop);
    }

    @Override
    public void deleteCrop(String cropcode) {

        Optional<CropEntity> selectecropcode = cropDao.findById(cropcode);
        if(selectecropcode.isEmpty()){
            throw new DataPersistFailedException("Crop not found");
        }
        cropDao.deleteById(cropcode);
    }

    @Override
    public void saveCrop(CropDto build) {

        CropEntity cropEntity =
                cropDao.save(mapping.convertToCropEntity(build));

        if (cropEntity == null) {
            throw new CropNotFoundException("Cannot save crop");
        }
    }

    @Override
    public CropDto getCropByCode(String cropCode) {
        Optional<CropEntity> cropEntity = cropDao.findById(cropCode);
        if (cropEntity.isPresent()) {
            return mapping.convertToCropDTO(cropEntity.get()); // Convert to DTO and return
        } else {
            throw new DataPersistFailedException("Crops not found"); // Or a custom exception if needed
        }
    }
}

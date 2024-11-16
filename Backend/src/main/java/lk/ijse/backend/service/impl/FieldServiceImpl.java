package lk.ijse.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.backend.dao.FieldDao;
import lk.ijse.backend.dto.impl.FieldDto;
import lk.ijse.backend.entity.impl.FiledEntity;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.service.FieldService;
import lk.ijse.backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    @Autowired
    private final FieldDao fieldDao;

    @Autowired
    private final Mapping mapping;

    @Override
    public List<FieldDto> getAllFiled() {
        List<FiledEntity> getAllField = fieldDao.findAll();
        return mapping.convertToFiledDTO(getAllField);
    }

    @Override
    public void deleteField(String fieldcode) {

        Optional<FiledEntity> selectefieldcode = fieldDao.findById(fieldcode);
        if(selectefieldcode.isEmpty()){
            throw new DataPersistFailedException("Field not found");
        }
        fieldDao.deleteById(fieldcode);
    }

    @Override
    public void saveFiled(FieldDto build) {

        FiledEntity filedEntity =
                fieldDao.save(mapping.convertToFiledEntity(build));

        if (filedEntity == null) {
            throw new DataPersistFailedException("Cannot save field");
        }

    }

    @Override
    public void updateFiled(FieldDto build) {
        Optional<FiledEntity> tmp = fieldDao.findById(build.getFieldCode());
        if(!tmp.isPresent()){
            throw new DataPersistFailedException("Field not found");
        }else {
           tmp.get().setFieldCode(build.getFieldCode());
           tmp.get().setFieldName(build.getFieldName());
           tmp.get().setFieldLocation(build.getFieldLocation());
           tmp.get().setExtentSize(build.getExtentSize());
           tmp.get().setFieldImage1(build.getFieldImage1());
           tmp.get().setFieldImage2(build.getFieldImage2());

        }
    }
}

package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.FieldDto;

import java.io.Serializable;
import java.util.List;

public interface FieldService  {
    List<FieldDto> getAllFiled();

    void deleteField(String fieldcode);

    void saveFiled(FieldDto build);

    void updateFiled(FieldDto build);

    FieldDto getFiledByCode(String fieldCode);
}

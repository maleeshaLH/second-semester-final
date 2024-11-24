package lk.ijse.backend.service.impl;

import lk.ijse.backend.dao.CropDao;
import lk.ijse.backend.dao.FieldDao;
import lk.ijse.backend.dao.LogMonitoringDao;
import lk.ijse.backend.dao.StaffDao;
import lk.ijse.backend.dto.impl.LogMonitoringDto;
import lk.ijse.backend.entity.impl.CropEntity;
import lk.ijse.backend.entity.impl.FiledEntity;
import lk.ijse.backend.entity.impl.LogMonitoringEntity;
import lk.ijse.backend.entity.impl.StaffEntity;
import lk.ijse.backend.exception.*;
import lk.ijse.backend.service.LogMonitoringService;
import lk.ijse.backend.util.Mapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class LogMonitoringServiceImpl implements LogMonitoringService {

    private LogMonitoringDao logMonitoringDao;
    private final CropDao cropDao;
    private final FieldDao fieldDao;
    private final StaffDao staffDao;
    private final Mapping mapping;
    @Override
    public void saveLogMonitoring(LogMonitoringDto logDTO) {

        FiledEntity field = fieldDao.findById(logDTO.getFieldCode())
                .orElseThrow(() -> new DataPersistFailedException("Invalid field code"));
        CropEntity crop = cropDao.findById(logDTO.getCropCode())
                .orElseThrow(() -> new DataPersistFailedException("Invalid crop code"));
        StaffEntity staff = staffDao.findById(logDTO.getId())
                .orElseThrow(() -> new DataPersistFailedException("Invalid staff ID"));

        LogMonitoringEntity log = mapping.convertToLogMonitoringEntity(logDTO);
        log.setFields(field);
        log.setCrops(crop);
        log.setStaffs(staff);

        LogMonitoringEntity savedLog = logMonitoringDao.save(log);
        if (savedLog == null) {
            throw new DataPersistFailedException("Can't save Monitoring Log");
        }
    }

    @Override
    public List<LogMonitoringDto> getAllLogMonitoring() {
        List<LogMonitoringEntity> logs = logMonitoringDao.findAll();
        return mapping.convertToLogMonitoringDTO(logs);
    }

    @Override
    public List<LogMonitoringDto> searchLogMonitoring(String searchTerm) {
        List<LogMonitoringEntity> logs = logMonitoringDao.findByMonitoringLogCodeOrMonitoringLogDate(searchTerm, searchTerm);
        return mapping.convertToLogMonitoringDTO(logs);
    }

    @Override
    public void updateLogMonitoring(String logCode, LogMonitoringDto logDTO) {

        LogMonitoringEntity existingLog = logMonitoringDao.findById(String.valueOf(logDTO))
                .orElseThrow(() -> new LogMonitoringNotFoundException(logCode));

        if (logDTO.getLog_date() != null) {
            existingLog.setLog_date(logDTO.getLog_date());
        }
        if (logDTO.getObservation() != null) {
            existingLog.setObservation(logDTO.getObservation());
        }
        if (logDTO.getLog_image() != null) {
            existingLog.setLog_image(logDTO.getLog_image());
        }
        if (logDTO.getFieldCode() != null) {
            FiledEntity field = fieldDao.findById(logDTO.getFieldCode())
                    .orElseThrow(() -> new FiledNotFoundException("Field not found with code: " + logDTO.getFieldCode()));
            existingLog.setFields(field);
        }

        if (logDTO.getCropCode() != null) {
            CropEntity crop = cropDao.findById(logDTO.getCropCode())
                    .orElseThrow(() -> new CropNotFoundException("Crop not found with code: " + logDTO.getCropCode()));
            existingLog.setCrops(crop);
        }

        if (logDTO.getId() != null) {
            StaffEntity staff = staffDao.findById(logDTO.getId())
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + logDTO.getId()));
            existingLog.setStaffs(staff);
        }
        logMonitoringDao.save(existingLog);
    }


    @Override
    public void deleteLogMonitoring(String logCode) {

        Optional<LogMonitoringEntity> selectedLog = logMonitoringDao.findById(logCode);
        if (!selectedLog.isPresent()) {
            throw new LogMonitoringNotFoundException(logCode);
        } else {
            logMonitoringDao.deleteById(logCode);
        }
    }
}

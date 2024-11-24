package lk.ijse.backend.service;

import lk.ijse.backend.dto.impl.LogMonitoringDto;

import java.util.List;

public interface LogMonitoringService {
    void saveLogMonitoring(LogMonitoringDto logDTO);

    List<LogMonitoringDto> getAllLogMonitoring();

    List<LogMonitoringDto> searchLogMonitoring(String searchTerm);

    void updateLogMonitoring(String logCode, LogMonitoringDto logDTO);

    void deleteLogMonitoring(String logCode);
}

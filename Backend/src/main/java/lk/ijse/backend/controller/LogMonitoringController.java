package lk.ijse.backend.controller;

import lk.ijse.backend.dto.impl.LogMonitoringDto;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.exception.LogMonitoringNotFoundException;
import lk.ijse.backend.service.LogMonitoringService;
import lk.ijse.backend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/logmonitoring")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5502")
public class LogMonitoringController {
    @Autowired
    private final LogMonitoringService logMonitoringService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLogMonitoring(
            @RequestParam("logCode") String logCode,
            @RequestParam("logDate") String logDate,
            @RequestParam("observation") String observation,
            @RequestPart("logImage") MultipartFile logImage,
            @RequestParam("fieldCode") String fieldCode,
            @RequestParam("cropCode") String cropCode,
            @RequestParam("staffId") String id
    ) {
        try {
            String base64Image = AppUtil.toBase64(logImage.getBytes());

            LogMonitoringDto logDTO = new LogMonitoringDto();
            logDTO.setLog_code(logCode);
            logDTO.setLog_date(logDate);
            logDTO.setObservation(observation);
            logDTO.setLog_image(base64Image);
            logDTO.setFieldCode(fieldCode);
            logDTO.setCropCode(cropCode);
            logDTO.setId(id);

            logMonitoringService.saveLogMonitoring(logDTO);

            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "allLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogMonitoringDto> getAllLogMonitoring() {
        return logMonitoringService.getAllLogMonitoring();
    }


    @GetMapping()
    public ResponseEntity<List<LogMonitoringDto>> searchLogMonitoring(@RequestParam("searchTerm") String searchTerm) {
        List<LogMonitoringDto> logs = logMonitoringService.searchLogMonitoring(searchTerm);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLogMonitoring(
            @PathVariable("logCode") String logCode,
            @RequestParam(value = "logDate") String logDate,
            @RequestParam(value = "observation") String observation,
            @RequestParam(value = "logImage") MultipartFile logImage,
            @RequestParam(value = "fieldCode") String fieldCode,
            @RequestParam(value = "cropCode") String cropCode,
            @RequestParam(value = "staffId") String id
    ) {
        try {
            LogMonitoringDto logDTO = new LogMonitoringDto();

            if (logDate != null) logDTO.setLog_date(logDate);
            if (observation != null) logDTO.setObservation(observation);
            if (logImage != null && !logImage.isEmpty()) {
                logDTO.setLog_image(AppUtil.toBase64(logImage.getBytes()));
            }
            if (fieldCode != null) logDTO.setFieldCode(fieldCode);
            if (cropCode != null) logDTO.setCropCode(cropCode);
            if (id != null) logDTO.setId(id);

            logMonitoringService.updateLogMonitoring(logCode, logDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<Void> deleteLogMonitoring(@PathVariable("logCode") String logCode) {
        try {
            logMonitoringService.deleteLogMonitoring(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LogMonitoringNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

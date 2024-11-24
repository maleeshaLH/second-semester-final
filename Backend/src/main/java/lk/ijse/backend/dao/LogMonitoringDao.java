package lk.ijse.backend.dao;

import lk.ijse.backend.entity.impl.LogMonitoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMonitoringDao extends JpaRepository<LogMonitoringEntity,String> {
    @Query("SELECT m FROM LogMonitoringEntity m WHERE m.log_code = :logCode OR m.log_date = :logDate")
    List<LogMonitoringEntity> findByMonitoringLogCodeOrMonitoringLogDate(@Param("logCode") String logCode, @Param("logDate") String logDate);

}

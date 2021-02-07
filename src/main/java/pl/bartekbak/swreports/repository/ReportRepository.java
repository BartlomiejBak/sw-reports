package pl.bartekbak.swreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekbak.swreports.DTO.ReportDTO;

public interface ReportRepository extends JpaRepository <ReportDTO, Long> {
}

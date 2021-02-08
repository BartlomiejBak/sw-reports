package pl.bartekbak.swreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekbak.swreports.dto.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}

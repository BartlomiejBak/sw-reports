package pl.bartekbak.swreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekbak.swreports.DTO.ResultDTO;

public interface ResultRepository extends JpaRepository<ResultDTO, Long> {
}

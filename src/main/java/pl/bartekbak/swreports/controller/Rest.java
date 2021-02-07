package pl.bartekbak.swreports.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartekbak.swreports.DTO.QueryDTO;
import pl.bartekbak.swreports.DTO.ReportDTO;
import pl.bartekbak.swreports.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class Rest {

    private final ReportService reportService;

    public Rest(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<HttpStatus> putReport(@PathVariable long reportId, @RequestBody QueryDTO queryDTO) {
        ReportDTO result = reportService.createReport(queryDTO);
        result.setReportId(reportId);
        reportService.putReport(result);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{reportId}")
    public void deleteReportById(@PathVariable long reportId) {
        reportService.deleteReportById(reportId);
    }

    @DeleteMapping
    public void deleteAllReports() {
        reportService.deleteReports();
    }

    @GetMapping("/{reportId}")
    public ReportDTO getReportById(@PathVariable long reportId) {
        return reportService.getReportById(reportId);
    }

    @GetMapping
    public List<ReportDTO> getAllReports(){
        return reportService.getAllReports();
    }
}

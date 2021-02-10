package pl.bartekbak.swreports.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.service.controller.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Api(value = "/reports")
@ApiResponses(value = {
        @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found")
})
public class Rest {

    private final ReportService reportService;

    public Rest(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping("/{reportId}")
    @ApiOperation(value = "Create or update report")
    public ResponseEntity<HttpStatus> putReport(@PathVariable long reportId, @RequestBody Query query) {
        Report result = reportService.createReport(query);
        result.setReportId(reportId);
        reportService.putReport(result);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{reportId}")
    @ApiOperation(value = "Delete single report")
    public void deleteReportById(@PathVariable long reportId) {
        reportService.deleteReportById(reportId);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete all reports")
    public void deleteAllReports() {
        reportService.deleteReports();
    }

    @GetMapping("/{reportId}")
    @ApiOperation(value = "Get single report")
    public Report getReportById(@PathVariable long reportId) {
        return reportService.getReportById(reportId);
    }

    @GetMapping
    @ApiOperation(value = "Get all reports")
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }
}

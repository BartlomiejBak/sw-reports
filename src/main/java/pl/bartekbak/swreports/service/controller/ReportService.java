package pl.bartekbak.swreports.service.controller;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;

import java.util.List;

public interface ReportService {
    String putReport(Report report);
    Report createReport(Query query);

    List<Report> getAllReports();

    Report getReportById(long id);

    String deleteReportById(long id);
    void deleteReports();
}

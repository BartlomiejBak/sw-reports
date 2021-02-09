package pl.bartekbak.swreports.service.controller;

import org.springframework.stereotype.Service;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.exception.InvalidQueryException;
import pl.bartekbak.swreports.exception.ResourceNotFoundException;
import pl.bartekbak.swreports.repository.ReportRepository;
import pl.bartekbak.swreports.service.consumer.QueryConsumer;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final QueryConsumer client;

    public ReportServiceImpl(ReportRepository repository, QueryConsumer client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    public String putReport(Report report) {
        repository.save(report);
        return "ok";
    }

    @Override
    public Report createReport(Query query) {
        if (query.getPlanetQueryCriteria() == null && query.getCharacterQueryCriteria() == null) {
            throw new InvalidQueryException("No proper parameters");
        }
        return client.createReport(query);
    }

    @Override
    public List<Report> getAllReports() {
        return repository.findAll();
    }

    @Override
    public Report getReportById(long id) {
        Optional<Report> result = repository.findById(id);
        Report report;
        if (result.isPresent()) {
            report = result.get();
        } else {
            throw new ResourceNotFoundException("Resource not found");
        }
        return report;
    }

    @Override
    public String deleteReportById(long id) {
        Optional<Report> result = repository.findById(id);
        if (result.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Resource not found");
        }
        return "successfully deleted";
    }

    @Override
    public void deleteReports() {
        repository.deleteAll();
    }
}

package pl.bartekbak.swreports.service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.exception.ResourceNotFoundException;
import pl.bartekbak.swreports.repository.ReportRepository;
import pl.bartekbak.swreports.service.consumer.QueryConsumer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepository repository;

    @Mock
    private QueryConsumer client;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Report report;
    private Query query;

    @BeforeEach
    void setUp() {
        report = Report.builder()
                .reportId(1L)
                .characterQueryCriteria("Anakin")
                .planetQueryCriteria("Tatooine")
                .build();
        query = Query.builder()
                .characterQueryCriteria("Anakin")
                .planetQueryCriteria("Tatooine")
                .build();
    }

    @Test
    void putReport_shouldInvokeSaveOnce() {
        //given
        when(repository.save(any(Report.class))).thenReturn(report);
        //when
        String result = reportService.putReport(report);
        //then
        verify(repository, times(1)).save(report);
        assertEquals("ok", result);
    }

    @Test
    void createReport_shouldReturnResult() {
        //given
        when(client.createReport(any(Query.class))).thenReturn(report);
        //when
        Report result = reportService.createReport(query);
        //then
        assertEquals(report, result);
    }

    @Test
    void getAllReports() {
        //given
        List<Report> reportList = List.of(report);
        when(repository.findAll()).thenReturn(reportList);
        //when
        List<Report> result = reportService.getAllReports();
        //then
        assertEquals(reportList, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getReportById_shouldInvokeFindByIdOnce() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(report));
        //when
        Report result = reportService.getReportById(1L);
        //then
        assertEquals(report, result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getReportById_shouldThrowResourceNotFound() {
        //given
        when(repository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> reportService.getReportById(1L));
    }

    @Test
    void deleteReportById_shouldInvokeDeleteByIdOnce() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(report));
        //when
        reportService.deleteReportById(1L);
        //then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteReportById_shouldThrowResourceNotFound() {
        //given
        when(repository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> reportService.deleteReportById(1L));
    }

    @Test
    void deleteReports_shouldInvokeDeleteAllOnce() {
        //given
        doNothing().when(repository).deleteAll();
        //when
        reportService.deleteReports();
        //then
        verify(repository, times(1)).deleteAll();
    }
}

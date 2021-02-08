package pl.bartekbak.swreports.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.exception.ResourceNotFoundException;
import pl.bartekbak.swreports.service.controller.ReportService;
import pl.bartekbak.swreports.service.RestResponseExceptionHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestTest {

    @Mock
    ReportService service;

    @InjectMocks
    Rest controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Report report;
    private Query query;

    @BeforeEach
    void setUp() {
        report = Report.builder()
                .reportId(1L)
                .build();
        query = Query.builder()
                .build();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void putReport_shouldReturnStatusNoContent() throws Exception {
        //given
        when(service.createReport(any(Query.class))).thenReturn(report);
        when(service.putReport(any(Report.class))).thenReturn("ok");
        //when
        mockMvc.perform(put("/reports/1")
                    .content(objectMapper.writeValueAsString(query))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        //then
        verify(service, times(1)).putReport(any(Report.class));
        verify(service, times(1)).createReport(any(Query.class));
    }

    @Test
    void deleteReportById_shouldInvokeDeleteReportByIdOnce() throws Exception {
        //given
        when(service.deleteReportById(anyLong())).thenReturn("ok");
        //when
        mockMvc.perform(delete("/reports/1"))
                .andExpect(status().isOk())
                .andReturn();
        //then
        verify(service, times(1)).deleteReportById(1L);
    }
    @Test
    void deleteReportById_shouldReturnResourceNotFoundStatus() throws Exception {
        //given
        when(service.deleteReportById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        mockMvc.perform(delete("/reports/1"))
                .andExpect(status().isNotFound())
                .andReturn();
        //then
        verify(service, times(1)).deleteReportById(1L);
    }

    @Test
    void deleteAllReports_shouldInvokeDeleteReportsOnce() throws Exception {
        //given
        doNothing().when(service).deleteReports();
        //when
        mockMvc.perform(delete("/reports"))
                .andExpect(status().isOk())
                .andReturn();
        //then
        verify(service, times(1)).deleteReports();
    }

    @Test
    void getReportById_shouldReturnReport() throws Exception {
        //given
        when(service.getReportById(anyLong())).thenReturn(report);
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/reports/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final Report result = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {
                });
        assertEquals(report, result);
        verify(service, times(1)).getReportById(1L);
    }

    @Test
    void getReportById_shouldReturnResourceNotFoundStatus() throws Exception {
        //given
        when(service.getReportById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        mockMvc.perform(get("/reports/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        //then
        verify(service, times(1)).getReportById(1L);
    }

    @Test
    void getAllReports_shouldReturnList() throws Exception {
        //given
        List<Report> reportList = List.of(report, report);
        when(service.getAllReports()).thenReturn(reportList);
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final List<Report> result = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {
                });
        assertEquals(reportList, result);
        verify(service, times(1)).getAllReports();
    }
}

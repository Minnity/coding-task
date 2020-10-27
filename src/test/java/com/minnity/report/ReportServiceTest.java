package com.minnity.report;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class ReportServiceTest {

    List<RequestLog> requestLogList;
    ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportService();
        requestLogList = new ArrayList<>();

        RequestLog log1 = SampleDataGenerator.aRequestLog(1, 200, 400, "/ping");
        RequestLog log2 = SampleDataGenerator.aRequestLog(1, 204, 50, "/users");
        RequestLog log3 = SampleDataGenerator.aRequestLog(5, 401, 700, "/vivaldi");
        RequestLog log4 = SampleDataGenerator.aRequestLog(7, 500, 310, "/restaurants");
        RequestLog log5 = SampleDataGenerator.aRequestLog(6, 30, 250, "users/orders");
        RequestLog log6 = SampleDataGenerator.aRequestLog(5, 404, 250, "/restaurants?filter=openNow");

        requestLogList.add(log1);
        requestLogList.add(log2);
        requestLogList.add(log3);
        requestLogList.add(log4);
        requestLogList.add(log5);
        requestLogList.add(log6);
    }

    @Test
    public void whenRequestLogProvided_thenCalculateRequestPerCompany() {
        Map<Integer, Long> map = reportService.calculateNumberOfRequestsPerCompany(requestLogList);
        assertNotNull(map);
        assertEquals(map.size(), 4);
        assertEquals("Company with id `1` should make 2 request", map.get(1).intValue(), 2);
        assertEquals("Company with id `5` should make 1 request ", map.get(5).intValue(), 2);
        assertEquals("Company with id `6` should make 1 request ", map.get(6).intValue(), 1);
    }

    @Test()
    public void whenRequestLogNull_thenNumberOfRequestsPerCompanyThrowException() {
        assertThrows(NullPointerException.class, () -> reportService.calculateNumberOfRequestsPerCompany(null));
    }

    @Test
    public void whenRequestLogProvided_thenFindRequestsWithError() {
        Map<Integer, List<RequestLog>> map = reportService.findRequestsWithError(requestLogList);
        assertNotNull(map);
        assertEquals("Only company with id 5 and 7 should finish with error Http response code", map.size(), 2);
        assertEquals("Company with id 5 should finish with 2 error Http response code", map.get(5).size(), 2);
        assertEquals("Company with id 7 should finish with 1 error Http response code ", map.get(7).size(), 1);
    }

    @Test()
    public void whenRequestLogNull_thenRequestsWithErrorThrowException() {
        assertThrows(NullPointerException.class, () -> reportService.findRequestsWithError(null));
    }

    @Test
    public void whenRequestLogProvided_thenFindPathWithLongestDurationTime() {
        String url = reportService.findRequestPathWithLongestDurationTime(requestLogList);
        assertFalse(url.isEmpty());
        assertEquals(url, "/vivaldi");
    }

    @Test()
    public void whenRequestLogNull_thenRequestPathWithLongestDurationTimeThrowException() {
        assertThrows(NullPointerException.class, () -> reportService.findRequestPathWithLongestDurationTime(null));
    }
}

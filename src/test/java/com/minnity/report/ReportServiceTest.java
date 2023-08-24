package com.minnity.report;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;


public class ReportServiceTest {
    List<RequestLog> requestLogs = new ArrayList<>();
    GeoLocation geoLocation1;
    GeoLocation geoLocation2;
    GeoLocation geoLocation3;
    RequestLog requestLog1;
    RequestLog requestLog2;
    RequestLog requestLog3;
    RequestLog requestLog4;

    @Before
    public void setup() {
        geoLocation1 = GeoLocation.GeoLocationBuilder.aGeoLocation()
                .withIpAddress("192.168.1.100")
                .withCity("New York")
                .withCountry("United States")
                .build();
        geoLocation2 = GeoLocation.GeoLocationBuilder.aGeoLocation()
                .withIpAddress("10.0.0.42")
                .withCity("Tokyo")
                .withCountry("Japan")
                .build();
        geoLocation3 = GeoLocation.GeoLocationBuilder.aGeoLocation()
                .withIpAddress("172.16.254.1")
                .withCity("Sydney")
                .withCountry("Australia")
                .build();
        requestLog1 = RequestLog.RequestLogBuilder.aRequestLog()
                .withUserId(823749582)
                .withCompanyId(12345)
                .withGeoLocation(geoLocation1)
                .withRequestDuration(2344)
                .withRequestStatus(200)
                .withRequestPath("/api/user1/profile")
                .withRequestMethod("GET")
                .withCreatedTime(Instant.parse("2023-08-23T10:15:30Z"))
                .build();
        requestLog2 = RequestLog.RequestLogBuilder.aRequestLog()
                .withUserId(129384712)
                .withCompanyId(67890)
                .withGeoLocation(geoLocation2)
                .withRequestDuration(3798)
                .withRequestStatus(404)
                .withRequestPath("/api/user2/order")
                .withRequestMethod("POST")
                .withCreatedTime(Instant.parse("2023-08-20T15:30:45Z"))
                .build();
        requestLog3 = RequestLog.RequestLogBuilder.aRequestLog()
                .withUserId(698340487)
                .withCompanyId(47593)
                .withGeoLocation(geoLocation3)
                .withRequestDuration(9876)
                .withRequestStatus(500)
                .withRequestPath("/api/user3/treatment")
                .withRequestMethod("PUT")
                .withCreatedTime(Instant.parse("2023-08-22T18:20:00Z"))
                .build();
        requestLog4 = RequestLog.RequestLogBuilder.aRequestLog()
                .withUserId(698340463)
                .withCompanyId(47593)
                .withGeoLocation(geoLocation3)
                .withRequestDuration(9876)
                .withRequestStatus(500)
                .withRequestPath("/api/user3/order")
                .withRequestMethod("PUT")
                .withCreatedTime(Instant.parse("2023-08-10T11:22:33Z"))
                .build();
        requestLogs.add(requestLog1);
        requestLogs.add(requestLog2);
        requestLogs.add(requestLog3);
        requestLogs.add(requestLog4);
    }

    /**
     * TASK 1 TESTING
     */
    @Test
    public void testCount_request_by_company_equal_true() {
        Map<Integer, Long> expectedMap = new HashMap<>();
        expectedMap.put(12345, 1L);
        expectedMap.put(67890, 1L);
        expectedMap.put(47593, 2L);
        Map<Integer, Long> actualMap = new ReportService().calculateNumberOfRequestsPerCompany(requestLogs);
        Assert.assertEquals(expectedMap, actualMap);
    }

    /**
     * TASK 2 TESTING
     */
    //Test for original method
/*    @Test
    public void testFind_request_with_error_equal_true() {
        requestLogs.remove(requestLog4); //remove duplicated object in Map for testing, otherwise duplicated key error.
        Map<Integer, RequestLog> expectedMap = new HashMap<>();
        expectedMap.put(67890, requestLog2);
        expectedMap.put(47593, requestLog3);
        System.out.println(expectedMap);
        Map<Integer, RequestLog> actualMap = new ReportService().findRequestsWithError(requestLogs);
        Assert.assertEquals(expectedMap, actualMap);
    }*/

    //Test for edited method
    @Test
    public void testFind_request_with_error_equal_true() {
        Map<Integer, List<RequestLog>> expectedMap = new HashMap<>();
        List<RequestLog> logsByCompanyID67890 = new ArrayList<>();
        logsByCompanyID67890.add(requestLog2);
        List<RequestLog> logsByCompanyID47593 = new ArrayList<>();
        logsByCompanyID47593.add(requestLog3);
        logsByCompanyID47593.add(requestLog4);
        expectedMap.put(67890, logsByCompanyID67890);
        expectedMap.put(47593, logsByCompanyID47593);
        Map<Integer, List<RequestLog>> actualMap = new ReportService().findRequestsWithError(requestLogs);
        System.out.println(actualMap);
        Assert.assertEquals(expectedMap, actualMap);
    }

    /**
     * TASK 3 TESTING
     */
    //Test for original method
/*    @Test
    //when we have no requestLog, method should return empty list
    public void testLongest_duration_API_return_0_API() {
        requestLogs.clear();
        String expectedAPI = null;
        String actualAPI = new ReportService().findRequestPathWithLongestDurationTime(requestLogs);
        Assert.assertEquals(expectedAPI, actualAPI);
    }
    @Test
    public void testLongest_duration_API_return_1_API() {
        *//* log4 have same max duration so need to remove this log from the list.
        So the list now will have only one max duration*//*
        requestLogs.remove(requestLog4);
        String expectedAPI = requestLog3.getRequestPath();
        String actualAPI = new ReportService().findRequestPathWithLongestDurationTime(requestLogs);
        System.out.println(expectedAPI);
        System.out.println(actualAPI);
        Assert.assertEquals(expectedAPI, actualAPI);
    }*/
    //Test for edited method
    @Test
    //when we have no requestLog, method should return empty list
    public void testLongest_duration_API_return_0_API() {
        requestLogs.clear();
        List<String> expectedAPI = new ArrayList<>();
        List<String> actualAPI = new ReportService().findRequestPathWithLongestDurationTime(requestLogs);
        Assert.assertEquals(expectedAPI, actualAPI);
    }

    @Test
    //when we have only one max duration
    public void testLongest_duration_API_return_1_API() {
        /* requestLog4 have same max duration so need to remove this log from the list.
        So the list now will have only one max duration*/
        requestLogs.remove(requestLog4);
        List<String> expectedAPI = new ArrayList<>();
        expectedAPI.add(requestLog3.getRequestPath());
        List<String> actualAPI = new ReportService().findRequestPathWithLongestDurationTime(requestLogs);
        Assert.assertEquals(expectedAPI, actualAPI);
    }

    @Test
    //when we have two or more max duration APIs (APIs have same maximum duration)
    public void testLongest_duration_API_return_2_APIs() {
        Set<String> expectedAPI = new HashSet<>();
        expectedAPI.add(requestLog3.getRequestPath());
        expectedAPI.add(requestLog4.getRequestPath());
        List<String> actualAPI = new ReportService().findRequestPathWithLongestDurationTime(requestLogs);
        Assert.assertEquals(expectedAPI, new HashSet<>(actualAPI));
    }
}
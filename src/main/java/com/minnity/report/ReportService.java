package com.minnity.report;

import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    //task 1: Return number of requests that were made for each company. (e.g. companyId -> requestNumber)
    public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {
        return requestLogs.stream().collect(Collectors.groupingBy(RequestLog::getCompanyId, Collectors.counting()));
    }

    //task 2: Count and return requests per company that finished with an error HTTP response code (>=400)
    /**Original version: the method return Map<Integer, RequestLog>*/
/*    public Map<Integer, RequestLog> findRequestsWithError(List<RequestLog> requestLogs) {
        return requestLogs.stream()
                .filter(requestLog -> requestLog.getRequestStatus() >= 400)
                .collect(Collectors.toMap(RequestLog::getCompanyId, requestLog -> requestLog));
    }*/

    /**
     * Edited version: the method returns Map<Integer, List<RequestLog>>
     */
    public Map<Integer, List<RequestLog>> findRequestsWithError(List<RequestLog> requestLogs) {
        Map<Integer, List<RequestLog>> resultMap = new HashMap<>();

        for (RequestLog requestLog : requestLogs) {
            if (requestLog.getRequestStatus() >= 400) {
                int companyId = requestLog.getCompanyId();
                List<RequestLog> errorLogs = resultMap.computeIfAbsent(companyId, k -> new ArrayList<>());
                errorLogs.add(requestLog);
            }
        }
        return resultMap;
    }

    //task 3: find and print API (requests path) that on average takes the longest time to process the request.

    /**
     * Original version: the method return String
     */
//    public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
//        Map<String, Double> avgDurations = requestLogs.stream()
//                .collect(Collectors.groupingBy(RequestLog::getRequestPath,
//                        Collectors.averagingLong(RequestLog::getRequestDuration)));
//
//        Optional<Map.Entry<String, Double>> maxAvgDuration = avgDurations.entrySet()
//                .stream()
//                .max(Comparator.comparingDouble(Map.Entry::getValue));
//
//        return maxAvgDuration.map(Map.Entry::getKey).orElse(null);
//    }
    /**Edited version: the method returns List<String>*/
    public List<String> findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
        Map<String, Double> APIAverageDurations = requestLogs.stream()
                .collect(Collectors.groupingBy(RequestLog::getRequestPath,
                        Collectors.averagingLong(RequestLog::getRequestDuration)));

        double maxAverageDuration = Double.MIN_VALUE;
        List<String> longestDurationAPIs = new ArrayList<>();
        for (Map.Entry<String, Double> entry : APIAverageDurations.entrySet()) {
            if (entry.getValue() > maxAverageDuration) {
                longestDurationAPIs.clear();
                longestDurationAPIs.add(entry.getKey());
                maxAverageDuration = entry.getValue();
            } else if (entry.getValue() == maxAverageDuration) {
                longestDurationAPIs.add(entry.getKey());
            }
        }
        return longestDurationAPIs;
    }
}

package com.minnity.report;

import java.util.Set;

import static java.time.Instant.now;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

public class SampleDataGenerator {

    final static Set<Integer> SAMPLE_RESPONSE_CODES = Set.of(200, 202, 204, 30, 401, 403, 404, 500, 503);
    final static Set<String> SAMPLE_API_ENDPOINTS = Set.of("/ping", "/users", "users/orders", "/vivaldi", "/restaurants", "/restaurants?filter=openNow");
    final static Set<String> SAMPLE_REQUEST_METHOD = Set.of("GET", "POST", "PUT");

    public static GeoLocation aGeoLocation() {
        return GeoLocation.GeoLocationBuilder.aGeoLocation()
                .withCity("city_" + nextInt(1, 4))
                .withCountry("country_" + nextInt(1, 4))
                .withIpAddress("127.0.0. +" + nextInt(1, 256))
                .build();
    }

    public static RequestLog aRequestLog() {
        return RequestLog.RequestLogBuilder
                .aRequestLog()
                .withCompanyId(nextInt(4, 8))
                .withUserId(nextLong(10, 20))
                .withCreatedTime(now().minusSeconds(nextInt(100, 300)))
                .withRequestDuration(nextLong(50, 600))
                .withRequestStatus(getRandomElement(SAMPLE_RESPONSE_CODES))
                .withRequestPath(getRandomElement(SAMPLE_API_ENDPOINTS))
                .withRequestMethod(getRandomElement(SAMPLE_REQUEST_METHOD))
                .withGeoLocation(aGeoLocation())
                .build();
    }

    public static RequestLog aRequestLog(int companyId, int responseCode, long durationTime, String api) {
        return RequestLog.RequestLogBuilder
                .aRequestLog()
                .withCompanyId(companyId)
                .withUserId(nextLong(10, 20))
                .withCreatedTime(now().minusSeconds(nextInt(100, 300)))
                .withRequestDuration(durationTime)
                .withRequestStatus(responseCode)
                .withRequestPath(api)
                .withRequestMethod(getRandomElement(SAMPLE_REQUEST_METHOD))
                .withGeoLocation(aGeoLocation())
                .build();
    }

    public static <T> T getRandomElement(Set<T> data) {
        return data
                .stream()
                .skip(nextInt(0, data.size()))
                .findAny()
                .orElseThrow();
    }
}

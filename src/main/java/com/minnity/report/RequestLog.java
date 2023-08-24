package com.minnity.report;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.Objects;

public class RequestLog {

  /*
  request path - server's API endpoint (e.g.  /api/user/orders)
  request method - HTTP request type (GET / POST / PUT / ORIGIN)
  */
  private final long userId;
  private final int companyId;
  private final GeoLocation geoLocation;
  private final long requestDuration;
  private final int requestStatus;
  private final String requestPath;
  private final String requestMethod;
  private final Instant createdTime;

  private RequestLog(long userId,
                    int companyId,
                    GeoLocation geoLocation,
                    long requestDuration,
                    int requestStatus,
                    String requestPath,
                    String requestMethod,
                    Instant createdTime) {
    this.userId = userId;
    this.companyId = companyId;
    this.geoLocation = geoLocation;
    this.requestDuration = requestDuration;
    this.requestStatus = requestStatus;
    this.requestPath = requestPath;
    this.requestMethod = requestMethod;
    this.createdTime = createdTime;
  }

  public long getUserId() {
    return userId;
  }

  public int getCompanyId() {
    return companyId;
  }

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public long getRequestDuration() {
    return requestDuration;
  }

  public int getRequestStatus() {
    return requestStatus;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public Instant getCreatedTime() {
    return createdTime;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userId", userId)
        .append("companyId", companyId)
        .append("geoLocation", geoLocation)
        .append("requestDuration", requestDuration)
        .append("requestStatus", requestStatus)
        .append("requestPath", requestPath)
        .append("requestMethod", requestMethod)
        .append("createdTime", createdTime)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RequestLog that = (RequestLog) o;
    return userId == that.userId && companyId == that.companyId && requestDuration == that.requestDuration && requestStatus == that.requestStatus && geoLocation.equals(that.geoLocation) && requestPath.equals(that.requestPath) && requestMethod.equals(that.requestMethod) && createdTime.equals(that.createdTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, companyId, geoLocation, requestDuration, requestStatus, requestPath, requestMethod, createdTime);
  }

  public static final class RequestLogBuilder {
    private long userId;
    private int companyId;
    private GeoLocation geoLocation;
    private long requestDuration;
    private int requestStatus;
    private String requestPath;
    private String requestMethod;
    private Instant createdTime;

    private RequestLogBuilder() {
    }

    public static RequestLogBuilder aRequestLog() {
      return new RequestLogBuilder();
    }

    public RequestLogBuilder withUserId(long userId) {
      this.userId = userId;
      return this;
    }

    public RequestLogBuilder withCompanyId(int companyId) {
      this.companyId = companyId;
      return this;
    }

    public RequestLogBuilder withGeoLocation(GeoLocation geoLocation) {
      this.geoLocation = geoLocation;
      return this;
    }

    public RequestLogBuilder withRequestDuration(long requestDuration) {
      this.requestDuration = requestDuration;
      return this;
    }

    public RequestLogBuilder withRequestStatus(int requestStatus) {
      this.requestStatus = requestStatus;
      return this;
    }

    public RequestLogBuilder withRequestPath(String requestPath) {
      this.requestPath = requestPath;
      return this;
    }

    public RequestLogBuilder withRequestMethod(String requestMethod) {
      this.requestMethod = requestMethod;
      return this;
    }

    public RequestLogBuilder withCreatedTime(Instant createdTime) {
      this.createdTime = createdTime;
      return this;
    }

    public RequestLog build() {
      return new RequestLog(userId, companyId, geoLocation, requestDuration, requestStatus, requestPath, requestMethod, createdTime);
    }
  }
}

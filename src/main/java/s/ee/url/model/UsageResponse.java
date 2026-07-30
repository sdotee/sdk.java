package s.ee.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for account usage statistics.
 */
public record UsageResponse(
        @JsonProperty("code") int code,
        @JsonProperty("data") Data data,
        @JsonProperty("message") String message
) {
    public record Data(
            @JsonProperty("api_count_day") int apiCountDay,
            @JsonProperty("api_count_day_limit") int apiCountDayLimit,
            @JsonProperty("api_count_month") int apiCountMonth,
            @JsonProperty("api_count_month_limit") int apiCountMonthLimit,
            @JsonProperty("file_count") long fileCount,
            @JsonProperty("link_count_day") int linkCountDay,
            @JsonProperty("link_count_day_limit") int linkCountDayLimit,
            @JsonProperty("link_count_month") int linkCountMonth,
            @JsonProperty("link_count_month_limit") int linkCountMonthLimit,
            @JsonProperty("qrcode_count_day") int qrcodeCountDay,
            @JsonProperty("qrcode_count_day_limit") int qrcodeCountDayLimit,
            @JsonProperty("qrcode_count_month") int qrcodeCountMonth,
            @JsonProperty("qrcode_count_month_limit") int qrcodeCountMonthLimit,
            @JsonProperty("storage_usage_limit_mb") String storageUsageLimitMb,
            @JsonProperty("storage_usage_mb") String storageUsageMb,
            @JsonProperty("text_count_day") int textCountDay,
            @JsonProperty("text_count_day_limit") int textCountDayLimit,
            @JsonProperty("text_count_month") int textCountMonth,
            @JsonProperty("text_count_month_limit") int textCountMonthLimit,
            @JsonProperty("upload_count_day") int uploadCountDay,
            @JsonProperty("upload_count_day_limit") int uploadCountDayLimit,
            @JsonProperty("upload_count_month") int uploadCountMonth,
            @JsonProperty("upload_count_month_limit") int uploadCountMonthLimit
    ) {
    }
}

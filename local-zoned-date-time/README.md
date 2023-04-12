# local-zoned-date-time

Some simple tests with Local/ZonedDate/Time/DateTime...

* LocalDateTimeToUTC prints current local date time and the corresponding conversion to given time zone. Default time zone UTC.
* ZoneIds prints default system zone ids. map.
* ConvertToTimezone from given parameters <yyyy-MM-dd> <HH:mm:ss> <Source time zone> <Target time zone> generates zoned date time and converts to target time zone:
    - Sample arguments:
        + 2023-06-01 05:00:30 "UTC" "Europe/Madrid"
        + 2023-01-01 05:00:30 "UTC" "Europe/Madrid"
        + 2023-06-01 05:00:30 "Europe/London" "Europe/Madrid"
        + 2023-01-01 05:00:30 "Europe/London" "Europe/Madrid"
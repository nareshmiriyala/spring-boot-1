package chapter12;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class LocalDateTimeWithTimezones {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.of(2016, 11, 28, 9, 30);
    System.out.println("Local date time is: " + localDateTime);

    ZonedDateTime LAZone = localDateTime.atZone(ZoneId.of("America/Los_Angeles"));
    System.out.println("LAZone: " + LAZone);

    ZonedDateTime HCMZone = localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
    System.out.println("HCMZone: " + HCMZone);

    ZonedDateTime LADateTimeToUTC = LAZone.withZoneSameInstant(ZoneId.of("UTC+00:00"));
    System.out.println("Converted to UTC time zone:" + LADateTimeToUTC);

    ZonedDateTime LADateTimeToGMTPlus1 = LAZone.withZoneSameInstant(ZoneId.of("GMT+01:00"));
    System.out.println("LADateTimeToGMTPlus1: " + LADateTimeToGMTPlus1);

    ZonedDateTime LADateTimeToGMTPlus2 = LAZone.withZoneSameLocal(ZoneId.of("GMT+01:00"));
    System.out.println("LADateTimeToGMTPlus1: " + LADateTimeToGMTPlus2);

    ZonedDateTime zonedDateTime = ZonedDateTime.parse("2018-07-16T09:21:45.492Z");
    System.out.println("Convert string to ZonedDatetime: " + zonedDateTime);

    LocalDateTime localDateTime1 = LocalDateTime.ofInstant(zonedDateTime.toInstant(), TimeZone.getDefault().toZoneId());
    System.out.println("Convert timezone to LocalDateTime: way 1 " + localDateTime1);

    LocalDateTime localDateTime2 = zonedDateTime.withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
    System.out.println("Convert timezone to LocalDateTime: way 2 " + localDateTime2);

  }
}

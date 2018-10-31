package weatherapp.services;

import org.junit.Test;
import weatherapp.resources.WeatherReport;
import weatherapp.utils.CoordPair;

import java.util.List;

import static org.junit.Assert.*;

public class NWSForecastServiceTest {
    private static NWSForecastService service = new NWSForecastService();
    private static CoordPair testPoint = new CoordPair(39.0693, -94.6716);

    @Test
    public void getCurrentWeather() {
        WeatherReport report = service.getCurrentWeather(testPoint);

        assertEquals(39.035875, report.coords.getLatitude(), 0.0);
        assertEquals(-94.637407, report.coords.getLongitude(), 0.0);
        assertEquals("Roeland Park, KS", report.name);
        assertNotNull(report.timestamp);

        assertNotEquals(0, report.summary.length());

//        System.out.println(report);
    }

    @Test
    public void getHourlyWeather() {
        List<WeatherReport> reports = service.getHourlyWeather(testPoint);
        assertEquals(24, reports.size());

        for (WeatherReport report : reports) {
            assertEquals(39.035875, report.coords.getLatitude(), 0.0);
            assertEquals(-94.637407, report.coords.getLongitude(), 0.0);
            assertEquals("Roeland Park, KS", report.name);
            assertNotNull(report.timestamp);

            assertNotEquals(0, report.summary.length());
        }

//        for (WeatherReport report : reports) {
//            System.out.println(report);
//        }
    }

    @Test
    public void get5DayWeather() {
        List<WeatherReport> reports = service.get5DayWeather(testPoint);
        assertEquals(10, reports.size());

        for (WeatherReport report : reports) {
            assertEquals(39.035875, report.coords.getLatitude(), 0.0);
            assertEquals(-94.637407, report.coords.getLongitude(), 0.0);
            assertEquals("Roeland Park, KS", report.name);
            assertNotNull(report.timestamp);

            assertNotEquals(0, report.summary.length());
        }

//        for (WeatherReport report : reports) {
//            System.out.println(report);
//        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void get10DayWeather() {
        List<WeatherReport> reports = service.get10DayWeather(testPoint);
    }
}
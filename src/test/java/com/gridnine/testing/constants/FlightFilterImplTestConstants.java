package com.gridnine.testing.constants;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterImplTestConstants {

    public static final Flight NORMAL_FLIGHT;
    public static final Flight NORMAL_MULTI_SEGMENT_FLIGHT;
    public static final Flight DEP_BEFORE_NOW_FLIGHT;
    public static final Flight ARR_BEFORE_DEP_FLIGHT;
    public static final Flight GROUND_TIME_MORE_THAN_TWO_HOURS_FIRST_FLIGHT;
    public static final Flight GROUND_TIME_MORE_THAN_TWO_HOURS_SECOND_FLIGHT;

    static {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        NORMAL_FLIGHT = createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2));
        NORMAL_MULTI_SEGMENT_FLIGHT = createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5));
        DEP_BEFORE_NOW_FLIGHT = createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow);
        ARR_BEFORE_DEP_FLIGHT = createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6));
        GROUND_TIME_MORE_THAN_TWO_HOURS_FIRST_FLIGHT = createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6));
        GROUND_TIME_MORE_THAN_TWO_HOURS_SECOND_FLIGHT = createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7));
    }

    public static List<Flight> getAllFlights() {
        return new ArrayList<>(List.of(
                NORMAL_FLIGHT,
                NORMAL_MULTI_SEGMENT_FLIGHT,
                DEP_BEFORE_NOW_FLIGHT,
                ARR_BEFORE_DEP_FLIGHT,
                GROUND_TIME_MORE_THAN_TWO_HOURS_FIRST_FLIGHT,
                GROUND_TIME_MORE_THAN_TWO_HOURS_SECOND_FLIGHT
        ));
    }

    public static List<Flight> getFlightsWhenDepAfterNow() {
        List<Flight> flights = getAllFlights();
        flights.remove(DEP_BEFORE_NOW_FLIGHT);
        return flights;
    }

    public static List<Flight> getFlightsWhenArrAfterDep() {
        List<Flight> flights = getAllFlights();
        flights.remove(ARR_BEFORE_DEP_FLIGHT);
        return flights;
    }

    public static List<Flight> getFlightsWhenTotalGroundTimeLessThanTwoHours() {
        List<Flight> flights = getAllFlights();
        flights.remove(GROUND_TIME_MORE_THAN_TWO_HOURS_FIRST_FLIGHT);
        flights.remove(GROUND_TIME_MORE_THAN_TWO_HOURS_SECOND_FLIGHT);
        return flights;
    }

    public static List<Flight> getNormalFlights() {
        return List.of(NORMAL_FLIGHT, NORMAL_MULTI_SEGMENT_FLIGHT);
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

}

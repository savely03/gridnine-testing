package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gridnine.testing.constants.FlightFilterImplTestConstants.*;
import static org.assertj.core.api.Assertions.*;


class FlightFilterImplTest {

    private static List<Flight> allFlights;
    private static List<Flight> flightsWhenDepAfterNow;
    private static List<Flight> flightsWhenArrAfterDep;
    private static List<Flight> flightsWhenTotalGroundTimeLessThanTwoHours;
    private static List<Flight> normalFlights;
    private final FlightFilter out = new FlightFilterImpl(allFlights);

    @BeforeAll
    static void init() {
        allFlights = getAllFlights();
        flightsWhenDepAfterNow = getFlightsWhenDepAfterNow();
        flightsWhenArrAfterDep = getFlightsWhenArrAfterDep();
        flightsWhenTotalGroundTimeLessThanTwoHours = getFlightsWhenTotalGroundTimeLessThanTwoHours();
        normalFlights = getNormalFlights();
    }

    @Test
    void getListFlightsTest() {
        assertThat(out.getListFlights()).isEqualTo(allFlights).hasSize(allFlights.size());
    }

    @Test
    void excludeWhenDepartureBeforeNowTest() {
        int initialSize = out.getListFlights().size();

        List<Flight> actual = out.excludeWhenDepartureBeforeNow().getListFlights();

        assertThat(actual).doesNotContain(DEP_BEFORE_NOW_FLIGHT).hasSize(initialSize - 1)
                .containsExactlyInAnyOrderElementsOf(flightsWhenDepAfterNow);
    }

    @Test
    void excludeWhenArrivalBeforeDepartureTest() {
        int initialSize = out.getListFlights().size();

        List<Flight> actual = out.excludeWhenArrivalBeforeDeparture().getListFlights();

        assertThat(actual).doesNotContain(ARR_BEFORE_DEP_FLIGHT).hasSize(initialSize - 1)
                .containsExactlyInAnyOrderElementsOf(flightsWhenArrAfterDep);
    }

    @Test
    void excludeWhenTotalGroundTimeMoreThanTwoHoursTest() {
        int initialSize = out.getListFlights().size();

        List<Flight> actual = out.excludeWhenTotalGroundTimeMoreThanTwoHours().getListFlights();

        assertThat(actual).doesNotContain(GROUND_TIME_MORE_THAN_TWO_HOURS_FIRST_FLIGHT,
                        GROUND_TIME_MORE_THAN_TWO_HOURS_SECOND_FLIGHT).hasSize(initialSize - 2)
                .containsExactlyInAnyOrderElementsOf(flightsWhenTotalGroundTimeLessThanTwoHours);
    }

    @Test
    void allFiltersTest() {
        int initialSize = out.getListFlights().size();

        List<Flight> actual = out
                .excludeWhenDepartureBeforeNow()
                .excludeWhenArrivalBeforeDeparture()
                .excludeWhenTotalGroundTimeMoreThanTwoHours()
                .getListFlights();

        assertThat(actual).hasSize(initialSize - 4).containsExactlyInAnyOrderElementsOf(normalFlights);
    }
}
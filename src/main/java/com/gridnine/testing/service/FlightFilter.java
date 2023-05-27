package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> getListFlights();

    FlightFilter excludeWhenDepartureBeforeNow();

    FlightFilter excludeWhenArrivalBeforeDeparture();

    FlightFilter excludeWhenTotalGroundTimeMoreThanTwoHours();
}

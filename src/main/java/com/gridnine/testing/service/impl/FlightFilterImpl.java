package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class FlightFilterImpl implements FlightFilter {

    private final List<Flight> flights;

    public FlightFilterImpl(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public List<Flight> getListFlights() {
        return Collections.unmodifiableList(flights);
    }

    @Override
    public FlightFilter excludeWhenDepartureBeforeNow() {
        return new FlightFilterImpl(flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now()))
                ).toList());

    }

    @Override
    public FlightFilter excludeWhenArrivalBeforeDeparture() {
        return new FlightFilterImpl(flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()))
                ).toList());
    }

    @Override
    public FlightFilter excludeWhenTotalGroundTimeMoreThanTwoHours() {
        return new FlightFilterImpl(flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    Duration duration = Duration.ZERO;

                    for (int i = 1; i < segments.size(); i++) {
                        LocalDateTime arrivalDate = segments.get(i - 1).getArrivalDate();
                        LocalDateTime departureDate = segments.get(i).getDepartureDate();
                        duration = duration.plus(Duration.between(departureDate, arrivalDate).abs());
                    }

                    return duration.toHours() <= 2;
                }).toList());
    }
}


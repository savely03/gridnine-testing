package com.gridnine.testing.main;

import com.gridnine.testing.factory.FlightBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilter;
import com.gridnine.testing.service.impl.FlightFilterImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilter flightFilter = new FlightFilterImpl(flights);
        System.out.println("--------------------------");
        System.out.println("All flights");
        flightFilter.getListFlights().forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println("Flights where departure is after now");
        flightFilter.excludeWhenDepartureBeforeNow().getListFlights().forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println("Flights where arrival is after departure");
        flightFilter.excludeWhenArrivalBeforeDeparture().getListFlights().forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println("Flights where total ground time is less or equal two hours");
        flightFilter.excludeWhenTotalGroundTimeMoreThanTwoHours().getListFlights().forEach(System.out::println);
        System.out.println("--------------------------");
    }
}

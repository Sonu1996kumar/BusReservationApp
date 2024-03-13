package com.reservationapp.controller;

import com.reservationapp.entity.Bus;
import com.reservationapp.entity.Passenger;
import com.reservationapp.entity.Route;
import com.reservationapp.entity.SubRoute;
import com.reservationapp.exception.ResourceNotFound;
import com.reservationapp.payload.ReservationDto;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.PassengerRepository;
import com.reservationapp.repository.RouteRepository;
import com.reservationapp.repository.SubRouteRepository;
import com.sun.jdi.request.StepRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private SubRouteRepository subRouteRepository;

    public ReservationDto bookTicket(
            @RequestParam long busId,
            @RequestParam long routeId,
            @RequestParam String seatNumber,
            Passenger passenger
    ){
        boolean busIsPresent=false;
        boolean routeIsPresent=false;
        boolean subRouteIsPresent=false;

        Optional<Bus> byId = busRepository.findById(busId);

        if(byId.isPresent()){
            busIsPresent=true;
            Bus bus = byId.get();
        }

        Optional<Route> byRouteId = routeRepository.findById(routeId);

        if(byRouteId.isPresent()){
            routeIsPresent=true;
            Route route = byRouteId.get();
        }

        Optional<SubRoute> bySubRouteId = subRouteRepository.findById(routeId);

        if(bySubRouteId.isPresent()){
            subRouteIsPresent=true;
            SubRoute subRoute = bySubRouteId.get();
        }


        if(busIsPresent && routeIsPresent || busIsPresent && subRouteIsPresent){
            //add
        }

        return null;
    }
}

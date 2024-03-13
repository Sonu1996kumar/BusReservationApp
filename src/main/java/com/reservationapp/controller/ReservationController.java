package com.reservationapp.controller;

import com.reservationapp.entity.Bus;
import com.reservationapp.entity.Passenger;
import com.reservationapp.entity.Route;
import com.reservationapp.entity.SubRoute;
import com.reservationapp.payload.ReservationDto;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.PassengerRepository;
import com.reservationapp.repository.RouteRepository;
import com.reservationapp.repository.SubRouteRepository;
import com.reservationapp.util.EmailService;
import com.reservationapp.util.ExcelGeneratorService;
import com.reservationapp.util.PdfTicketGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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

    @Autowired
    private PdfTicketGeneratorService pdfTicketGeneratorService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelGeneratorService excelGeneratorService;



    //http://localhost:8080/api/reservation?busId=1&routeId=1
    @PostMapping
    public ResponseEntity<String> bookTicket(
            @RequestParam long busId,
            @RequestParam long routeId,

            @RequestBody Passenger passenger
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
            //add passenger details

            Passenger p = new Passenger();
            p.setFirstName(passenger.getFirstName());
            p.setLastName(passenger.getLastName());
            p.setEmail(passenger.getEmail());
            p.setMobile(passenger.getMobile());
            p.setBusId(busId);
            p.setRouteId(routeId);

            Passenger savedPassenger = passengerRepository.save(p);
            byte[] pdfBytes = pdfTicketGeneratorService.generateTicket(savedPassenger, byRouteId.get().getFromLocation(), byRouteId.get().getToLocation(), byRouteId.get().getFromDate(), byRouteId.get().getToDate());

            emailService.sendEmailWithAttachment(passenger.getEmail(), "Booking Confirmed","Your Reservation id"+savedPassenger.getId(),pdfBytes,"Ticket");
        }

        return new ResponseEntity<>("done...", HttpStatus.CREATED);
    }

    @GetMapping("/passengers/excel")
    public ResponseEntity<byte[]> generateExcelFile() {
        try {
            List<Passenger> passengers = fetchPassengersFromDatabase(); // Fetch passengers from database or any other source
            byte[] excelBytes = excelGeneratorService.generateExcel(passengers);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "passengers_data.xlsx");

            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return ResponseEntity.badRequest().build();
        }
    }

    private List<Passenger> fetchPassengersFromDatabase() {
        return passengerRepository.findAll();
    }
}

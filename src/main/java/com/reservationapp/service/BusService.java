package com.reservationapp.service;

import com.reservationapp.entity.Bus;
import com.reservationapp.payload.BusDto;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    public BusDto addBus(BusDto busDto){
        Bus bus = mapToEntity(busDto);
        driverRepository.save(busDto.getDriver());
        Bus savedBus = busRepository.save(bus);
        return mapToDto(savedBus);
    }


    Bus mapToEntity(BusDto busDto){
        Bus bus = new Bus();


        bus.setBusNumber(busDto.getBusNumber());
        bus.setBusType(busDto.getBusType());
        bus.setFromLocation(busDto.getFromLocation());
        bus.setToLocation(busDto.getToLocation());
        bus.setFromDate(busDto.getFromDate());
        bus.setToDate(busDto.getToDate());
        bus.setTotalDuration(busDto.getTotalDuration());
        bus.setFromTime(busDto.getFromTime());
        bus.setToTime(busDto.getToTime());
        bus.setPrice(busDto.getPrice());
        bus.setTotalSeats(busDto.getTotalSeats());
        bus.setAvailableSeats(busDto.getAvailableSeats());
        bus.setDriver(busDto.getDriver());
        return bus;
    }

    BusDto mapToDto(Bus bus) {
        BusDto busDto = new BusDto();

        busDto.setBusId(bus.getBusId());
        busDto.setBusNumber(bus.getBusNumber());
        busDto.setBusType(bus.getBusType());
        busDto.setFromLocation(bus.getFromLocation());
        busDto.setToLocation(bus.getToLocation());
        busDto.setFromDate(bus.getFromDate());
        busDto.setToDate(bus.getToDate());
        busDto.setTotalDuration(bus.getTotalDuration());
        busDto.setFromTime(bus.getFromTime());
        busDto.setToTime(bus.getToTime());
        busDto.setPrice(bus.getPrice());
        busDto.setTotalSeats(bus.getTotalSeats());
        busDto.setAvailableSeats(bus.getAvailableSeats());
        busDto.setDriver(bus.getDriver());
        return busDto;
    }
}

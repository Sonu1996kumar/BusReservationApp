package com.reservationapp.service;

import com.reservationapp.entity.Bus;
import com.reservationapp.payload.BusDto;
import com.reservationapp.repository.BusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    //@Transactional    //----Eska use hmlog esliye krte h kyu ki ye ek hi transection krta h ek baar me
    public void addBus(BusDto busDto) {


        Bus bus = new Bus();
        bus.setBusNumber(busDto.getBusNumber());
        bus.setBusType(busDto.getBusType());
        bus.setPrice(busDto.getPrice());
        bus.setTotalSeats(busDto.getTotalSeats());
        bus.setAvailableSeats(busDto.getAvailableSeats());

        Bus savedBus = busRepository.save(bus);
    }
}
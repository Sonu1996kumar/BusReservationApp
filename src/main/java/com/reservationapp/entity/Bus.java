package com.reservationapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busId;
    @Column(name = "bus_number",unique = true)
    private String busNumber;
    private String busType;
    private String fromLocation;
    private String toLocation;
    private Date fromDate;
    private Date toDate;
    private String totalDuration;
    private String fromTime;
    private String toTime;
    private double price;
    private int totalSeats;
    private int availableSeats;
}
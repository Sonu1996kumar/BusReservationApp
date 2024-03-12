package com.reservationapp.payload;




import com.reservationapp.entity.SubRoute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {
    private String busNumber;
    private String busType;
    private double price;
    private int totalSeats;
    private int availableSeats;

}
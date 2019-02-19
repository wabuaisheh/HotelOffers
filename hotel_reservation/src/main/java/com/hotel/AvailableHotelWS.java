/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel;

import java.time.LocalDate;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author w.fahed
 */
@Path("AvailableHotelWS/{fromDate}/{city}/{numberOfAdults}/{toDate}")
public class AvailableHotelWS {

    @POST
    @Path("getAvailableHotel")
    @Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
    public List<AvailableHotelBean> getAvailableHotel(@PathParam("fromDate") LocalDate fromDate,
            @PathParam("city") String city,
            @PathParam("numberOfAdults") String numberOfAdults,
            @PathParam("toDate") LocalDate toDate) {

        return new HotelServices().getOffers(fromDate.toString(), toDate.toString(), city, numberOfAdults);

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel;

import java.util.ArrayList;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Walaa
 */
public class HotelServicesIT {
    
    public HotelServicesIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOffers method, of class HotelServices.
     */
    @Test
    public void testGetOffers() {
        System.out.println("getOffers");
        String fromDate = "";
        String toDate = "";
        String city = "";
        String noOfAdults = "";
        HotelServices instance = new HotelServices();
        instance.getOffers(fromDate, toDate, city, noOfAdults);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of enumFields method, of class HotelServices.
     */
    @Test
    public void testEnumFields() {
        System.out.println("enumFields");
        String providerName = "";
        HotelServices instance = new HotelServices();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.enumFields(providerName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of initJsonRequest method, of class HotelServices.
     */
    @Test
    public void testInitJsonRequest() {
        System.out.println("initJsonRequest");
        ArrayList<String> result_2 = null;
        String provider = "";
        String fromDate = "";
        String city = "";
        String numberOfAdults = "";
        String toDate = "";
        HotelServices instance = new HotelServices();
        JSONObject expResult = null;
        JSONObject result = instance.initJsonRequest(result_2, provider, fromDate, city, numberOfAdults, toDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of initJsonResponse method, of class HotelServices.
     */
    @Test
    public void testInitJsonResponse() {
        System.out.println("initJsonResponse");
        ArrayList<String> result_2 = null;
        String providerName = "";
        String providerNameResponse = "";
        String hotelName = "";
        ArrayList<String> amenities = null;
        double fare = 0.0;
        HotelServices instance = new HotelServices();
        JSONObject expResult = null;
        JSONObject result = instance.initJsonResponse(result_2, providerName, providerNameResponse, hotelName, amenities, fare);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of convertResponseKeyToOriginal method, of class HotelServices.
     */
    @Test
    public void testConvertResponseKeyToOriginal() {
        System.out.println("convertResponseKeyToOriginal");
        ArrayList<String> providerResponse = null;
        String providerName = "";
        String hotelName = "";
        ArrayList<String> amenities = null;
        double fare = 0.0;
        HotelServices instance = new HotelServices();
        String expResult = "";
        String result = instance.convertResponseKeyToOriginal(providerResponse, providerName, hotelName, amenities, fare);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of mapToObject method, of class HotelServices.
     */
    @Test
    public void testMapToObject() {
        System.out.println("mapToObject");
        String jsonAsString = "";
        HotelServices instance = new HotelServices();
        AvailableHotelBean expResult = null;
        AvailableHotelBean result = instance.mapToObject(jsonAsString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}

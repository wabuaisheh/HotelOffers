/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author w.fahed
 */
public class HotelServices {

    private final HttpClient httpClient;
    private HttpContext httpContext;

    public HotelServices() {
        httpClient = null;
    }

    public HotelServices(HttpClient client) {
        httpClient = client;
    }

    //list of providers constant 
    enum Providers {

        BestHotels, CrazyHotels;
    }

    //first provider enum >> name:represent availableHotel key  value:represent provider key 
    //first provider response data 
    enum BestHotels {

        city("city"), fromDate("fromDate"), toDate("toDate"), numberOfAdults("numberOfAdults");

        enum BestHotelsResponse {

            hotelName("hotel"), fare("hotelFare"), amenities("roomAmenities"), rate("hotelRate");
            private String responsecontent;

            public String getResponseContent() {
                return this.responsecontent;
            }

            BestHotelsResponse(String responsecontent) {
                this.responsecontent = responsecontent;
            }
        }
        private String content;

        public String getContent() {
            return this.content;
        }

        BestHotels(String content) {
            this.content = content;
        }
    }

    //first provider enum >> name:represent availableHotel key  value:represent provider key 
    //first provider response data 
    enum CrazyHotels {

        city("city"), fromDate("from"), toDate("To"), numberOfAdults("adultsCount");

        enum CrazyHotelsResponse {
            //to do fare price and discount

            hotelName("hotelName"), rate("rate"), amenities("amenities"), price("price"), discount("discount");
            private String responsecontent;

            public String getResponseContent() {
                return this.responsecontent;
            }

            CrazyHotelsResponse(String responsecontent) {
                this.responsecontent = responsecontent;
            }
        }
        private String content;

        public String getContent() {
            return this.content;
        }

        CrazyHotels(String content) {
            this.content = content;
        }
    }

    //start Point
    public List<AvailableHotelBean> getOffers(String fromDate, String toDate, String city, String noOfAdults) {

        //get list of provider
        List<AvailableHotelBean> availableHotels = new ArrayList<>();
        //get providers enum list 
        ArrayList<String> providers = enumFields(SystemConstant.PROVIDERS);

        //loop on providers list to get result from each one
        for (String provider : providers) {
            //START  >>> just for test instead of post request 
            String hotelName = "hotel" + provider;
            ArrayList<String> amenities = new ArrayList<>();
            amenities.add("amenities0" + provider);
            amenities.add("amenities1" + provider);
            double fare = 10;
            //END 

            //get providers hotel list to get key and original key ex: hotelName("hotel")>>>originalKey("providerKey")
            ArrayList<String> providerFields = enumFields(provider);

            //create json request parameter body for post webservice request
            String jsonRequest = initJsonRequest(providerFields, provider, fromDate, city, noOfAdults, toDate).toString();
            System.out.println(jsonRequest);

            //uncomment to execute post request
            //executePostRequest("localhost:8080/" + provider, jsonRequest);//to be active  must return[ hotelName, amenities, fare]
            //get providers hotel response list to get key and original key
            ArrayList<String> providerResponseFields = enumFields(provider + "Response");

            // replace provider key to available hotel key and then convert json result to java object to edit data
            AvailableHotelBean bean = mapToObject(convertResponseKeyToOriginal(providerResponseFields, provider, hotelName, amenities, fare));
            //add each result from provider in list to view later 
            availableHotels.add(bean);

        }

        for (AvailableHotelBean bean : availableHotels) {
            System.out.println(bean.getHotelName());

            if (bean.getFare() == 0.0) {
                bean.setFare(bean.getPrice() * bean.getDiscount());

            }
            System.out.println(bean.getFare());
            System.out.println(bean.getAmenities());
            System.out.println("***************************");
        }
        return availableHotels;
    }

    //return the key and content as a list for provider enum or response enum 
    public ArrayList<String> enumFields(String providerName) {

        ArrayList<String> list = new ArrayList<>();

        switch (providerName) {
            case SystemConstant.BEST_HOTELS:
                BestHotels[] bestHotel = BestHotels.values();
                for (BestHotels hotel : bestHotel) {
                    list.add(hotel.getContent());
                }
                break;
            case SystemConstant.PROVIDERS:
                Providers[] providers = Providers.values();
                for (Providers provider : providers) {
                    list.add(provider.toString());
                }
                break;
            case SystemConstant.CRAZY_HOTELS:
                CrazyHotels[] crazyHotel = CrazyHotels.values();
                for (CrazyHotels hotel : crazyHotel) {
                    list.add(hotel.getContent());
                }
                break;

            case SystemConstant.BEST_HOTELS_RESPONSE:
                BestHotels.BestHotelsResponse[] bestHotelsResponse = BestHotels.BestHotelsResponse.values();
                for (BestHotels.BestHotelsResponse hotel : bestHotelsResponse) {
                    list.add(hotel.getResponseContent());
                }
                break;
            case SystemConstant.CRAZY_HOTELS_RESPONSE:
                CrazyHotels.CrazyHotelsResponse[] crazyHotelsResponse = CrazyHotels.CrazyHotelsResponse.values();
                for (CrazyHotels.CrazyHotelsResponse hotel : crazyHotelsResponse) {
                    list.add(hotel.getResponseContent());
                }
                break;
            case SystemConstant.BEST_HOTELS_NAME:
                BestHotels[] BestHotelsName = BestHotels.values();
                for (BestHotels hotel : BestHotelsName) {
                    list.add(hotel.name());
                }
                break;
            case SystemConstant.CRAZY_HOTELS_NAME:
                CrazyHotels[] CrazyHotelsName = CrazyHotels.values();
                for (CrazyHotels hotel : CrazyHotelsName) {
                    list.add(hotel.name());
                }
                break;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    //return the key name for provider that equal the main available hotel key
    private String searchForKey(String enumKey, String provider) {

        String enumValue = "";
        if (provider.equals(SystemConstant.CRAZY_HOTELS)) {
            CrazyHotels[] crazyHotels = CrazyHotels.values();

            for (CrazyHotels hotel : crazyHotels) {
                if (hotel.name().equals(enumKey)) {
                    enumValue = hotel.getContent();
                    break;
                }
            }
        }
        if (provider.equals(SystemConstant.BEST_HOTELS)) {
            BestHotels[] bestHotels = BestHotels.values();

            for (BestHotels hotel : bestHotels) {
                if (hotel.name().equals(enumKey)) {
                    enumValue = hotel.getContent();
                    break;
                }
            }
        }
        if (provider.equals(SystemConstant.BEST_HOTELS_RESPONSE)) {

            BestHotels.BestHotelsResponse[] bestHotelsResponse = BestHotels.BestHotelsResponse.values();

            for (BestHotels.BestHotelsResponse hotel : bestHotelsResponse) {
                if (hotel.name().equals(enumKey)) {
                    enumValue = hotel.getResponseContent();
                    break;
                }
            }
        }

        if (provider.equals(SystemConstant.CRAZY_HOTELS_RESPONSE)) {

            CrazyHotels.CrazyHotelsResponse[] crazyHotelsResponse = CrazyHotels.CrazyHotelsResponse.values();
            for (CrazyHotels.CrazyHotelsResponse hotel : crazyHotelsResponse) {
                if (hotel.name().equals(enumKey)) {
                    enumValue = hotel.getResponseContent();
                    break;
                }
            }
        }
        return enumValue;
    }

    //create the parameter as json to call the providers web service 
    public JSONObject initJsonRequest(ArrayList<String> result, String provider, String fromDate, String city, String numberOfAdults, String toDate) {

        JSONObject newJsonObj = null;
        if (result != null) {
            Iterator iterator = result.iterator();
            try {
                newJsonObj = new JSONObject();
                while (iterator.hasNext()) {

                    String param = "";
                    String name = iterator.next().toString();

                    if (searchForKey(SystemConstant.FROM_DATE, provider).equals(name)) {
                        param = fromDate;
                    }
                    if (searchForKey(SystemConstant.CITY, provider).equals(name)) {
                        param = city;
                    }
                    if (searchForKey(SystemConstant.NUMBER_OF_ADULTS, provider).equals(name)) {
                        param = numberOfAdults;
                    }
                    if (searchForKey(SystemConstant.TO_DATE, provider).equals(name)) {
                        param = toDate;
                    }
                    newJsonObj.put(name, param);

                }
            } catch (JSONException ex) {
                Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newJsonObj;
    }

    //create the response as json  
    public JSONObject initJsonResponse(ArrayList<String> result, String providerName, String providerNameResponse, String hotelName, ArrayList<String> amenities, double fare) {

        JSONObject newJsonObj = null;
        if (result != null) {
            newJsonObj = new JSONObject();
            Iterator iterator = result.iterator();
            try {
                newJsonObj.put(SystemConstant.PROVIDER, providerName);

                while (iterator.hasNext()) {
                    String name = iterator.next().toString();
                    String param = "not found";
                    if (searchForKey(SystemConstant.HOTEL_NAME, providerNameResponse).equals(name)) {
                        param = hotelName;
                    }
                    if (searchForKey(SystemConstant.AMINITIES, providerNameResponse).equals(name)) {
                        param = amenities.toString();
                    }
                    if (searchForKey(SystemConstant.FARE, providerNameResponse).equals(name)) {
                        param = Double.toString(fare);
                    }//to do just as example 
                    if (searchForKey(SystemConstant.DISCOUNT, providerNameResponse).equals(name)) {
                        param = Double.toString(0.5);
                    }
                    if (searchForKey(SystemConstant.PRICE, providerNameResponse).equals(name)) {
                        param = Double.toString(20);
                    }

                    newJsonObj.put(name, param);

                }
            } catch (JSONException ex) {
                Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newJsonObj;
    }

//    replace providers key with available hotel keys
    public String convertResponseKeyToOriginal(ArrayList<String> providerResponse, String providerName, String hotelName, ArrayList<String> amenities, double fare) {

        String jsonAsString = "";

        if (providerName.equals(SystemConstant.BEST_HOTELS)) {
            jsonAsString = initJsonResponse(providerResponse, SystemConstant.BEST_HOTELS, SystemConstant.BEST_HOTELS_RESPONSE, hotelName, amenities, fare).toString();
            System.out.println("befor >>> " + jsonAsString);
            BestHotels.BestHotelsResponse[] bestHotelsResponse = BestHotels.BestHotelsResponse.values();
            for (BestHotels.BestHotelsResponse hotel : bestHotelsResponse) {
                jsonAsString = jsonAsString.replaceAll("\\b" + hotel.getResponseContent() + "\\b", hotel.name());
            }
        }
        if (providerName.equals(SystemConstant.CRAZY_HOTELS)) {
            jsonAsString = initJsonResponse(providerResponse, SystemConstant.CRAZY_HOTELS, SystemConstant.CRAZY_HOTELS_RESPONSE, hotelName, amenities, fare).toString();
            System.out.println("befor >>> " + jsonAsString);
            CrazyHotels.CrazyHotelsResponse[] crazyHotelsResponse = CrazyHotels.CrazyHotelsResponse.values();
            for (CrazyHotels.CrazyHotelsResponse hotel : crazyHotelsResponse) {
                jsonAsString = jsonAsString.replaceAll("\\b" + hotel.getResponseContent() + "\\b", hotel.name());
            }
        }
        System.out.println("after >>> " + jsonAsString);
        return jsonAsString;
    }

    //to call provider webservice 
    private HttpResponse executePostRequest(String apiURI, String payloadAsString) {
        try {
            StringEntity input = new StringEntity(payloadAsString);
            input.setContentType("application/json");

            HttpPost postRequest = new HttpPost(apiURI);
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest, httpContext);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //convert provider webservice response to object to be able to change value and make our changes on fare 
    public AvailableHotelBean mapToObject(String jsonAsString) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = jsonAsString;
        AvailableHotelBean availableHotel = null;
        try {
            availableHotel = objectMapper.readValue(json, AvailableHotelBean.class);
        } catch (IOException ex) {
            Logger.getLogger(HotelServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return availableHotel;
    }
//uncomment to test 
//    public static void main(String[] args) {
//
//        HotelServices hotelServices = new HotelServices();
//        String fromDate = "fromDateparam";
//        String city = "cityparam";
//        String numberOfAdults = "numberOfAdultsparam";
//        String toDate = "toDateparam";
//
//        hotelServices.getOffers(fromDate, toDate, city, numberOfAdults);
//
//    }
}

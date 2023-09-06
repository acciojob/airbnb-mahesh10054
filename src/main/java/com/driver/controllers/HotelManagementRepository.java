package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class HotelManagementRepository {

    Map<String, Hotel> hotelDb = new HashMap<>();
    Map<Integer, User> userDb = new HashMap<>();
    Map<String, Booking> bookingDb = new HashMap<>();


    public HashMap addHotel(Hotel hotel) {
        hotelDb.put(hotel.getHotelName(),hotel);
        return (HashMap) hotelDb;
    }

    public Integer addUser(User user) {
        userDb.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        int maxfacility = 0;
        String ans = "";

        for(String hotelName : hotelDb.keySet())
        {
            if(hotelDb.get(hotelName).getFacilities().size() > maxfacility)
            {
                maxfacility = hotelDb.get(hotelName).getFacilities().size();
                ans = hotelName;
            }
            else if(hotelDb.get(hotelName).getFacilities().size() == maxfacility && hotelName.length() < ans.length())
            {
                ans = hotelName;
            }
        }
        return ans;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        List<Facility> oldFacilities = hotelDb.get(hotelName).getFacilities();

        for(Facility list : newFacilities)
        {
            if(!oldFacilities.contains(list))
            {
                oldFacilities.add(list);
            }
        }
        hotelDb.get(hotelName).setFacilities(oldFacilities);

        return hotelDb.get(hotelName);
    }

    public int bookARoom(Booking booking) {
        String hotelName = booking.getHotelName();

        if(hotelDb.containsKey(hotelName) == false){
            return -1;
        }

        int bookRooms = booking.getNoOfRooms();
        int availableRooms = hotelDb.get(hotelName).getAvailableRooms();
        int costnight = hotelDb.get(hotelName).getPricePerNight();
        if(bookRooms > availableRooms)
        {
            return -1;
        }
        bookingDb.put(String.valueOf(UUID.randomUUID()),booking);
        return bookRooms*costnight;
    }

    public int getBookings(Integer aadharCard) {

        if(userDb.containsKey(aadharCard) == false) return 0;
        String userName = userDb.get(aadharCard).getName();
        for(String BID : bookingDb.keySet())
        {
            if(bookingDb.get(BID).getBookingPersonName() == userName) return 1;
        }
        return 0;
    }
}

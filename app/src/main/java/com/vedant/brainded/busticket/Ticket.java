package com.vedant.brainded.busticket;

/**
 * Created by Dell on 6/23/2017.
 */

public class Ticket {
    String name;
    String seatNum;

    public String getName() {
        return name;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public Ticket(){

    }

    public Ticket(String name, String seatNum){
        this.name = name;
        this.seatNum = seatNum;
    }

}
